package edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SampleHistoryDto (Integer id,

    @NotEmpty(message = "Date is Required")
    String sampleDate,

    @NotEmpty(message = "Category is Required")
    String sampleCategory,

    @NotEmpty(message = "Notes is Required")
    String sampleNotes,

    MeteoriteDto meteor){

}
