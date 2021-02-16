package dream.model;

import lombok.Data;

@Data
public class Candidate {
    private int id;
    private String name;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }
}