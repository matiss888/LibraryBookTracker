package bootcamp.hibernate_practical.exception;

public class BookAlreadyInTheLibrary extends RuntimeException {
    public BookAlreadyInTheLibrary(String message) {
        super(message);
    }
}
