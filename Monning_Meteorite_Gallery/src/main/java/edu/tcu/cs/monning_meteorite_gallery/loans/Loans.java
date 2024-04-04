package edu.tcu.cs.monning_meteorite_gallery.loans;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Loans implements Serializable {
    @Id
    private String loaneeName;

    private String loaneInstitution;

    private String Email;

    private String Address;

    private String loanStartdate;

    private String loanDuedate;

    private String loanedMonning;

    private String trackingNumber;

    private String notes;

    private String extraFiles;

    public String getLoaneeName() {
        return loaneeName;
    }

    public void setLoaneeName(String loaneeName) {
        this.loaneeName = loaneeName;
    }

    public String getLoaneInstitution() {
        return loaneInstitution;
    }

    public void setLoaneInstitution(String loaneInstitution) {
        this.loaneInstitution = loaneInstitution;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLoanStartdate() {
        return loanStartdate;
    }

    public void setLoanStartdate(String loanStartdate) {
        this.loanStartdate = loanStartdate;
    }

    public String getLoanDuedate() {
        return loanDuedate;
    }

    public void setLoanDuedate(String loanDuedate) {
        this.loanDuedate = loanDuedate;
    }

    public String getLoanedMonning() {
        return loanedMonning;
    }

    public void setLoanedMonning(String loanedMonning) {
        this.loanedMonning = loanedMonning;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getExtraFiles() {
        return extraFiles;
    }

    public void setExtraFiles(String extraFiles) {
        this.extraFiles = extraFiles;
    }
}
