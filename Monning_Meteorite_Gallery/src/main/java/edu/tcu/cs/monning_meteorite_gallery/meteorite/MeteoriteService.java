package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.IdWorker.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeteoriteService {
    private final MeteoriteRepository meteoriteRepository;

//    private final IdWorker idWorker; no beans?

    public MeteoriteService(MeteoriteRepository meteoriteRepository) {
        this.meteoriteRepository = meteoriteRepository;
    }

    public Meteorite findByID(String meteoriteId){
        return this.meteoriteRepository.findById(meteoriteId)
                //orElseThrow means get object or throw exception
                .orElseThrow(()-> new MeteoriteNotFoundException(meteoriteId));
    }
}
