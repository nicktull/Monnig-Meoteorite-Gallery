package edu.tcu.cs.monning_meteorite_gallery.meteorite;


import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.converter.MeteoriteDtoToMeteoriteConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.converter.MeteoriteToMeteoriteDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meteorites/")
public class MeteoriteController {

    private final MeteoriteService meteoriteService;
    private final MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter;
    private final MeteoriteDtoToMeteoriteConverter meteoriteDtoToMeteoriteConverter;

    public MeteoriteController(MeteoriteService meteoriteService, MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter, MeteoriteDtoToMeteoriteConverter meteoriteDtoToMeteoriteConverter) {
        this.meteoriteService = meteoriteService;                                                                                                                     // Need to find the beans
        this.meteoriteToMeteoriteDtoConverter = meteoriteToMeteoriteDtoConverter;
        this.meteoriteDtoToMeteoriteConverter = meteoriteDtoToMeteoriteConverter;
    }

    @GetMapping("/{meteoriteID}")
    public Result findMeteoriteById(@PathVariable String meteoriteID){
        Meteorite foundMeteorite = this.meteoriteService.findByID(meteoriteID);
        MeteoriteDto meteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(foundMeteorite);
        return new Result(true, StatusCode.SUCCESS, "Found", meteoriteDto);
    }

    @PostMapping
    public Result addMeteorite(@Validated @RequestBody MeteoriteDto meteoriteDto){
        // Convert MeteoriteDto to meteorite
        Meteorite newMeteorite = this.meteoriteDtoToMeteoriteConverter.convert(meteoriteDto);
        Meteorite savedMeteorite = this.meteoriteService.save(newMeteorite);
        MeteoriteDto savedMeteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(savedMeteorite);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedMeteoriteDto);
    }

    @PutMapping("/{meteoriteID}")
    public Result updateArtifact(@PathVariable String meteoriteId, @RequestBody @Valid MeteoriteDto meteoriteDto){
        return null;
    }

    @DeleteMapping("/{meteoriteId}")
    public Result deleteArtifact(@PathVariable String meteoriteId){
        this.meteoriteService.delete(meteoriteId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}
