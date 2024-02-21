package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {

    Post create(Post post);

    void update(Post post);

    void delete(int id);

    Collection<Post> findAll();

    Optional<Post> findById(int id);

    Collection<Post> getTodayPosts();

    Collection<Post> getPostsWithPhoto();

    Collection<Post> getPostsWithCarName(String name);
}
