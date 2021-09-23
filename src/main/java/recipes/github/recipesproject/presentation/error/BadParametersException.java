package recipes.github.recipesproject.presentation.error;

public class BadParametersException  extends RuntimeException {
    public BadParametersException(String cause) {
        super(cause);
    }
}