package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    @NotBlank(message = "Fill title field to update book information")
    private String title;
    @NotBlank(message = "Fill author field to update book information")
    private String author;
    @NotBlank(message = "Fill genre field to update book information")
    private String genre;
    @Positive
    private int publicationYear;
    public boolean available;
}
