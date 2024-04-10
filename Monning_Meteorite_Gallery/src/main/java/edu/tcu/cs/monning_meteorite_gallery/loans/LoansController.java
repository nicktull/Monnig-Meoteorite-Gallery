package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.loans.converter.LoansDtoToLoansConverter;
import edu.tcu.cs.monning_meteorite_gallery.loans.converter.LoansToLoansDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/loans")
public class LoansController {

    private final LoansService loansService;
    private final LoansToLoansDtoConverter loansToLoansDtoConverter;
    private final LoansDtoToLoansConverter loansDtoToLoansConverter;

    public LoansController(LoansService loansService, LoansToLoansDtoConverter loansToLoansDtoConverter, LoansDtoToLoansConverter loansDtoToLoansConverter) {
        this.loansService = loansService;
        this.loansToLoansDtoConverter = loansToLoansDtoConverter;
        this.loansDtoToLoansConverter = loansDtoToLoansConverter;
    }

    @GetMapping("/{loansId}")
    public Result findLoansById(@PathVariable Integer loansId) {
        Loans foundLoans = this.loansService.findById(loansId);
        LoansDto loansDto = this.loansToLoansDtoConverter.convert(foundLoans);
        return new Result(true, StatusCode.SUCCESS, "Found", loansDto);
    }
    @GetMapping
    public Result findAllLoans() {
        List<Loans> foundLoans = this.loansService.findAll();

        // Convert foundLoans to a list of loanDtos
        List<LoansDto> loanDtos = foundLoans.stream()
                .map(this.loansToLoansDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Found All Loans",loanDtos);
    }

    @GetMapping("/activeLoans")
    public Result findActiveLoans(){
        List<Loans> foundLoans = this.loansService.findAll();

        List<Loans> archivedLoans = foundLoans.stream()
                .filter(loan -> loan.getStatus().equals("Active"))
                .toList();

        List<LoansDto> loanDtos = archivedLoans.stream()
                .map(this.loansToLoansDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Found All Active Loans", loanDtos);
    }

    @GetMapping("/archivedLoans")
    public Result findArchivedLoans() {
        List<Loans> foundLoans = this.loansService.findAll();

        List<Loans> archivedLoans = foundLoans.stream()
                .filter(loan -> loan.getStatus().equals("Archived"))
                .toList();

        List<LoansDto> loanDtos = archivedLoans.stream()
                .map(this.loansToLoansDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Found All Archived Loans", loanDtos);
    }

    @PostMapping
    public Result addLoans(@Valid @RequestBody LoansDto loansDto) {
        Loans newLoans = this.loansDtoToLoansConverter.convert(loansDto);
        assert newLoans != null; //Make sure that newLoans cannot be null
        Loans savedLoans = this.loansService.save(newLoans);
        LoansDto savedLoansDto = this.loansToLoansDtoConverter.convert(savedLoans);
        return new Result(true, StatusCode.SUCCESS, "Saved", savedLoansDto);
    }

    @PutMapping("/{loansId}")
    public Result updateLoans(@PathVariable Integer loansId, @Valid @RequestBody LoansDto loansDto) {
        Loans newLoan = this.loansDtoToLoansConverter.convert(loansDto);
        Loans updatedLoan = this.loansService.update(loansId, newLoan);
        LoansDto updatedLoansDto = this.loansToLoansDtoConverter.convert(updatedLoan);
        return new Result(true, StatusCode.SUCCESS, "Updated", updatedLoansDto);
    }

//    //Perhaps a new dto specifically for updating status is needed
//    @PutMapping("/archive/{loansId}")
//    public Result archiveLoan(@PathVariable Integer loansId, @Valid @RequestBody LoansDto loansDto){
//        Loans newLoan = this.loansDtoToLoansConverter.convert(loansDto);
//        Loans updatedLoan = this.loansService.archive(loansId, newLoan);
//        LoansDto updatedLoansDto = this.loansToLoansDtoConverter.convert(updatedLoan);
//        return new Result(true, StatusCode.SUCCESS, "Updated", updatedLoansDto);
//    }

    /**
     * Loans a meteorite to a loanee
     * if the meteorite is already being loaned, it will remove the meteorite from
     * the previous loanee and add it to the specified loanee
    **/
    @PutMapping("/{loanId}/meteorites/{meteoriteId}")
    public Result loanMeteorite(@PathVariable Integer loanId, @PathVariable String meteoriteId){
        this.loansService.loanMeteorite(loanId, meteoriteId);
        return new Result(true, StatusCode.SUCCESS, "Loan Meteorite Success");
    }

    //May not be needed
    @DeleteMapping("/{loansId}")
    public Result deleteLoans(@PathVariable Integer loansId) {
        this.loansService.delete(loansId);
        return new Result(true, StatusCode.SUCCESS, "Deleted");
    }
}
