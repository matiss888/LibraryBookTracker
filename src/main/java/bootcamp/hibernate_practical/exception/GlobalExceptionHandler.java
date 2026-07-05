package bootcamp.hibernate_practical.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> bookNotFound(BookNotFoundException bookNotFoundException) {
        return new ResponseEntity<>(bookNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotInTheLibrary.class)
    public ResponseEntity<String> bookNotInTheLibrary(BookNotInTheLibrary bookNotInTheLibrary) {
        return new ResponseEntity<>(bookNotInTheLibrary.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookAlreadyInTheLibrary.class)
    public ResponseEntity<String> bookAlreadyInTheLibrary(BookAlreadyInTheLibrary bookAlreadyInTheLibrary) {
        return new ResponseEntity<>(bookAlreadyInTheLibrary.getMessage(), HttpStatus.CONFLICT);
    }
}
