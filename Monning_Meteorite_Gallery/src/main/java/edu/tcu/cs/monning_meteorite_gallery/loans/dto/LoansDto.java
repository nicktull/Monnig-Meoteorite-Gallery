package edu.tcu.cs.monning_meteorite_gallery.loans.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoansDto(

    Integer loanId,

    @NotEmpty(message = "Loanee Name is Required")
    String loaneeName,

    @NotEmpty(message = "Loanee Institution is Required")
    String loaneeInstitution,

    @NotEmpty(message = "Email is Required")
    String loaneeEmail,

    @NotEmpty(message = "Address is Required")
    String loaneeAddress,

    @NotEmpty(message = "Loan Start Date is Required")
    String loanStartdate,

    @NotEmpty(message = "Loan Due Date is Required")
    String loanDuedate,

    @NotEmpty(message = "Tracking Number is Required")
    String trackingNumber,

    String loaneeNotes,

    String extraFiles,

    Integer numberOfMeteorites) {

}
