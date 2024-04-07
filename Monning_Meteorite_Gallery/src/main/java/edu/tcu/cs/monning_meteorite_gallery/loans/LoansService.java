package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LoansService {
    private final LoansRepository loansRepository;

    public LoansService(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
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

}
