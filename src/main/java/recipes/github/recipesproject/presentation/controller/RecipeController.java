package recipes.github.recipesproject.presentation.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.github.recipesproject.business.constants.Constants;
import recipes.github.recipesproject.business.model.Recipe;
import recipes.github.recipesproject.business.service.Interface.IRecipeService;
import recipes.github.recipesproject.presentation.dto.RecipeDTO;
import recipes.github.recipesproject.presentation.error.BadParametersException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {

    private final IRecipeService recipeService;

    @Autowired
    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable @Min(1) long id) {
        Recipe recipe = this.recipeService.getRecipe(id);
        return new ResponseEntity<>(convertToDto(recipe), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> createRecipe(@RequestBody @Valid RecipeDTO recipeDto) {
        Map<String, Long> response = new HashMap<>();
        Long recipeId = this.recipeService.createRecipe(convertToEntity(recipeDto));
        response.put("id", recipeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteRecipeById(@PathVariable @Min(value = 1, message="The value should be positive") long id) {
        this.recipeService.deleteRecipe(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity updateRecipe(@PathVariable long id, @RequestBody @Valid RecipeDTO newRecipeDto) {
        this.recipeService.updateRecipe(id, convertToEntity(newRecipeDto));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> getRecipesBySearch(@RequestParam Map<String,String> allParams) {
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

        return new ResponseEntity<>(recipes.stream()
                .map(this::convertToDto).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    private RecipeDTO convertToDto(Recipe recipe) {
        RecipeDTO recipeDto = modelMapper.map(recipe, RecipeDTO.class);
        return recipeDto;
    }

    private Recipe convertToEntity(RecipeDTO recipeDto) {
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        return recipe;
    }
}
