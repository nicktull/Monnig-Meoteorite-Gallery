package edu.tcu.cs.monning_meteorite_gallery.System;

import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.loans.LoansRepository;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.MeteoriteUser;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.UserService;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistory;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final MeteoriteRepository meteoriteRepository;
    private final LoansRepository loansRepository;
    private final SampleHistoryRepository sampleHistoryRepository;
    private final UserService userService;

    public DBDataInitializer(MeteoriteRepository meteoriteRepository, LoansRepository loansRepository, SampleHistoryRepository sampleHistoryRepository, UserService userService) {
        this.meteoriteRepository = meteoriteRepository;
        this.loansRepository = loansRepository;
        this.sampleHistoryRepository = sampleHistoryRepository;
        this.userService = userService;
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
        Abernathy.setMonnigNumber("M239.1");
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
//        Majima.setStatus("Archived"); default should be active
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
//        kiryu.setStatus("Active"); default should be active
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

        SampleHistory entry1 = new SampleHistory();
        entry1.setSampleDate("12/12/2025");
        entry1.setSampleCategory("Created");
        entry1.setSampleNotes("Adding new meteoeite to the system, meow.");
        entry1.setMeteor(Abernathy);

        SampleHistory entry2 = new SampleHistory();
        entry2.setSampleDate("11/11/2024");
        entry2.setSampleCategory("Deleted");
        entry2.setSampleNotes("Deleting meteorite from database in 10 days, ruff.");
        entry2.setMeteor(Abee);

        sampleHistoryRepository.save(entry1);
        sampleHistoryRepository.save(entry2);


        // Create some users.
        MeteoriteUser u1 = new MeteoriteUser();
        u1.setId(1);
        u1.setUsername("john@tcu.edu");
        u1.setPassword("123456");
        u1.setEnabled(true);
        u1.setRoles("admin user");

        MeteoriteUser u2 = new MeteoriteUser();
        u2.setId(2);
        u2.setUsername("eric@tcu.edu");
        u2.setPassword("654321");
        u2.setEnabled(true);
        u2.setRoles("user");

        MeteoriteUser u3 = new MeteoriteUser();
        u3.setId(3);
        u3.setUsername("tom@tcu.edu");
        u3.setPassword("qwerty");
        u3.setEnabled(false);
        u3.setRoles("user");

        this.userService.save(u1);
        this.userService.save(u2);
        this.userService.save(u3);
    }
}
