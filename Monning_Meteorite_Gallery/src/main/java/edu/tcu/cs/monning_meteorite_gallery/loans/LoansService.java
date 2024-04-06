package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LoansService {
    private final LoansRepository loansRepository;

    private final IdWorker idWorker;

    public LoansService(LoansRepository loansRepository, IdWorker idWorker) {
        this.loansRepository = loansRepository;
        this.idWorker = idWorker;
    }

    public List<Loans> getAllLoans() {return this.loansRepository.findAll();}

    public Loans findById (String loaneeId){
        return this.loansRepository.findById(loaneeId)
                .orElseThrow(() -> new LoansNotFoundException(loaneeId));
    }

    public Loans save (Loans newLoanee) {
        newLoanee.setLoaneeId(idWorker.nextId() + "");
        return this.loansRepository.save(newLoanee);
    }

    public void delete (String loaneeId) {
        this.loansRepository.findById(loaneeId).orElseThrow(() -> new LoansNotFoundException(loaneeId));
        this.loansRepository.deleteById(loaneeId);
    }

    public Loans update (String loaneeId, Loans newLoanee) {
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
                    oldLoanee.setMeteorites(newLoanee.getMeteorites()); //may need adjusting because of one to many
                    oldLoanee.setExtraFiles(newLoanee.getExtraFiles());
                    return this.loansRepository.save(oldLoanee);
                })
                .orElseThrow(() -> new LoansNotFoundException(loaneeId));
    }
}
