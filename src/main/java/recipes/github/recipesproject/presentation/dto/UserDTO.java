package recipes.github.recipesproject.presentation.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @NotBlank(message = "Email should be filled")
    @Email(message="Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message="Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password should be filled")
    @Size(min = 8, message="Password should be at least 8 characters long")
    private String password;
}
