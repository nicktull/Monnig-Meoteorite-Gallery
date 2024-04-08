package edu.tcu.cs.monning_meteorite_gallery.System;

import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.loans.LoansRepository;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final MeteoriteRepository meteoriteRepository;
    private final LoansRepository loansRepository;

    public DBDataInitializer(MeteoriteRepository meteoriteRepository, LoansRepository loansRepository) {
        this.meteoriteRepository = meteoriteRepository;
        this.loansRepository = loansRepository;
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

        Meteorite abuMoharek = new Meteorite();
        abuMoharek.setName("AbuMoharek");
        abuMoharek.setMonnigNumber("M787.1");
        abuMoharek.setCountry("Egypt");
        abuMoharek.setMClass("Ordinary Chondrite");
        abuMoharek.setMGroup("H");
        abuMoharek.setYearFound("1997");
        abuMoharek.setWeight("56.4");

        Loans Majima = new Loans();
//        Majima.setLoaneeId(1); id isnt needed due to @GeneratedValue in Loans
        Majima.setLoaneeName("Goro Majima");
        Majima.setLoaneeInstitution("Yakuza Foundation");
        Majima.setLoaneeEmail("majima.goro@example.com");
        Majima.setLoaneeAddress("1-2-3 Sotenbori, Osaka");
        Majima.setLoanStartdate("2024-05-01");
        Majima.setLoanDuedate("2024-12-01");
        Majima.setTrackingNumber("TN987654321");
        Majima.setLoaneeNotes("KIRYU-CHAN!!!!");
        Majima.setExtraFiles("agreement.pdf, item_list.xlsx");
        Majima.addMeteorite(Abernathy);
        Majima.addMeteorite(Abott);

        Loans kiryu = new Loans();
//        kiryu.setLoaneeId(2);
        kiryu.setLoaneeName("Kazuma Kiryu");
        kiryu.setLoaneeInstitution("Yakuza Foundation");
        kiryu.setLoaneeEmail("kazuma.kiryu@example.com");
        kiryu.setLoaneeAddress("1-2-3 Kamurocho, Tokyo");
        kiryu.setLoanStartdate("2024-04-01");
        kiryu.setLoanDuedate("2024-10-01");
        kiryu.setTrackingNumber("TN123456789");
        kiryu.setLoaneeNotes("Life is like a trampoline. The lower you fall, the higher you go.");
        kiryu.setExtraFiles("Kakattekoi.pdf, item_list.xlsx");
        kiryu.addMeteorite(Abee);

        abuMoharek.setLoanStatus();
        Abee.setLoanStatus();
        Abernathy.setLoanStatus();
        Abott.setLoanStatus();

        meteoriteRepository.save(abuMoharek);

        loansRepository.save(Majima);
        loansRepository.save(kiryu);

    }
}
