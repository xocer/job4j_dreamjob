package dream.store;

import dream.model.Candidate;
import dream.model.City;
import dream.model.Post;
import dream.model.User;

import java.util.Collection;
import java.util.List;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    void addPhoto(int id, String path);

    String getPathPhoto(int id);

    void delete(int id);

    boolean addUser(User user);

    boolean deleteUser(int id);

    User findUserByEmail(String email);

    List<City> findAllCity();
}
