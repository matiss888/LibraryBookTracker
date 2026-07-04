package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre(),
                request.getPublicationYear(),
                true
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> listOfBooks = bookRepository.findAll();
        List<BookResponse> responseForBooks = new ArrayList<>();
        for (Book book : listOfBooks) {
            BookResponse bookResponse = new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getPublicationYear(),
                    book.isAvailable());
            responseForBooks.add(bookResponse);
        }
        return responseForBooks;
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no book with this id"));
        return mapToResponse(book);
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("There is no book with this id"));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPublicationYear(request.getPublicationYear());
        book.setAvailable(request.isAvailable());
        bookRepository.save(book);
        return mapToResponse(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResponse> findByAuthor(String author) {
        List<Book> booksByAuthor = bookRepository.findByAuthor(author);
        return booksByAuthor.stream()
                .map(a -> new BookResponse(a.getId(),a.getTitle(),a.getAuthor(),a.getGenre(),a.getPublicationYear(),a.isAvailable()))
                .toList();
    }

    public List<BookResponse> findAvailableBooks(){
        List<Book> availableBooks = bookRepository.findByAvailableTrue();
        return availableBooks.stream()
                .map(a -> new BookResponse(a.getId(),a.getTitle(),a.getAuthor(),a.getGenre(),a.getPublicationYear(),a.isAvailable()))
                .toList();
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.isAvailable());
    }
}
