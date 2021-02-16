package dream.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = "id")
public class Post {
    private final int id;
    private String name;
    private String description;
    private LocalDate date;

    public Post(int id, String name, String description, LocalDate date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }
}

