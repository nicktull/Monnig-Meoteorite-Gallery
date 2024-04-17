package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SampleHistoryService {

    private final SampleHistoryRepository sampleHistoryRepository;

    public SampleHistoryService(SampleHistoryRepository sampleHistoryRepository){
        this.sampleHistoryRepository = sampleHistoryRepository;
    }

    public SampleHistory findById(String sampleHistoryId){
        return this.sampleHistoryRepository.findById(sampleHistoryId)
                .orElseThrow(() -> new ObjectNotFoundException("sample history", sampleHistoryId));
    }

    public SampleHistory save(SampleHistory newEntry) {
        return this.sampleHistoryRepository.save(newEntry);
    }

    public void delete(String sampleHistoryId) {
        this.sampleHistoryRepository.findById(sampleHistoryId)
                .orElseThrow(() -> new ObjectNotFoundException("sample history", sampleHistoryId));
        this.sampleHistoryRepository.deleteById(sampleHistoryId);
    }

}
