package edu.tcu.cs.monning_meteorite_gallery.loans.converter;

import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoansToLoansDtoConverter implements Converter<Loans, LoansDto> {

    @Override
    public LoansDto convert(Loans source) {
        LoansDto loansDto;
        loansDto = new LoansDto(
                source.getLoaneeId(),
                source.getLoaneeName(),
                source.getLoaneeInstitution(),
                source.getLoaneeEmail(),
                source.getLoaneeAddress(),
                source.getLoanStartdate(),
                source.getLoanDuedate(),
                source.getTrackingNumber(),
                source.getLoaneeNotes(),
                source.getExtraFiles(),
                source.getNumberOfMeteorites());
        return loansDto;
    }
}
