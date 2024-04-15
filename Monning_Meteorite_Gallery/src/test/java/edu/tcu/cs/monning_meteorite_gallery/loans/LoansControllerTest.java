package edu.tcu.cs.monning_meteorite_gallery.loans;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import org.assertj.core.api.Condition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoansControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LoansService loansService;

    List<Loans> loanees;
    List<Meteorite> meteorites;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp() {

        this.meteorites = new ArrayList<>();
        this.loanees = new ArrayList<>();

        Loans johnDoe = new Loans();
        johnDoe.setLoaneeId(1);
        johnDoe.setLoaneeName("John Doe");
        johnDoe.setLoaneeInstitution("National Science Institute");
        johnDoe.setLoaneeEmail("johndoe@example.com");
        johnDoe.setLoaneeAddress("123 Science Lane, Research City, RC 45678");
        johnDoe.setLoanStartdate("2024-01-01");
        johnDoe.setLoanDuedate("2024-06-30");
        johnDoe.setTrackingNumber("TN123456789");
        johnDoe.setLoaneeNotes("Please handle with care, especially the fragile samples.");
        johnDoe.setExtraFiles("loan_agreement.pdf, sample_list.xlsx");
        johnDoe.setMeteorites(meteorites);

        Loans janeSmith = new Loans();
        janeSmith.setLoaneeId(2);
        janeSmith.setLoaneeName("Jane Smith");
        janeSmith.setLoaneeInstitution("University of Technology");
        janeSmith.setLoaneeEmail("janesmith@example.com");
        janeSmith.setLoaneeAddress("456 Innovation Drive, Tech Town, TT 78901");
        janeSmith.setLoanStartdate("2024-03-01");
        janeSmith.setLoanDuedate("2024-09-30");
        janeSmith.setTrackingNumber("TN987654321");
        janeSmith.setLoaneeNotes("Ensure temperature control for the sensitive equipment.");
        janeSmith.setExtraFiles("equipment_list.pdf, maintenance_guide.pdf");

        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        this.meteorites.add(meteorite1);
        this.meteorites.add(meteorite2);

        this.loanees.add(johnDoe);
        this.loanees.add(janeSmith);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findLoansByIdSuccess() throws Exception{
        // Given
        given(this.loansService.findById(1)).willReturn(this.loanees.getFirst());

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/loans/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Found"))
                .andExpect(jsonPath("$.data.loanId").value(1))
                .andExpect(jsonPath("$.data.loaneeName").value("John Doe"));

    }

    @Test
    void findLoansByIdFailure() throws Exception {
        // Given
        given(this.loansService.findById(1)).willThrow(new ObjectNotFoundException("loanee" ,1));

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/loans/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loanee with Id 1"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

//    @Test
//    void testFindAllLoansSuccess() throws Exception {
//        // Given, Arrange inputs and targets. Define the behavior of Mock Objects
//        given(this.loansService.findAll()).willReturn(this.loanees);
//
//        // When and Then
//        this.mockMvc.perform(get(this.baseUrl + "/loans").accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Found All Loans"))
//                .andExpect(jsonPath("$.data", Matchers.hasSize(this.loanees.size())))
//                .andExpect(jsonPath("$.data[0].loanId").value(1))
//                .andExpect(jsonPath("$.data[0].loaneeName").value("John Doe"))
//                .andExpect(jsonPath("$.data[1].loanId").value(2))
//                .andExpect(jsonPath("$.data[1].loaneeName").value("Jane Smith"));
//    }

    @Test
    void testAddLoanSuccess() throws Exception {
        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        LoansDto loansDto = new LoansDto(
                null, //the loanId is generated automatically
                "Kazuma Kiryu",
                "Yakuza Foundation",
                "kazuma.kiryu@example.com",
                "1-2-3 Kamurocho, Tokyo",
                "2024-04-01",
                "2024-10-01",
                "TN123456789",
                "Active",
                "KAKATTE KOI!",
                "agreement.pdf, item_list.xlsx",
                Arrays.asList(meteorite1, meteorite2)
        );

        String json = this.objectMapper.writeValueAsString(loansDto);

        Loans savedLoanee = new Loans();
        savedLoanee.setLoaneeId(3);
        savedLoanee.setLoaneeName("Kazuma Kiryu");
        savedLoanee.setLoaneeInstitution("Yakuza Foundation");

        // Given
        given(this.loansService.save(Mockito.any(Loans.class))).willReturn(savedLoanee);

        //When and Then
        this.mockMvc.perform(post(this.baseUrl + "/loans").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Saved"))
                .andExpect(jsonPath("$.data.loanId").isNotEmpty())
                .andExpect(jsonPath("$.data.loaneeName").value("Kazuma Kiryu"))
                .andExpect(jsonPath("$.data.loaneeInstitution").value("Yakuza Foundation"));
                //add rest if necessary

    }

    @Test
    void testUpdateLoansSuccess() throws Exception {
        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        LoansDto loansDto = new LoansDto(
                null, //the loanId is generated automatically
                "Kazuma Kiryu",
                "Yakuza Foundation",
                "kazuma.kiryu@example.com",
                "1-2-3 Kamurocho, Tokyo",
                "2024-04-01",
                "2024-10-01",
                "TN123456789",
                "Active",
                "Life is like a trampoline. The lower you fall, the higher you go.",
                "agreement.pdf, item_list.xlsx",
                Arrays.asList(meteorite1, meteorite2)
        );

        Loans updatedLoans = new Loans();
        updatedLoans.setLoaneeId(1);
        updatedLoans.setLoaneeName("Kazuma Kiryu");

        String json = this.objectMapper.writeValueAsString(loansDto);

        // Given
        given(this.loansService.update(eq(1), Mockito.any(Loans.class))).willReturn(updatedLoans);

        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Updated"))
                .andExpect(jsonPath("$.data.loanId").isNotEmpty())
                .andExpect(jsonPath("$.data.loaneeName").value("Kazuma Kiryu"));
    }

    @Test
    void testUpdateLoansErrorWithNonExistingLoanId() throws Exception {
        // Given
        given(this.loansService.update(eq(7), Mockito.any(Loans.class))).willThrow(new ObjectNotFoundException("loanee", 7));

        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        LoansDto loansDto = new LoansDto(
                7,
                "Goro Majima",
                "Yakuza Foundation",
                "majima.goro@example.com",
                "1-2-3 Sotenbori, Osaka",
                "2024-05-01",
                "2024-12-01",
                "TN987654321",
                "Active",
                "KIRYU-CHAN!!!!",
                "agreement.pdf, item_list.xlsx",
                Arrays.asList(meteorite1, meteorite2)
                );

        String json = this.objectMapper.writeValueAsString(loansDto);

        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/7").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loanee with Id 7"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteLoansSuccess() throws Exception {
        // Given
        doNothing().when(this.loansService).delete(2);

        // When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/loans/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Deleted"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteLoansErrorWithNonExistentLoadId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("loanee", 7)).when(this.loansService).delete(7);

        // When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/loans/7").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loanee with Id 7"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testLoanMeteoriteSuccess() throws Exception {
        // Given
        doNothing().when(this.loansService).loanMeteorite(2, "M001");

        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/2/meteorites/M001").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Loan Meteorite Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testLoanMeteoriteErrorWithNonExistentLoanId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("loanee", 7)).when(this.loansService).loanMeteorite(7, "M001");

        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/7/meteorites/M001").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loanee with Id 7"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testLoanMeteoriteErrorWithNonExistentMeteoriteId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("meteorite", "M008")).when(this.loansService).loanMeteorite(1, "M008");

        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/1/meteorites/M008").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find meteorite with Id M008"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testArchiveLoanSuccess() throws Exception {
        // Given
        doNothing().when(this.loansService).archive(2);

        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/archive/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Archived Loan"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testArchiveLoanWithNonExistentLoaneeId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("loanee", 1)).when(this.loansService).archive(1);
        // When and Then
        this.mockMvc.perform(put(this.baseUrl + "/loans/archive/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find loanee with Id 1"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    //Implement test cases for findActiveLoans and findArchivedLoans
}