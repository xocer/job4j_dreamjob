package dream.store;

import dream.model.Candidate;
import dream.model.Post;

import java.util.Collection;

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
}
