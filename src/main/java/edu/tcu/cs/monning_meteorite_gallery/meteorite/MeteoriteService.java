package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.utils.IdWorker;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistory;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistoryRepository;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MeteoriteService {
    private final MeteoriteRepository meteoriteRepository;

    private final SampleHistoryService sampleHistoryService;

    private final IdWorker idWorker;

    //Idworker generates an id
    public MeteoriteService(MeteoriteRepository meteoriteRepository, IdWorker idWorker, SampleHistoryRepository sampleHistoryRepository, SampleHistoryService sampleHistoryService) {
        this.meteoriteRepository = meteoriteRepository;
        this.idWorker = idWorker;
        this.sampleHistoryService = sampleHistoryService;

    }

    public Meteorite findByID(String meteoriteId){
        return this.meteoriteRepository.findById(meteoriteId)
                //orElseThrow means get object or throw exception
                .orElseThrow(()-> new ObjectNotFoundException("meteorite", meteoriteId));
    }

    public Page<Meteorite> findAll(Pageable pageable) {return this.meteoriteRepository.findAll(pageable);}

    public Meteorite save (Meteorite newMeteorite){
        newMeteorite.setMonnigNumber(idWorker.nextId() + "");
        return this.meteoriteRepository.save(newMeteorite);
    }

    public void delete(String meteoriteId){
        Meteorite meteoriteToBeDeleted = this.meteoriteRepository.findById(meteoriteId).orElseThrow(() -> new ObjectNotFoundException("meteorite", meteoriteId));
        List<SampleHistory> history = meteoriteToBeDeleted.getSampleHistory();
        meteoriteToBeDeleted.removeSampleHistory();
        history.forEach(sampleHistory -> this.sampleHistoryService.delete(sampleHistory.getSampleHistoryId()));
        this.meteoriteRepository.deleteById(meteoriteId);
    }

    public Meteorite update(String meteoriteId, Meteorite update){
        return this.meteoriteRepository.findById(meteoriteId)
                .map(oldMeteorite ->{
                    oldMeteorite.setName(update.getName());
                    oldMeteorite.setYearFound(update.getYearFound());
                    oldMeteorite.setCountry(update.getCountry());
                    oldMeteorite.setMClass(update.getMClass());
                    oldMeteorite.setMGroup(update.getMGroup());
                    oldMeteorite.setWeight(update.getWeight());
                    oldMeteorite.setLoanee(update.getLoanee()); //may need to adjust as each meteorite can only be loaned to one person
                    return this.meteoriteRepository.save(oldMeteorite);
                })
                .orElseThrow(() -> new ObjectNotFoundException("meteorite", meteoriteId));
    }

    public Meteorite subsample(String meteoriteId, Meteorite oldMeteorite){
        return this.meteoriteRepository.findById(meteoriteId)
                .map(subsample ->{
                    subsample.setName(oldMeteorite.getName());
                    subsample.setYearFound(oldMeteorite.getYearFound());
                    subsample.setCountry(oldMeteorite.getCountry());
                    subsample.setMClass(oldMeteorite.getMClass());
                    subsample.setMGroup(oldMeteorite.getMGroup());
                    subsample.setWeight(oldMeteorite.getWeight());
                    subsample.setLoanee(oldMeteorite.getLoanee());
                    return this.meteoriteRepository.save(subsample);
                })
                .orElseThrow(() -> new ObjectNotFoundException("meteorite", meteoriteId));
    }

}

