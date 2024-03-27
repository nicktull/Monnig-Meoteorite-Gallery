package edu.tcu.cs.monning_meteorite_gallery.System;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final MeteoriteRepository meteoriteRepository;

    public DBDataInitializer(MeteoriteRepository meteoriteRepository) {
        this.meteoriteRepository = meteoriteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Meteorite Abott = new Meteorite();
        Abott.setName("Abott");
        Abott.setMonnigNumber("M398.1");
        Abott.setCountry("USA");
        Abott.setMClass("Ordinary Chondrite");
        Abott.setMGroup("H");
        Abott.setYearFound("1951");
        Abott.setWeight("325.1");

        Meteorite Abee = new Meteorite();
        Abee.setName("Abee");
        Abee.setMonnigNumber("M499.2");
        Abee.setCountry("Canada");
        Abee.setMClass("Enstatite Chondrite");
        Abee.setMGroup("EH");
        Abee.setYearFound("1952");
        Abee.setWeight("785.9");

        Meteorite Abernathy = new Meteorite();
        Abernathy.setName("Abernathy");
        Abernathy.setMonnigNumber("M239.1.1");
        Abernathy.setCountry("USA");
        Abernathy.setMClass("Ordinary Chondrite");
        Abernathy.setMGroup("L");
        Abernathy.setYearFound("1941");
        Abernathy.setWeight("453.1");

        meteoriteRepository.save(Abott);
        meteoriteRepository.save(Abee);
        meteoriteRepository.save(Abernathy);
    }
}
