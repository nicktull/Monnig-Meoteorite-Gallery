package edu.tcu.cs.monning_meteorite_gallery.meteorite;


import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.converter.MeteoriteToMeteoriteDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meteorites/")
public class MeteoriteController {

    private final MeteoriteService meteoriteService;
    private final MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter;

    public MeteoriteController(MeteoriteService meteoriteService, MeteoriteToMeteoriteDtoConverter meteoriteToMeteoriteDtoConverter) {
        this.meteoriteService = meteoriteService;
        this.meteoriteToMeteoriteDtoConverter = meteoriteToMeteoriteDtoConverter;
    }

    @GetMapping("/{meteoriteID}")
    public Result findMeteoriteById(@PathVariable String meteoriteID){
        Meteorite foundMeteorite = this.meteoriteService.findByID(meteoriteID);
        MeteoriteDto meteoriteDto = this.meteoriteToMeteoriteDtoConverter.convert(foundMeteorite);
        return new Result(true, StatusCode.SUCCESS, "Found", meteoriteDto);
    }

}
