package recipes.github.recipesproject.presentation.error;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String cause) {
        super(cause);
    }
}
