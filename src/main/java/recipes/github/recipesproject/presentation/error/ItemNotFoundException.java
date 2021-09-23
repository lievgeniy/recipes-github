package recipes.github.recipesproject.presentation.error;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String cause) {
        super(cause);
    }
}
