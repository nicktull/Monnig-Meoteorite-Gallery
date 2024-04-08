package edu.tcu.cs.monning_meteorite_gallery.loans.converter;

import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoansDtoToLoansConverter implements Converter<LoansDto, Loans> {

    @Override
    public Loans convert(LoansDto source) {
        Loans loan = new Loans();
            loan.setLoaneeId(source.loanId());
            loan.setLoaneeName(source.loaneeName());
            loan.setLoaneeEmail(source.loaneeEmail());
            loan.setLoaneeAddress(source.loaneeAddress());
            loan.setLoanStartdate(source.loanStartdate());
            loan.setLoanDuedate(source.loanDuedate());
            loan.setMeteorites(source.meteorites());
            loan.setTrackingNumber(source.trackingNumber());
            loan.setLoaneeNotes(source.loaneeNotes());
            loan.setExtraFiles(source.extraFiles());

        return loan;
    }
}
