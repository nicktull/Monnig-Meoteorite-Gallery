package edu.tcu.cs.monning_meteorite_gallery.loans;

import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.loans.converter.LoansDtoToLoansConverter;
import edu.tcu.cs.monning_meteorite_gallery.loans.converter.LoansToLoansDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * Finds loans filtered by specified attributes with options for pagination and sorting.
     * The method filters loans in-memory after retrieving a page of loans based on sorting criteria.
     * This is suitable for smaller datasets. For bigger datasets, filter via database.
     *
     * @param loaneeId Optional ID of the loanee to filter by.
     * @param loaneeName Optional name of the loanee to filter by.
     * @param loaneeInstitution Optional institution associated with the loanee to filter by.
     * @param loaneeEmail Optional email of the loanee to filter by.
     * @param loaneeAddress Optional address of the loanee to filter by.
     * @param loanStartdate Optional start date of the loan to filter by.
     * @param loanDuedate Optional due date of the loan to filter by.
     * @param trackingNumber Optional tracking number of the loan to filter by.
     * @param loaneeNotes Optional notes associated with the loanee to filter by.
     * @param extraFiles Optional extra files associated with the loan to filter by.
     * @param status Optional status of the loan to filter by.
     * @param page Page number for pagination.
     * @param size Number of loans per page.
     * @param sortBy Attribute and direction for sorting (e.g., "loaneeName,ASC").
     * @return A result object that includes status, message, and a list of filtered loan DTOs.
     */
    @GetMapping("/search")
    public Result searchLoans(
            @RequestParam(required = false) Integer loanId,
            @RequestParam(required = false) String loaneeName,
            @RequestParam(required = false) String loaneeInstitution,
            @RequestParam(required = false) String loaneeEmail,
            @RequestParam(required = false) String loaneeAddress,
            @RequestParam(required = false) String loanStartdate,
            @RequestParam(required = false) String loanDuedate,
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) String loaneeNotes,
            @RequestParam(required = false) String extraFiles,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "loanId,ASC") String sortBy) {

        String[] sortParams = sortBy.split(",");
        Pageable pageable = PageRequest.of(
                page, size, Sort.by(
                        Sort.Direction.fromString(sortParams[1]), sortParams[0])
        );

        // Fetch all loans using pageable
        List<Loans> allLoans = loansService.findAll(pageable).getContent();

        // Apply combined predicates
        List<Loans> filteredLoans = allLoans.stream().filter(loan -> {
            if (status != null && !loan.getStatus().equals(status)) return false;
            if (loanStartdate != null && !loan.getLoanStartdate().equals(loanStartdate)) return false;
            if (loanDuedate != null && !loan.getLoanDuedate().equals(loanDuedate)) return false;
            if (loanId != null && !loan.getLoanId().equals(loanId)) return false;
            if (loaneeName != null && !loan.getLoaneeName().equals(loaneeName)) return false;
            if (loaneeInstitution != null && !loan.getLoaneeInstitution().equals(loaneeInstitution)) return false;
            if (loaneeEmail != null && !loan.getLoaneeEmail().equals(loaneeEmail)) return false;
            if (loaneeAddress != null && !loan.getLoaneeAddress().equals(loaneeAddress)) return false;
            if (trackingNumber != null && !loan.getTrackingNumber().equals(trackingNumber)) return false;
            if (loaneeNotes != null && !loan.getLoaneeNotes().equals(loaneeNotes)) return false;
            if (extraFiles != null && !loan.getExtraFiles().equals(extraFiles)) return false;
            return true; // Default case: include loan in result
        }).toList();

        // Convert filtered loans to DTOs
        List<LoansDto> loanDtos = filteredLoans.stream()
                .map(loansToLoansDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Filtered Loans", loanDtos);
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
