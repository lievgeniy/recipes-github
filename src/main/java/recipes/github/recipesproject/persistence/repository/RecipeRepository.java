package recipes.github.recipesproject.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import recipes.github.recipesproject.business.model.Recipe;
import recipes.github.recipesproject.persistence.repository.Interface.IRecipeRepository;
import recipes.github.recipesproject.persistence.repository.Interface.IRecipeRepositoryCRUD;
import recipes.github.recipesproject.presentation.error.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("recipeRepository")
public class RecipeRepository implements IRecipeRepository {
    private final IRecipeRepositoryCRUD recipeRepository;

    @Autowired
    public RecipeRepository(@Qualifier("recipeRepositoryCRUD") IRecipeRepositoryCRUD recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe getRecipe(long id) {
        return this.recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Long addRecipe(Recipe recipe) {
        return this.recipeRepository.save(recipe).getId();
    }

    @Override
    public void deleteRecipe(long id) {
        this.recipeRepository.deleteById(id);
    }

    @Override
    public void updateRecipe(long id, Recipe newRecipe) {
        Optional<Recipe> oldRecipe = this.recipeRepository.findById(id);
        if (!oldRecipe.isPresent()) {
            throw new ItemNotFoundException("Recipe not found with id : " + id);
        }
        oldRecipe.get().setName(newRecipe.getName());
        oldRecipe.get().setDescription(newRecipe.getDescription());
        oldRecipe.get().setIngredients(newRecipe.getIngredients());
        oldRecipe.get().setDirections(newRecipe.getDirections());
        oldRecipe.get().setCategory(newRecipe.getCategory());
        oldRecipe.get().setDate(LocalDateTime.now());
        this.recipeRepository.save(oldRecipe.get());
    }

    public List<Recipe> searchRecipeByName(String name) {
        return this.recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public List<Recipe> searchRecipeByCategory(String category) {
        return this.recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
}
