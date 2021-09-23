package recipes.github.recipesproject.presentation.error;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String cause) {
        super(cause);
    }
}
