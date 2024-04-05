package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

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

    @ManyToOne
    private Loans loanee;

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

    public Loans getLoanee() {
        return loanee;
    }

    public void setLoanee(Loans loanee) {
        this.loanee = loanee;
    }
}