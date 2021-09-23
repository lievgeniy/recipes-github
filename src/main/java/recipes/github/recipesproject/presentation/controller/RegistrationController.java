package recipes.github.recipesproject.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.github.recipesproject.business.model.User;
import recipes.github.recipesproject.business.service.Interface.IUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public void createUser(@RequestBody @Valid User user) {
        this.userService.createUser(user);
    }
}
