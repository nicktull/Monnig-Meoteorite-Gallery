package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.System.exception.ObjectNotFoundException;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteRepository;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.MeteoriteService;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter.SampleHistoryDtoToSampleHistoryConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter.SampleHistoryToSampleHistoryDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto.SampleHistoryDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/samplehistory")
public class SampleHistoryController {

    private final SampleHistoryService sampleHistoryService;

    private final SampleHistoryToSampleHistoryDtoConverter sampleHistoryToSampleHistoryDtoConverter;

    private final SampleHistoryDtoToSampleHistoryConverter sampleHistoryDtoToSampleHistoryConverter;

    private final MeteoriteService meteoriteService;

    private final MeteoriteRepository meteoriteRepository;

    public SampleHistoryController(SampleHistoryService sampleHistoryService, SampleHistoryToSampleHistoryDtoConverter sampleHistoryToSampleHistoryDtoConverter, SampleHistoryDtoToSampleHistoryConverter sampleHistoryDtoToSampleHistoryConverter, MeteoriteService meteoriteService, MeteoriteRepository meteoriteRepository){

        this.sampleHistoryService = sampleHistoryService;
        this.sampleHistoryToSampleHistoryDtoConverter = sampleHistoryToSampleHistoryDtoConverter;
        this.sampleHistoryDtoToSampleHistoryConverter = sampleHistoryDtoToSampleHistoryConverter;
        this.meteoriteService = meteoriteService;
        this.meteoriteRepository = meteoriteRepository;

    }

    @GetMapping("/find/{meteoriteId}")
    public Result findSampleHistory(@PathVariable String meteoriteId){
        Meteorite owner = this.meteoriteService.findByID(meteoriteId);
        List<SampleHistory> sampleHistory = owner.getSampleHistory();
        List<SampleHistoryDto> newSampleHistoryDto = sampleHistory.stream()
                .map(this.sampleHistoryToSampleHistoryDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Returned Sample History Successful.", newSampleHistoryDto);
    }

    @PostMapping("/add/{meteoriteId}")
    public Result addSampleHistory(@PathVariable String meteoriteId, @Valid @RequestBody SampleHistoryDto sampleHistoryDto){
        Meteorite meteorite = this.meteoriteRepository.findById(meteoriteId).orElseThrow(() -> new ObjectNotFoundException("meteorite", meteoriteId));
        // Convert SampleHistoryDto to samplehistory
        SampleHistory newSampleHistory = this.sampleHistoryDtoToSampleHistoryConverter.convert(sampleHistoryDto);
        assert newSampleHistory != null;
        newSampleHistory.setMeteor(meteorite);
        SampleHistory savedSampleHistory = this.sampleHistoryService.save(newSampleHistory);
        SampleHistoryDto savedSampleHistoryDto = this.sampleHistoryToSampleHistoryDtoConverter.convert(savedSampleHistory);
        return new Result(true, StatusCode.SUCCESS, "Add Sample History Success", savedSampleHistoryDto);
    }

    @DeleteMapping("/delete/{sampleHistoryId}")
    public Result deleteSampleHistory(@PathVariable Integer sampleHistoryId){
        this.sampleHistoryService.delete(sampleHistoryId);
        return new Result(true, StatusCode.SUCCESS, "Entry Deleted");
    }





}
