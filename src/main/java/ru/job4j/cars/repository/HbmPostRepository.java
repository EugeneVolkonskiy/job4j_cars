package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {

    private CrudRepository crudRepository;

    @Override
    public Collection<Post> getTodayPosts() {
        return crudRepository.query(
                "FROM Post WHERE created < now() AND created > current_date", Post.class
        );
    }

    @Override
    public Collection<Post> getPostsWithPhoto() {
        return crudRepository.query(
                "SELECT DISTINCT p FROM Post p JOIN FETCH p.photos WHERE post_id is not null", Post.class
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
