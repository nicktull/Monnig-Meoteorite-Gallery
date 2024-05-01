package edu.tcu.cs.monning_meteorite_gallery.meteoriteUser;


import edu.tcu.cs.monning_meteorite_gallery.System.Result;
import edu.tcu.cs.monning_meteorite_gallery.System.StatusCode;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.converter.UserDtoToUserConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.converter.UserToUserDtoConverter;
import edu.tcu.cs.monning_meteorite_gallery.meteoriteUser.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService userService;

    private final UserDtoToUserConverter userDtoToUserConverter; // Convert userDto to user.

    private final UserToUserDtoConverter userToUserDtoConverter; // Convert user to userDto.


    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @GetMapping
    public Result findAllUsers() {
        List<MeteoriteUser> foundMeteoriteUsers = this.userService.findAll();

        // Convert foundUsers to a list of UserDtos.
        List<UserDto> userDtos = foundMeteoriteUsers.stream()
                .map(this.userToUserDtoConverter::convert)
                .collect(Collectors.toList());

        // Note that UserDto does not contain password field.
        return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Integer userId) {
        MeteoriteUser foundMeteoriteUser = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(foundMeteoriteUser);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
    }

    /**
     * We are not using UserDto, but User, since we require password.
     *
     * @param newMeteoriteUsers
     * @return
     */
    @PostMapping
    public Result addUser(@Valid @RequestBody MeteoriteUser newMeteoriteUsers) {
        MeteoriteUser savedUser = this.userService.save(newMeteoriteUsers);
        UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
    }

    // We are not using this to update password, need another changePassword method in this class.
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
        MeteoriteUser update = this.userDtoToUserConverter.convert(userDto);
        MeteoriteUser updatedMeteoriteUser = this.userService.update(userId, update);
        UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedMeteoriteUser);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId) {
        this.userService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}