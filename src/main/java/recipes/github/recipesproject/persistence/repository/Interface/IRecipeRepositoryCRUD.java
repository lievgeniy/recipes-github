package recipes.github.recipesproject.persistence.repository.Interface;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.github.recipesproject.business.model.Recipe;

import java.util.List;

@Repository
@Qualifier("recipeRepositoryCRUD")
public interface IRecipeRepositoryCRUD extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}
