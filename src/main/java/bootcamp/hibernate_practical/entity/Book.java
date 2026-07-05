package bootcamp.hibernate_practical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor; // Description below why I commented this out.

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor, could not launch the application with this annotation
// to check if my endpoints are working in Insomnia so commented it out.
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private boolean available;
    @Enumerated(EnumType.STRING)
    private BorrowedStatus borrowedStatus;

    public Book(String title, String author, String genre, int publicationYear, boolean available, BorrowedStatus borrowedStatus) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.available = available;
        this.borrowedStatus = borrowedStatus;
    }
}
