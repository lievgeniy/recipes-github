package recipes.github.recipesproject.presentation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RecipeDTO {

    @NotBlank(message = "Recipe name should be filled")
    private String name;

    @NotBlank(message = "Recipe description should be filled")
    private String description;

    @NotEmpty(message = "There should be at least one ingredient")
    @Size(min = 1)
    private List<String> ingredients;

    @NotEmpty(message = "There should be at least one direction")
    @Size(min = 1)
    private List<String> directions;

    @NotBlank(message = "Recipe category should be filled")
    private String category;
}
