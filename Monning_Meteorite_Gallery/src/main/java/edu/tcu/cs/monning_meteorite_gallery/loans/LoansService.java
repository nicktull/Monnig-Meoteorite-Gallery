package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoansService {
    private final LoansRepository loansRepository;

    private final MeteoriteRepository meteoriteRepository;

    public LoansService(LoansRepository loansRepository, MeteoriteRepository meteoriteRepository) {
        this.loansRepository = loansRepository;
        this.meteoriteRepository = meteoriteRepository;
    }

    public List<Loans> getAllLoans() {return this.loansRepository.findAll();}

    public Loans findById (Integer loaneeId){
        return this.loansRepository.findById(loaneeId)
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loaneeId));
    }

    public Page<Loans> findAll(Pageable pageable){
        return this.loansRepository.findAll(pageable);
    }

    public Loans save (Loans newLoanee) {
        return this.loansRepository.save(newLoanee);
    }

    public void delete (Integer loaneeId) {
        Loans loanToBeDeleted = this.loansRepository.findById(loaneeId)
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loaneeId));

        loanToBeDeleted.removeAllMeteorites();
        this.loansRepository.deleteById(loaneeId);
    }

    public Loans update(Integer loaneeId, Loans newLoanee) {
        return this.loansRepository.findById(loaneeId)
                .map(oldLoanee -> {
                    oldLoanee.setLoaneeName(newLoanee.getLoaneeName());
                    oldLoanee.setLoaneeInstitution(newLoanee.getLoaneeInstitution());
                    oldLoanee.setLoaneeEmail(newLoanee.getLoaneeEmail());
                    oldLoanee.setLoaneeAddress(newLoanee.getLoaneeAddress());
                    oldLoanee.setLoanStartdate(newLoanee.getLoanStartdate());
                    oldLoanee.setLoanDuedate(newLoanee.getLoanDuedate());
                    oldLoanee.setTrackingNumber(newLoanee.getTrackingNumber());
                    oldLoanee.setLoaneeNotes(newLoanee.getLoaneeNotes());
                    oldLoanee.setExtraFiles(newLoanee.getExtraFiles());
                    return this.loansRepository.save(oldLoanee);
                })
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loaneeId));
    }

    public void loanMeteorite(Integer loaneeId, String meteoriteId) {
        // Find the meteorite by id from DB
        Meteorite meteoriteToBeLoaned = this.meteoriteRepository.findById(meteoriteId)
                .orElseThrow(() -> new ObjectNotFoundException("meteorite", meteoriteId));

        // Find loanee by id from db
        Loans loan = this.loansRepository.findById(loaneeId)
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loaneeId));

        // loan meteorite
        // Check if meteorite is already loaned to someone.
        if(meteoriteToBeLoaned.getLoanee() != null){
            meteoriteToBeLoaned.getLoanee().removeLoanee(meteoriteToBeLoaned);
        }
        loan.addMeteorite(meteoriteToBeLoaned);
    }

    public void archive(Integer loaneeId) {
        //Find loanee by id from db
        Loans loanToBeArchived = this.loansRepository.findById(loaneeId)
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loaneeId));

        loanToBeArchived.setStatus();
    }

//    public List<Loans> filterLoans(Predicate<Loans> criteria) {
//        List<Loans> allLoans = findAll(); // This should call the existing method that returns all loans
//        return allLoans.stream().filter(criteria).collect(Collectors.toList());
//    }
}
