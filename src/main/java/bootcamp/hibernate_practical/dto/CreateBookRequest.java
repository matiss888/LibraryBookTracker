package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CreateBookRequest {
    @NotBlank(message = "Fill in title field to create book")
    private String title;
    @NotBlank(message = "Fill in author field to create book ")
    private String author;
    @NotBlank(message = "Fill in genre field to create book ")
    private String genre;
    @Positive
    private int publicationYear;
}
