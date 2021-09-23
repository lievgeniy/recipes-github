package recipes.github.recipesproject.presentation.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class RecipeExceptionHandler {
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Error> handleException(RecipeNotFoundException e) {
        Error error = new Error(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadParametersException.class)
    public ResponseEntity<Error> handleBadParametersException(BadParametersException e) {
        Error error = new Error(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Error> handleBadParametersException(ItemNotFoundException e) {
        Error error = new Error(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Error> handleUnauthorizedException(UnauthorizedException e) {
        Error error = new Error(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleBadRequestException(MethodArgumentNotValidException e) {

        // Handle All Field Validation Errors
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage());
            sb.append(";");
        }

        Error error = new Error(sb.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
