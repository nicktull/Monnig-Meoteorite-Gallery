package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeteoriteService {
    private final MeteoriteRepository meteoriteRepository;

    private final IdWorker idWorker;

    //Idworker generates an id
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
                .orElseThrow(() -> new MeteoriteNotFoundException(meteoriteId));
    }
}

