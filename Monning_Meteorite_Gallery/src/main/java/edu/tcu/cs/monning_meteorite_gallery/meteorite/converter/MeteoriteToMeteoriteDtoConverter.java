package edu.tcu.cs.monning_meteorite_gallery.meteorite.converter;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeteoriteToMeteoriteDtoConverter implements Converter<Meteorite, MeteoriteDto> {

    public MeteoriteToMeteoriteDtoConverter() {}

    @Override
    public MeteoriteDto convert(Meteorite source) {
        MeteoriteDto meteoriteDto = new MeteoriteDto(source.getMonnigNumber(),
                                                        source.getName(),
                                                        source.getCountry(),
                                                        source.getMClass(),
                                                        source.getMGroup(),
                                                        source.getYearFound(),
                                                        source.getWeight());
        return meteoriteDto;
    }

}