package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.IdWorker.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeteoriteService {
    private final MeteoriteRepository meteoriteRepository;

    private final IdWorker idWorker; //    private final IdWorker idWorker; no beans?

    public MeteoriteService(MeteoriteRepository meteoriteRepository, IdWorker idWorker) {  // Need to come back and find the beans
        this.meteoriteRepository = meteoriteRepository;
        this.idWorker = idWorker;
    }

    public Meteorite findByID(String meteoriteId){
        return this.meteoriteRepository.findById(meteoriteId)
                //orElseThrow means get object or throw exception
                .orElseThrow(()-> new MeteoriteNotFoundException(meteoriteId));
    }

    public Meteorite save (Meteorite newMeteorite){
        newMeteorite.setMonnigNumber(idWorker.nextId() + "");
        return this.meteoriteRepository.save(newMeteorite);
    }

    public void delete(String meteoriteId){
        this.meteoriteRepository.findById(meteoriteId).orElseThrow(() -> new MeteoriteNotFoundException(meteoriteId));
        this.meteoriteRepository.deleteById(meteoriteId);
    }
}

