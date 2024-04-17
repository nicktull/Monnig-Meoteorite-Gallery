package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class SampleHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Automatically generate an id
    private String id;

    private String sampleDate;

    private String sampleCategory;

    private String sampleNotes;

    @ManyToOne
    private Meteorite meteor;

    public SampleHistory(){
    }

    public String getId(){ return id;}

    public void setId(String id) { this.id = id; }

    public String getSampleDate() {return sampleDate;}

    public void setSampleDate(String sampleDate) {this.sampleDate = sampleDate;}

    public String getSampleCategory() {return sampleCategory;}

    public void setSampleCategory(String sampleCategory) {this.sampleCategory = sampleCategory;}

    public String getSampleNotes() {return sampleNotes;}

    public void setSampleNotes(String sampleNotes) {this.sampleNotes = sampleNotes;}

    public Meteorite getMeteor(){ return meteor; }

    public void setMeteor(Meteorite meteor) { this.meteor = meteor; }


}
