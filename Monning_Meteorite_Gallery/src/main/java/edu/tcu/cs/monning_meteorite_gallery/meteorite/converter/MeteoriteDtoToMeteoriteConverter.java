package edu.tcu.cs.monning_meteorite_gallery.meteorite.converter;

import edu.tcu.cs.monning_meteorite_gallery.meteorite.Meteorite;
import edu.tcu.cs.monning_meteorite_gallery.meteorite.dto.MeteoriteDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeteoriteDtoToMeteoriteConverter implements Converter<MeteoriteDto, Meteorite> {

    @Override
    public Meteorite convert(MeteoriteDto source) {
        Meteorite meteorite = new Meteorite();
            meteorite.setMonnigNumber(source.MonnigNumber());
            meteorite.setName(source.Name());
            meteorite.setCountry(source.Country());
            meteorite.setMClass(source.MClass());
            meteorite.setMGroup(source.Group());
            meteorite.setYearFound(source.year());
            meteorite.setWeight(source.weight());
            return meteorite;
    }
}
