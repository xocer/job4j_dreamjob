package dream.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "id")
public class Post {
    private int id;
    private String name;
    private String description;
    private LocalDate date;

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

