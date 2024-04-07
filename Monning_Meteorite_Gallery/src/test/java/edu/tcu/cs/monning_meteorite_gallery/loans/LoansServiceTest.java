package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoansServiceTest {

    @Mock
    LoansRepository loansRepository;

    @InjectMocks
    LoansService loansService;

    List<Loans> loanees;

    List<Meteorite> meteorites;

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

        meteorite1.setLoanee(johnDoe);
        meteorite2.setLoanee(johnDoe);

        this.meteorites.add(meteorite1);
        this.meteorites.add(meteorite2);

        this.loanees.add(johnDoe);
        this.loanees.add(janeSmith);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given
        Loans loanee = new Loans();

        loanee.setLoaneeId(1);
        loanee.setLoaneeName("John Doe");
        loanee.setLoaneeInstitution("National Science Institute");
        loanee.setLoaneeEmail("johndoe@example.com");
        loanee.setLoaneeAddress("123 Science Lane, Research City, RC 45678");
        loanee.setLoanStartdate("2024-01-01");
        loanee.setLoanDuedate("2024-06-30");
        loanee.setTrackingNumber("TN123456789");
        loanee.setLoaneeNotes("Please handle with care, especially the fragile samples.");
        loanee.setExtraFiles("loan_agreement.pdf, sample_list.xlsx");
        loanee.setMeteorites(meteorites);

        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        meteorite1.setLoanee(loanee);
        meteorite2.setLoanee(loanee);

        given(loansRepository.findById(1)).willReturn(Optional.of(loanee));

        // When
        Loans returnedLoan = loansService.findById(1);

        // Then
        assertThat(returnedLoan.getLoaneeId()).isEqualTo(1);
        assertThat(returnedLoan.getLoaneeName()).isEqualTo("John Doe");
        assertThat(returnedLoan.getLoaneeInstitution()).isEqualTo("National Science Institute");
        assertThat(returnedLoan.getLoaneeEmail()).isEqualTo("johndoe@example.com");
        assertThat(returnedLoan.getLoaneeAddress()).isEqualTo("123 Science Lane, Research City, RC 45678");
        assertThat(returnedLoan.getLoanStartdate()).isEqualTo("2024-01-01");
        assertThat(returnedLoan.getLoanDuedate()).isEqualTo("2024-06-30");
        assertThat(returnedLoan.getTrackingNumber()).isEqualTo("TN123456789");
        assertThat(returnedLoan.getLoaneeNotes()).isEqualTo("Please handle with care, especially the fragile samples.");
        assertThat(returnedLoan.getExtraFiles()).isEqualTo("loan_agreement.pdf, sample_list.xlsx");
        assertThat(returnedLoan.getMeteorites()).hasSize(2);
        verify(loansRepository, times(1)).findById(1);
    }

    @Test
    void FindByIdNotFound() {
        // Given
        given(loansRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(()->{
            Loans returnedLoan = loansService.findById(1);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find loanee with id 1");
        verify(loansRepository, times(1)).findById(1);
    }

    @Test
    void testFindAllSuccess(){
        // Given Arrange inputs and Targets. Define the behavior of the Mock Object
        given(loansRepository.findAll()).willReturn(this.loanees);

        // When Act on the target behavior
        List<Loans> returnedLoans = loansService.findAll();

        // Then Assert expected values, then verify findAll is called exactly once
        assertThat(returnedLoans.size()).isEqualTo(this.loanees.size());
        verify(loansRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess(){
        // Given
        Loans newLoanee = new Loans();

        newLoanee.setLoaneeName("Johnny Appleseed");

        given(loansRepository.save(newLoanee)).willReturn(newLoanee);

        // When
        Loans savedLoanee = loansService.save(newLoanee);

        // Then
        assertThat(savedLoanee.getLoaneeName()).isEqualTo("Johnny Appleseed");
        verify(loansRepository, times(1)).save(newLoanee);

    }

    @Test
    void testUpdateSuccess(){
        // Given
        Loans oldLoanee = new Loans();
        oldLoanee.setLoaneeId(1);
        oldLoanee.setLoaneeName("John Doe");
        oldLoanee.setLoaneeInstitution("National Science Institute");
        oldLoanee.setLoaneeEmail("johndoe@example.com");
        oldLoanee.setLoaneeAddress("123 Science Lane, Research City, RC 45678");
        oldLoanee.setLoanStartdate("2024-01-01");
        oldLoanee.setLoanDuedate("2024-06-30");
        oldLoanee.setTrackingNumber("TN123456789");
        oldLoanee.setLoaneeNotes("Please handle with care, especially the fragile samples.");
        oldLoanee.setExtraFiles("loan_agreement.pdf, sample_list.xlsx");
        oldLoanee.setMeteorites(meteorites);

        Loans newLoanee = new Loans();
        newLoanee.setLoaneeName("Jane Smith");
        newLoanee.setLoaneeInstitution("University of Technology");
        newLoanee.setLoaneeEmail("janesmith@example.com");
        newLoanee.setLoaneeAddress("456 Innovation Drive, Tech Town, TT 78901");
        newLoanee.setLoanStartdate("2024-03-01");
        newLoanee.setLoanDuedate("2024-09-30");
        newLoanee.setTrackingNumber("TN987654321");
        newLoanee.setLoaneeNotes("Ensure temperature control for the sensitive equipment.");
        newLoanee.setExtraFiles("equipment_list.pdf, maintenance_guide.pdf");
        newLoanee.setMeteorites(meteorites);

        given(loansRepository.findById(1)).willReturn(Optional.of(oldLoanee));
        given(loansRepository.save(oldLoanee)).willReturn(oldLoanee);

        // When
        Loans updatedLoanee = loansService.update(1, newLoanee);

        // Then
        assertThat(updatedLoanee.getLoaneeId()).isEqualTo(1);
        assertThat(updatedLoanee.getLoaneeName()).isEqualTo(newLoanee.getLoaneeName());
        verify(loansRepository, times(1)).findById(1);
        verify(loansRepository, times(1)).save(oldLoanee);
    }

    @Test
    void testUpdateNotFound(){
        // Given
        Loans newLoanee = new Loans();
        newLoanee.setLoaneeId(1);
        newLoanee.setLoaneeName("Jane Smith");
        newLoanee.setLoaneeInstitution("University of Technology");
        newLoanee.setLoaneeEmail("janesmith@example.com");
        newLoanee.setLoaneeAddress("456 Innovation Drive, Tech Town, TT 78901");
        newLoanee.setLoanStartdate("2024-03-01");
        newLoanee.setLoanDuedate("2024-09-30");
        newLoanee.setTrackingNumber("TN987654321");
        newLoanee.setLoaneeNotes("Ensure temperature control for the sensitive equipment.");
        newLoanee.setExtraFiles("equipment_list.pdf, maintenance_guide.pdf");
        newLoanee.setMeteorites(meteorites);

        given(loansRepository.findById(2)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, ()-> {
           loansService.update(2, newLoanee);
        });

        // Then
        verify(loansRepository, times(1)).findById(2);
    }

    @Test
    void testDeleteSuccess(){
        // Given
        Loans loanee = new Loans();

        loanee.setLoaneeId(1);
        loanee.setLoaneeName("John Doe");
        loanee.setLoaneeInstitution("National Science Institute");
        loanee.setLoaneeEmail("johndoe@example.com");
        loanee.setLoaneeAddress("123 Science Lane, Research City, RC 45678");
        loanee.setLoanStartdate("2024-01-01");
        loanee.setLoanDuedate("2024-06-30");
        loanee.setTrackingNumber("TN123456789");
        loanee.setLoaneeNotes("Please handle with care, especially the fragile samples.");
        loanee.setExtraFiles("loan_agreement.pdf, sample_list.xlsx");
        loanee.setMeteorites(meteorites);

        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        meteorite1.setLoanee(loanee);
        meteorite2.setLoanee(loanee);

        given(this.loansRepository.findById(1)).willReturn(Optional.of(loanee));
        doNothing().when(this.loansRepository).deleteById(1);

        // When
        this.loansService.delete(1);

        // Then
        verify(loansRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNotFound(){
        // Given
        given(this.loansRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.loansService.delete(1);
        });

        // Then
        verify(this.loansRepository, times(1)).findById(1);
    }
}