package edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.converter;

import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.MeteoriteUser;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, MeteoriteUser> {

    @Override
    public MeteoriteUser convert(UserDto source) {
        MeteoriteUser meteoriteUser = new MeteoriteUser();
        meteoriteUser.setUsername(source.username());
        meteoriteUser.setEnabled(source.enabled());
        meteoriteUser.setRoles(source.roles());
        return meteoriteUser;
    }

}