package dream.store;

import dream.model.Candidate;
import dream.model.Post;
import dream.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();
    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"), it.getInt("photo_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE candidate SET name=? WHERE id=?")) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getId());
            ps.execute();
        } catch (SQLException throwables) {
            LOG.error("error", throwables);
        }
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post SET name=? WHERE id=?")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (SQLException throwables) {
            LOG.error("error", throwables);
        }
    }

    @Override
    public Post findPostById(int id) {
        Post post = new Post(0, "");
        try (Connection cn = pool.getConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id=?")){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                post.setId(resultSet.getInt(1));
                post.setName(resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            LOG.error("error", throwables);
        }
        return post;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = new Candidate(0, "");
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                candidate.setId(resultSet.getInt(1));
                candidate.setName(resultSet.getString(2));
                candidate.setPhoto_id(resultSet.getInt(3));
            }
        } catch (SQLException throwables) {
            LOG.error("error", throwables);
        }
        return candidate;
    }

    @Override
    public void addPhoto(int id, String path) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO photo(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, path);
            ps.execute();
            int photo_id = 0;
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    photo_id = resultSet.getInt(1);
                }
            }
            try (PreparedStatement preparedStatement = cn.prepareStatement(
                    "update candidate set photo_id=? where candidate.id=?"
            )) {
                preparedStatement.setInt(1, photo_id);
                preparedStatement.setInt(2, id);
                preparedStatement.execute();
            }
        } catch (SQLException throwables) {
            LOG.error("error in addPhoto");
        }
    }

    @Override
    public String getPathPhoto(int id) {
        String answer = "";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select name from photo where photo.id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                answer = rs.getString(1);
            }
        } catch (SQLException throwables) {
            LOG.error("error in getPathPhoto");
        }
        return answer;
    }

    @Override
    public void delete(int id) {
        int photoId = PsqlStore.instOf().findCandidateById(id).getPhoto_id();
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement(
                     "DELETE FROM candidate where id=?"
             )) {
            pr.setInt(1, id);
            pr.execute();


            if (photoId > 0) {
                try (PreparedStatement preparedStatement = cn.prepareStatement(
                        "DELETE FROM photo WHERE id=?"
                )) {
                    preparedStatement.setInt(1, photoId);
                    preparedStatement.execute();
                }
            }
        } catch (SQLException throwables) {
            LOG.error("error in Delete method");
        }
    }

    @Override
    public boolean addUser(User user) {
        try (var connection = pool.getConnection();
             final var preparedStatement = connection.prepareStatement(
                     "insert into users(name, email, password) values (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.execute();

            try (final var rs = preparedStatement.getGeneratedKeys()){
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
            return true;

        } catch (SQLException throwables) {
            LOG.error("error in AddUser method");
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        try (final var connection = pool.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from users where users.id=?")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            LOG.error("error in AddUser method");
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        final var user = new User(0, "", "", "");
        try (final var connection = pool.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from users where users.email=?")) {
            preparedStatement.setString(1, email);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
            }
        } catch (SQLException throwables) {
            LOG.error("error in AddUser method");
        }
        return user;
    }
}