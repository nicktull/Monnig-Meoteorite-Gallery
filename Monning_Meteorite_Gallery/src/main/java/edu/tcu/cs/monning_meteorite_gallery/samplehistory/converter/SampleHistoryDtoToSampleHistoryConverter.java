package edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter;

import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistory;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto.SampleHistoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SampleHistoryDtoToSampleHistoryConverter implements Converter<SampleHistoryDto, SampleHistory> {

    @Override
    public SampleHistory convert(SampleHistoryDto source) {
        SampleHistory sampleHistory = new SampleHistory();
            sampleHistory.setSampleHistoryId(source.id());
            sampleHistory.setSampleDate(source.sampleDate());
            sampleHistory.setSampleCategory(source.sampleCategory());
            sampleHistory.setSampleNotes(source.sampleNotes());
            return sampleHistory;
    }

}
