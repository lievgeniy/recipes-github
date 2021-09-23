package recipes.github.recipesproject.presentation.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.github.recipesproject.business.model.User;
import recipes.github.recipesproject.business.service.Interface.IUserService;
import recipes.github.recipesproject.presentation.dto.UserDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody @Valid UserDTO userDto) {
        User user = convertToEntity(userDto);
        this.userService.createUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    private UserDTO convertToDto(User user) {
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        return userDto;
    }

    private User convertToEntity(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
