package edu.tcu.cs.monning_meteorite_gallery.meteorite.dto;

import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import jakarta.validation.constraints.NotEmpty;

public record MeteoriteDto(
        @NotEmpty(message = "Monnig Number is Required")
        String MonnigNumber,
        @NotEmpty(message = "Name is Required")
        String Name,
        @NotEmpty(message = "Country is Required")
        String Country,
        @NotEmpty(message = "Class is Required")
        String MClass,
        @NotEmpty(message = "Group is Required")
        String MGroup,
        @NotEmpty(message = "Year Found is Required")
        String yearFound,
        @NotEmpty(message = "Weight is Required")
        String weight,
        String loanStatus
) {
}

