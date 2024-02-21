package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {

    private CrudRepository crudRepository;

    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.persist(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE FROM Post WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Post> findAll() {
        return crudRepository.query("FROM Post", Post.class);
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "FROM Post WHERE id = :fId", Post.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Post> getTodayPosts() {
        return crudRepository.query(
                "FROM Post WHERE created > :fLastDay", Post.class,
                Map.of("fLastDay", LocalDateTime.now().minusDays(1))
        );
    }

    @Override
    public Collection<Post> getPostsWithPhoto() {
        return crudRepository.query(
                "SELECT DISTINCT p FROM Post p JOIN FETCH p.photos WHERE SIZE(p.photos) > 0", Post.class
        );
    }

    @Override
    public Collection<Post> getPostsWithCarName(String name) {
        return crudRepository.query(
                "FROM Post p JOIN FETCH p.car WHERE name LIKE :fName ", Post.class,
                Map.of("fName", "%" + name + "%")
        );
    }
}
