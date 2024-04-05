package edu.tcu.cs.monning_meteorite_gallery.System;

import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.loans.LoansRespository;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final MeteoriteRepository meteoriteRepository;
    private final LoansRespository loansRespository;

    public DBDataInitializer(MeteoriteRepository meteoriteRepository, LoansRespository loansRespository) {
        this.meteoriteRepository = meteoriteRepository;
        this.loansRespository = loansRespository;
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

        Loans johnDoe = new Loans();
        johnDoe.setLoaneeName("John Doe");
        johnDoe.setLoaneeInstitution("XYZ University");
        johnDoe.setLoaneeEmail("john.doe@example.com");
        johnDoe.setLoaneeAddress("123 Main St, Anytown, USA");
        johnDoe.setLoanStartdate("2024-01-01");
        johnDoe.setLoanDuedate("2024-12-31");
        johnDoe.setTrackingNumber("TRACK123456");
        johnDoe.setLoaneeNotes("Notes about the loan");
        johnDoe.setExtraFiles("path/to/extra/files");
        johnDoe.addMeteorite(Abernathy);
        johnDoe.addMeteorite(Abott);

        meteoriteRepository.save(Abee);

        loansRespository.save(johnDoe);
    }
}
