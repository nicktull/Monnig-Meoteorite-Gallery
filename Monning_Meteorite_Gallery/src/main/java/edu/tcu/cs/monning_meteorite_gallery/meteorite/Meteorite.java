package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistory;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistoryService;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meteorite implements Serializable {

    @Id
    private String monnigNumber;

    private String name;

    private String country;

    private String mClass;

    private String mGroup;

    private String yearFound;

    private String weight;

    private String loanStatus = "Available";

    @ManyToOne
    @JsonIgnore
    private Loans loanee;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "meteor")
    private List<SampleHistory> sampleHistory = new ArrayList<>();   // One meteor has many history


    public Meteorite() {}

    public String getMonnigNumber() {
        return monnigNumber;
    }

    public void setMonnigNumber(String monnigNumber) {
        this.monnigNumber = monnigNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMClass() {
        return mClass;
    }

    public void setMClass(String mClass) {
        this.mClass = mClass;
    }

    public String getMGroup() {
        return mGroup;
    }

    public void setMGroup(String mGroup) {
        this.mGroup = mGroup;
    }

    public String getYearFound() {
        return yearFound;
    }

    public void setYearFound(String yearFound) {
        this.yearFound = yearFound;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus() {
        if(this.loanee != null){
            this.loanStatus = "Unavailable";
        } else{
            this.loanStatus = "Available";
        }
    }

    public Loans getLoanee() {
        return loanee;
    }

    public void setLoanee(Loans loanee) {
        this.loanee = loanee;
    }

    public List<SampleHistory> getSampleHistory(){ return sampleHistory; }

    public void setSampleHistory(List<SampleHistory> sampleHistory) { this.sampleHistory = sampleHistory; }

    public void addSampleHistory(SampleHistory sampleHistory){
        sampleHistory.setMeteor(this);
        this.sampleHistory.add(sampleHistory);
    }

    public void removeSampleHistory(SampleHistory sampleHistoryToBeAssigned) {
        sampleHistoryToBeAssigned.setMeteor(null);
        this.sampleHistory.remove(sampleHistoryToBeAssigned);
    }


}