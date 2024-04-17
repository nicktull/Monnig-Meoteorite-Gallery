package edu.tcu.cs.monning_meteorite_gallery.samplehistory;

import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter.SampleHistoryDtoToSampleHistoryConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.converter.SampleHistoryToSampleHistoryDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.samplehistory.dto.SampleHistoryDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/samplehistory")
public class SampleHistoryController {

    private final SampleHistoryService sampleHistoryService;

    private final SampleHistoryToSampleHistoryDtoConverter sampleHistoryToSampleHistoryDtoConverter;

    private final SampleHistoryDtoToSampleHistoryConverter sampleHistoryDtoToSampleHistoryConverter;

    public SampleHistoryController(SampleHistoryService sampleHistoryService, SampleHistoryToSampleHistoryDtoConverter sampleHistoryToSampleHistoryDtoConverter, SampleHistoryDtoToSampleHistoryConverter sampleHistoryDtoToSampleHistoryConverter){
        this.sampleHistoryService = sampleHistoryService;
        this.sampleHistoryToSampleHistoryDtoConverter = sampleHistoryToSampleHistoryDtoConverter;
        this.sampleHistoryDtoToSampleHistoryConverter = sampleHistoryDtoToSampleHistoryConverter;
    }

    @GetMapping("/{sampleHistoryId}")
    public Result findArtifactByID(@PathVariable String sampleHistoryId) {
        SampleHistory foundSampleHistory = this.sampleHistoryService.findById(sampleHistoryId);
        SampleHistoryDto sampleHistoryDto = this.sampleHistoryToSampleHistoryDtoConverter.convert(foundSampleHistory);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", sampleHistoryDto);
    }

    @PostMapping
    public Result addSampleHistory(@Valid @RequestBody SampleHistoryDto sampleHistoryDto){
        SampleHistory newSampleHistory = this.sampleHistoryDtoToSampleHistoryConverter.convert(sampleHistoryDto);
        SampleHistory savedSampleHistory = this.sampleHistoryService.save(newSampleHistory);
        SampleHistoryDto savedSampleHistoryDto = this.sampleHistoryToSampleHistoryDtoConverter.convert(savedSampleHistory);
        return new Result(true, StatusCode.SUCCESS, "Add sample history success.", savedSampleHistoryDto);
    }

    @DeleteMapping("/{sampleHistoryId}")
    public Result deleteSampleHistory(@PathVariable String sampleHistoryId){
        this.sampleHistoryService.delete(sampleHistoryId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }



}
