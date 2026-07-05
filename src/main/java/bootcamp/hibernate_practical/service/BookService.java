package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.entity.BorrowedStatus;
import bootcamp.hibernate_practical.exception.BookAlreadyInTheLibrary;
import bootcamp.hibernate_practical.exception.BookNotFoundException;
import bootcamp.hibernate_practical.exception.BookNotInTheLibrary;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                true,
                BorrowedStatus.RETURNED
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> listOfBooks = bookRepository.findAll();
        List<BookResponse> responseForBooks = new ArrayList<>();
        for (Book book : listOfBooks) {
            BookResponse bookResponse = mapToResponse(book);
            responseForBooks.add(bookResponse);
        }
        return responseForBooks;
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("There is no book with this id"));
        return mapToResponse(book);
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new BookNotFoundException("There is no book with this id"));
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

    public List<BookResponse> findByPartialTitle(String partialTitle) {
        List<Book> booksByPartialTitle = bookRepository.findByTitleContaining(partialTitle);
        return booksByPartialTitle.stream()
                .map(a -> new BookResponse(a.getId(),a.getTitle(),a.getAuthor(),a.getGenre(),a.getPublicationYear(),a.isAvailable()))
                .toList();

    }

    public BookResponse borrowBook (Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("There is no book with this id"));
        if(book.isAvailable() && book.getBorrowedStatus().equals(BorrowedStatus.RETURNED)) {
            book.setAvailable(false);
            book.setBorrowedStatus(BorrowedStatus.BORROWED);
        } else {
            throw new BookNotInTheLibrary("Book is not in the library right now");
        }
        bookRepository.save(book);
        return mapToResponse(book);
    }

    public BookResponse returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("There is no book with this id"));
        if(!book.isAvailable() && book.getBorrowedStatus().equals(BorrowedStatus.BORROWED)) {
            book.setAvailable(true);
            book.setBorrowedStatus(BorrowedStatus.RETURNED);
        } else {
            throw new BookAlreadyInTheLibrary("Book is already in the library");
        }
        bookRepository.save(book);
        return mapToResponse(book);
    }

    public List<BookResponse> findByPublicationYear(int publicationYear) {
        List<Book> booksByPublicationYear = bookRepository.findByPublicationYear(publicationYear);
        return booksByPublicationYear.stream()
                .map(a -> new BookResponse(a.getId(),a.getTitle(),a.getAuthor(),a.getGenre(),a.getPublicationYear(),a.isAvailable()))
                .toList();
    }

    public int countAvailableBooksInLibrary() {
        return bookRepository.findByAvailableTrue().size();
    }
}
