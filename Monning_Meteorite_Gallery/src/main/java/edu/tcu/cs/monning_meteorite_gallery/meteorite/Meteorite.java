package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Meteorite implements Serializable {

    @Id
    private String MonnigNumber;

    private String Name;

    private String Country;

    private String MClass;

    private String MGroup;

    private String yearFound;

    private String weight;

    public String getMonnigNumber() {
        return MonnigNumber;
    }

    public void setMonnigNumber(String monnigNumber) {
        MonnigNumber = monnigNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getMClass() {
        return MClass;
    }

    public void setMClass(String MClass) {
        this.MClass = MClass;
    }

    public String getMGroup() {
        return MGroup;
    }

    public void setMGroup(String MGroup) {
        this.MGroup = MGroup;
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

}
