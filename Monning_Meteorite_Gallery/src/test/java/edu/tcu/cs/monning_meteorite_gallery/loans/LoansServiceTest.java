package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LoansServiceTest {

    @Mock
    LoansRepository loansRepository;

    @InjectMocks
    LoansService loansService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given
        Loans loanee = new Loans();

        loanee.setLoaneeId("L001");
        loanee.setLoaneeName("John Doe");
        loanee.setLoaneeInstitution("National Science Institute");
        loanee.setLoaneeEmail("johndoe@example.com");
        loanee.setLoaneeAddress("123 Science Lane, Research City, RC 45678");
        loanee.setLoanStartdate("2024-01-01");
        loanee.setLoanDuedate("2024-06-30");
        loanee.setTrackingNumber("TN123456789");
        loanee.setLoaneeNotes("Please handle with care, especially the fragile samples.");
        loanee.setExtraFiles("loan_agreement.pdf, sample_list.xlsx");

        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        meteorite1.setLoanee(loanee);
        meteorite2.setLoanee(loanee);

        given(loansRepository.findById("L001")).willReturn(Optional.of(loanee));

        // When
        Loans returnedLoan = loansService.findById("L001");

        // Then
        assertThat(returnedLoan.getLoaneeId()).isEqualTo("L001");
        assertThat(returnedLoan.getLoaneeName()).isEqualTo("John Doe");
        assertThat(returnedLoan.getLoaneeInstitution()).isEqualTo("National Science Institute");
        assertThat(returnedLoan.getLoaneeEmail()).isEqualTo("johndoe@example.com");
        assertThat(returnedLoan.getLoaneeAddress()).isEqualTo("123 Science Lane, Research City, RC 45678");
        assertThat(returnedLoan.getLoanStartdate()).isEqualTo("2024-01-01");
        assertThat(returnedLoan.getLoanDuedate()).isEqualTo("2024-06-30");
        assertThat(returnedLoan.getTrackingNumber()).isEqualTo("TN123456789");
        assertThat(returnedLoan.getLoaneeNotes()).isEqualTo("Please handle with care, especially the fragile samples.");
        assertThat(returnedLoan.getExtraFiles()).hasSize(2);
        assertThat(returnedLoan.getMeteorites()).hasSize(2);
        verify(loansRepository, times(1)).findById("L001");
    }

    @Test
    void FindByIdNotFound() {
        // Given
        given(loansRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(()->{
            Loans returnedLoan = loansService.findById("L001");
        });

        // Then
        assertThat(thrown).isInstanceOf(LoansNotFoundException.class).hasMessage("Could not find loanee with id L001");
        verify(loansRepository, times(1)).findById("L001");
    }

    @Test
    void testAddLoanSuccess() {
        Loans loanee = new Loans();

        loanee.setLoaneeId("L001");
        loanee.setLoaneeName("John Doe");
        loanee.setLoaneeInstitution("National Science Institute");
        loanee.setLoaneeEmail("johndoe@example.com");
        loanee.setLoaneeAddress("123 Science Lane, Research City, RC 45678");
        loanee.setLoanStartdate("2024-01-01");
        loanee.setLoanDuedate("2024-06-30");
        loanee.setTrackingNumber("TN123456789");
        loanee.setLoaneeNotes("Please handle with care, especially the fragile samples.");
        loanee.setExtraFiles("loan_agreement.pdf, sample_list.xlsx");

        Meteorite meteorite1 = new Meteorite();
        meteorite1.setMonnigNumber("M001");
        meteorite1.setName("Moon Rock");
        Meteorite meteorite2 = new Meteorite();
        meteorite2.setMonnigNumber("M002");
        meteorite2.setName("Mars Rock");

        meteorite1.setLoanee(loanee);
        meteorite2.setLoanee(loanee);

    }
}