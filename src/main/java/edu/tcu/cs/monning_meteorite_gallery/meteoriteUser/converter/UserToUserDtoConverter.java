package edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.converter;

import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.MeteoriteUser;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<MeteoriteUser, UserDto> {

    @Override
    public UserDto convert(MeteoriteUser source) {
        // We are not setting password in DTO.
        final UserDto userDto = new UserDto(source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles());
        return userDto;
    }

}