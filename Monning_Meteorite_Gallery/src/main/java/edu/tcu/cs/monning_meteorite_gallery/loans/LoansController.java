package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.loans.converter.LoansDtoToLoansConverter;
import edu.tcu.cs.monning_meteorite_gallery.loans.converter.LoansToLoansDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;
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

    /**
     *Finds a loan by its Id (LoaneeId located in Loans)
     *
     * @param loansId the Id of the loan to find
     * @return a result containing the found loan, if any
     */
    @GetMapping("/{loansId}")
    public Result findLoansById(@PathVariable Integer loansId) {
        Loans foundLoans = this.loansService.findById(loansId);
        LoansDto loansDto = this.loansToLoansDtoConverter.convert(foundLoans);
        return new Result(true, StatusCode.SUCCESS, "Found", loansDto);
    }

    /**
     * Finds all loans
     *
     * @return a result containing all loans if any
     */
    @GetMapping
    public Result findAllLoans() {
        List<Loans> foundLoans = this.loansService.findAll();

        // Convert foundLoans to a list of loanDtos
        List<LoansDto> loanDtos = foundLoans.stream()
                .map(this.loansToLoansDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Found All Loans",loanDtos);
    }

    @GetMapping("/search")
    public Result searchLoans(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String loanStartdate) {
        Predicate<Loans> statusPredicate = status == null ? loan -> true : loan -> loan.getStatus().equals(status);
        Predicate<Loans> datePredicate = loanStartdate == null ? loan -> true : loan -> loan.getLoanStartdate().equals(loanStartdate);

        List<Loans> filteredLoans = loansService.filterLoans(statusPredicate.and(datePredicate));
        List<LoansDto> loanDtos = filteredLoans.stream()
                .map(loansToLoansDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Filtered Loans", loanDtos);
    }

    /**
     * Returns a sorted list of loans based on whether the status of a loan is "Active"
     *
     * @return
     */
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

    /**
     * Returns a sorted list of loans based on whether the status of a loan is "Archived"
     *
     * @return
     */
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

    /**
     * Adds a loan based on the arguments provided for loansDto
     *
     * @param loansDto contains the body of the post request
     * @return header of the post request
     */
    @PostMapping
    public Result addLoans(@Valid @RequestBody LoansDto loansDto) {
        Loans newLoans = this.loansDtoToLoansConverter.convert(loansDto);
        assert newLoans != null; //Make sure that newLoans cannot be null
        Loans savedLoans = this.loansService.save(newLoans);
        LoansDto savedLoansDto = this.loansToLoansDtoConverter.convert(savedLoans);
        return new Result(true, StatusCode.SUCCESS, "Saved", savedLoansDto);
    }

    /**
     *
     * @param loansId LoaneeId
     * @param loansDto
     * @return
     */
    @PutMapping("/{loansId}")
    public Result updateLoans(@PathVariable Integer loansId, @Valid @RequestBody LoansDto loansDto) {
        Loans newLoan = this.loansDtoToLoansConverter.convert(loansDto);
        Loans updatedLoan = this.loansService.update(loansId, newLoan);
        LoansDto updatedLoansDto = this.loansToLoansDtoConverter.convert(updatedLoan);
        return new Result(true, StatusCode.SUCCESS, "Updated", updatedLoansDto);
    }

    /**
     *
     * @param loansId LoaneeId
     * @return
     */
    @PutMapping("/archive/{loansId}")
    public Result archiveLoan(@PathVariable Integer loansId){
        this.loansService.archive(loansId);
        return new Result(true, StatusCode.SUCCESS, "Archived Loan");
    }

    /**
     * Loans a meteorite to a loanee
     * if the meteorite is already being loaned, it will remove the meteorite from
     * the previous loanee and add it to the specified loanee
    *
     * @param loanId
     * @param meteoriteId
     * @return*/
    @PutMapping("/{loanId}/meteorites/{meteoriteId}")
    public Result loanMeteorite(@PathVariable Integer loanId, @PathVariable String meteoriteId){
        this.loansService.loanMeteorite(loanId, meteoriteId);
        return new Result(true, StatusCode.SUCCESS, "Loan Meteorite Success");
    }

    /**
     *
     * @param loansId
     * @return
     */
    @DeleteMapping("/{loansId}")
    public Result deleteLoans(@PathVariable Integer loansId) {
        this.loansService.delete(loansId);
        return new Result(true, StatusCode.SUCCESS, "Deleted");
    }
}
