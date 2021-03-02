package dream.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
}
