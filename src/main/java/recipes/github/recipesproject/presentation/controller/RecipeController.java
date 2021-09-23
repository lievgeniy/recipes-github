package recipes.github.recipesproject.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.github.recipesproject.business.constants.Constants;
import recipes.github.recipesproject.business.model.Recipe;
import recipes.github.recipesproject.business.service.Interface.IRecipeService;
import recipes.github.recipesproject.presentation.error.BadParametersException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {

    private final IRecipeService recipeService;

    @Autowired
    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable @Min(1) long id) {
        Recipe recipe = this.recipeService.getRecipe(id);
        return recipe;
    }

    @PostMapping("/new")
    public Map<String, Long> createRecipe(@RequestBody @Valid Recipe recipe) {
        Map<String, Long> response = new HashMap<>();
        Long recipeId = this.recipeService.createRecipe(recipe);
        response.put("id", recipeId);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipeById(@PathVariable @Min(value = 1, message="The value should be positive") long id) {
        this.recipeService.deleteRecipe(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable long id, @RequestBody @Valid Recipe newRecipe) {
        this.recipeService.updateRecipe(id, newRecipe);
    }

    @GetMapping("/search")
    public List<Recipe> getRecipesBySearch(@RequestParam Map<String,String> allParams) {
        if (allParams.size() != 1 ) {
            throw new BadParametersException("Incorrect number of request parameters!");
        }

        Map.Entry<String, String> param = allParams.entrySet()
                .stream()
                .findFirst()
                .get();

        List<Recipe> recipes = new ArrayList<>();

        if (!Constants.SEARCH_PARAM_NAME.equals(param.getKey()) && !Constants.SEARCH_PARAM_CATEGORY.equals(param.getKey())) {
            throw new BadParametersException("Incorrect name of the request parameter!");
        }

        recipes = this.recipeService.searchByNameOrCategory(param.getKey(), param.getValue());

        return recipes;
    }
}
