package dream.model;

import lombok.Data;

@Data
public class Candidate {
    private int id;
    private String name;
    private int photo_id;
    private int city_id;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Candidate(int id, String name, int city_id) {
        this.id = id;
        this.name = name;
        this.city_id = city_id;
    }

    public Candidate(int id, String name, int photo_id, int city_id) {
        this.id = id;
        this.name = name;
        this.photo_id = photo_id;
        this.city_id = city_id;
    }
}