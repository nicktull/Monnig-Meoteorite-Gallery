package edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter;


import edu.tcu.cs.monning_meteorite_gallery.meteorite.converter.MeteoriteToMeteoriteDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistory;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto.SampleHistoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SampleHistoryToSampleHistoryDtoConverter implements Converter <SampleHistory, SampleHistoryDto>{

    private final MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter;

    public SampleHistoryToSampleHistoryDtoConverter(MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter) {
        this.meteoriteToMeteoriteDtoConverter = meteoriteToMeteoriteDtoConverter;
    }

    @Override
    public SampleHistoryDto convert(SampleHistory source) {
        SampleHistoryDto sampleHistoryDto = new SampleHistoryDto(
                    source.getId(),
                    source.getSampleDate(),
                    source.getSampleCategory(),
                    source.getSampleNotes(),
                    source.getMeteor() != null
                                ? this.meteoriteToMeteoriteDtoConverter.convert(source.getMeteor())
                                :null);
                    return sampleHistoryDto;

    }
}
