package recipes.github.recipesproject.business.service.Interface;

import recipes.github.recipesproject.business.model.Recipe;

import java.util.List;

public interface IRecipeService {
    Recipe getRecipe(long id);
    Long createRecipe(Recipe recipe);
    void deleteRecipe(long id);
    void updateRecipe(long id, Recipe newRecipe);
    List<Recipe> searchByNameOrCategory(String param, String searchValue);
}
