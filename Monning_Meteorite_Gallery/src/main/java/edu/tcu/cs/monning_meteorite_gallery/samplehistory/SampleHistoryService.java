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

    public SampleHistory findById(Integer id){
        return this.sampleHistoryRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ObjectNotFoundException("sample history", id));
    }

    public SampleHistory save(SampleHistory newEntry) {
        return this.sampleHistoryRepository.save(newEntry);
    }

    public void delete(Integer id) {
        this.sampleHistoryRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ObjectNotFoundException("sample history", id));
        this.sampleHistoryRepository.deleteById(String.valueOf(id));
    }

}
