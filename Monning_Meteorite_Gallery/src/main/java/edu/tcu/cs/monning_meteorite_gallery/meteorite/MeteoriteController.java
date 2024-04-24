package edu.tcu.cs.monning_meteorite_gallery.meteorite;

import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.loans.Loans;
import edu.tcu.cs.monning_meteorite_gallery.loans.dto.LoansDto;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.converter.MeteoriteDtoToMeteoriteConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.converter.MeteoriteToMeteoriteDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistory;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.SampleHistoryService;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter.SampleHistoryDtoToSampleHistoryConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter.SampleHistoryToSampleHistoryDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto.SampleHistoryDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/meteorites")
public class MeteoriteController {

    private final MeteoriteService meteoriteService;
    private final MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter;
    private final MeteoriteDtoToMeteoriteConverter meteoriteDtoToMeteoriteConverter;

    public MeteoriteController(MeteoriteService meteoriteService, MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter, MeteoriteDtoToMeteoriteConverter meteoriteDtoToMeteoriteConverter) {
        this.meteoriteService = meteoriteService;                                                                                                                     // Need to find the beans
        this.meteoriteToMeteoriteDtoConverter = meteoriteToMeteoriteDtoConverter;
        this.meteoriteDtoToMeteoriteConverter = meteoriteDtoToMeteoriteConverter;
    }

    /**
     * Finds meteorites filtered by specified attributes with options for pagination and sorting.
     * The method filters meteorites in-memory after retrieving a page of meteorites based on sorting criteria.
     *
     *
     * @param name Optional name of the meteorite to filter by.
     * @param country Optional country of meteorite
     * @param MClass Optional MClass of meteorite
     * @param MGroup Optional MGroup of meteorite
     * @param yearFound Optional yearFound of meteorite
     * @param weight Optional weight of meteorite
     * @param loanStatus Optional loan status of meteorite
     *
     * @return A result object that includes status, message, message, and a list of filtered meteorites DTOs.
     */
    @GetMapping("/search")
    public Result searchMeteorite(
            @RequestParam(required = false) String monnigNumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String MClass,
            @RequestParam(required = false) String MGroup,
            @RequestParam(required = false) String yearFound,
            @RequestParam(required = false) String weight,
            @RequestParam(required = false) String loanStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "monnigNumber,DESC") String sortBy) {

        String[] sortParams = sortBy.split(",");
        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Direction.fromString(sortParams[1]), sortParams[0]));

        // Fetch all meteorites using pageable
        List<Meteorite> allMeteorites = meteoriteService.findAll(pageable).getContent();

        // Apply combined predicates
        List<Meteorite> filteredMeteorites = allMeteorites.stream().filter(meteorite -> {
            if (monnigNumber != null && !meteorite.getMonnigNumber().equals(monnigNumber)) return false;
            if (name != null && !meteorite.getName().equals(name)) return false;
            if (country != null && !meteorite.getCountry().equals(country)) return false;
            if (MClass != null && !meteorite.getMClass().equals(MClass)) return false;
            if (MGroup != null && !meteorite.getMGroup().equals(MGroup)) return false;
            if (yearFound != null && !meteorite.getYearFound().equals(yearFound)) return false;
            if (weight != null && !meteorite.getWeight().equals(weight)) return false;
            if (loanStatus != null && !meteorite.getLoanStatus().equals(loanStatus)) return false;
            return true; // Default case: include meteorite in result
        }).toList();

        // Convert filtered Meteorites to DTOs
        List<MeteoriteDto> meteoriteDtos = filteredMeteorites.stream()
                .map(meteoriteToMeteoriteDtoConverter::convert)
                .collect(Collectors.toList());

        return new Result (true, StatusCode.SUCCESS, "Filtered Meteorites", meteoriteDtos);
    }


    @GetMapping("/{meteoriteId}")
    public Result findMeteoriteById(@PathVariable String meteoriteId){
        Meteorite foundMeteorite = this.meteoriteService.findByID(meteoriteId);
        MeteoriteDto meteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(foundMeteorite);
        return new Result(true, StatusCode.SUCCESS, "Found", meteoriteDto);
    }

    @GetMapping
    public Result findAllMeteorites(Pageable pageable){
        Page<Meteorite> meteoritePage = this.meteoriteService.findAll(pageable);

        // Convert meteoritePage to a list of meteoriteDtoPage
        Page<MeteoriteDto> meteoriteDtoPage = meteoritePage
                .map(this.meteoriteToMeteoriteDtoConverter::convert);
        return new Result(true, StatusCode.SUCCESS, "Found All Meteorites", meteoriteDtoPage);
    }

    @PostMapping
    public Result addMeteorite(@Valid @RequestBody MeteoriteDto meteoriteDto){
        // Convert MeteoriteDto to meteorite
        Meteorite newMeteorite = this.meteoriteDtoToMeteoriteConverter.convert(meteoriteDto);
        assert newMeteorite != null; //Make sure that newMeteorite cannot be null
        Meteorite savedMeteorite = this.meteoriteService.save(newMeteorite);
        MeteoriteDto savedMeteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(savedMeteorite);

        return new Result(true, StatusCode.SUCCESS, "Add Success", savedMeteoriteDto);
    }


    @PutMapping("/{meteoriteId}")
    public Result updateMeteorite(@PathVariable String meteoriteId, @Valid @RequestBody MeteoriteDto meteoriteDto){
        Meteorite update = this.meteoriteDtoToMeteoriteConverter.convert(meteoriteDto);
        Meteorite updatedMeteorite = this.meteoriteService.update(meteoriteId, update);
        MeteoriteDto updatedMeteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(updatedMeteorite);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedMeteoriteDto);
    }

    @DeleteMapping("/{meteoriteId}")
    public Result deleteMeteorite(@PathVariable String meteoriteId){
        this.meteoriteService.delete(meteoriteId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    @PostMapping("/addSubSample/{meteoriteId}")
    public Result addSubSample(@PathVariable String meteoriteId, @Valid @RequestBody MeteoriteDto meteoriteDto){
        Meteorite sample = this.meteoriteDtoToMeteoriteConverter.convert(meteoriteDto);
        Meteorite newSubSampleMeteorite = this.meteoriteService.subsample(meteoriteId, sample);
        MeteoriteDto newSubSampleMeteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(newSubSampleMeteorite);
        return new Result(true, StatusCode.SUCCESS, "Add Subsample Success", newSubSampleMeteoriteDto);
    }

}
