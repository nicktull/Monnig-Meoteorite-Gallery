package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Loans> findAll(){
        return this.loansRepository.findAll();
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

    public void loanMeteorite(Integer loanId, String meteoriteId) {
        // Find the meteorite by id from DB
        Meteorite meteoriteToBeLoaned = this.meteoriteRepository.findById(meteoriteId)
                .orElseThrow(() -> new ObjectNotFoundException("meteorite", meteoriteId));

        // Find loanee by id from db
        Loans loan = this.loansRepository.findById(loanId)
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loanId));

        // loan meteorite
        // Check if meteorite is already loaned to someone.
        if(meteoriteToBeLoaned.getLoanee() != null){
            meteoriteToBeLoaned.getLoanee().removeLoanee(meteoriteToBeLoaned);
        }
        loan.addMeteorite(meteoriteToBeLoaned);
    }

    public Loans archive(Integer loaneeId, Loans newLoanee) {
        return this.loansRepository.findById(loaneeId)
                .map(oldLoanee ->{
                    oldLoanee.setStatus();
                    return this.loansRepository.save(oldLoanee);
                })
                .orElseThrow(() -> new ObjectNotFoundException("loanee", loaneeId));
    }

}
