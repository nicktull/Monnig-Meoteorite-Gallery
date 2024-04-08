package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Loans implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Automatically generate an id
    private Integer loaneeId;

    private String loaneeName;

    private String loaneeInstitution;

    private String loaneeEmail;

    private String loaneeAddress;

    private String loanStartdate;

    private String loanDuedate;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "loanee")
    private List<Meteorite> meteorites = new ArrayList<>();

    public Loans(){

    }

    private String trackingNumber;

    private String loaneeNotes;

    private String extraFiles;

    //Getters and Setters


    public Integer getLoaneeId() {
        return loaneeId;
    }

    public void setLoaneeId(Integer loaneeId) {
        this.loaneeId = loaneeId;
    }

    public String getLoaneeName() {
        return loaneeName;
    }

    public void setLoaneeName(String loaneeName) {
        this.loaneeName = loaneeName;
    }

    public String getLoaneeInstitution() {
        return loaneeInstitution;
    }

    public void setLoaneeInstitution(String loaneeInstitution) {
        this.loaneeInstitution = loaneeInstitution;
    }

    public String getLoaneeEmail() {
        return loaneeEmail;
    }

    public void setLoaneeEmail(String loaneeEmail) {
        this.loaneeEmail = loaneeEmail;
    }

    public String getLoaneeAddress() {
        return loaneeAddress;
    }

    public void setLoaneeAddress(String loaneeAddress) {
        this.loaneeAddress = loaneeAddress;
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

    public List<Meteorite> getMeteorites() {
        return meteorites;
    }

    public void setMeteorites(List<Meteorite> meteorites) {
        this.meteorites = meteorites;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getLoaneeNotes() {
        return loaneeNotes;
    }

    public void setLoaneeNotes(String loaneeNotes) {
        this.loaneeNotes = loaneeNotes;
    }

    public String getExtraFiles() {
        return extraFiles;
    }

    public void setExtraFiles(String extraFiles) {
        this.extraFiles = extraFiles;
    }

    public void addMeteorite(Meteorite meteorite) {
        meteorite.setLoanee(this);
        this.meteorites.add(meteorite);
    }

    public Integer getNumberOfMeteorites() {
        return this.meteorites.size();
    }

    public void removeAllMeteorites() {
        this.meteorites.forEach(meteorite -> meteorite.setLoanee(null));
        this.meteorites.clear();
    }
}
