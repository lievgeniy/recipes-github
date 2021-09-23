package recipes.github.recipesproject.persistence.repository.Interface;

import recipes.github.recipesproject.business.model.Recipe;

import java.util.List;

public interface IRecipeRepository {
    Recipe getRecipe(long id);
    Long addRecipe(Recipe recipe);
    void deleteRecipe(long id);
    void updateRecipe(long id, Recipe newRecipe);
    List<Recipe> searchRecipeByName(String name);
    List<Recipe> searchRecipeByCategory(String category);
}
