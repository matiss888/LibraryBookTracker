package bootcamp.hibernate_practical.exception;

public class BookNotInTheLibrary extends RuntimeException {
    public BookNotInTheLibrary(String message) {
        super(message);
    }
}
