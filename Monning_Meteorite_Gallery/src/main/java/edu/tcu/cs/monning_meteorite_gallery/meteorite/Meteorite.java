package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import jakarta.persistence.Id;

public class Meteorite {

    @Id
    private String MonnigNumber;

    private String Name;

    private String Country;

    private String MClass;

    private String Group;

    private int year;

    private double weight;

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

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
