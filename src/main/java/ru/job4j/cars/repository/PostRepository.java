package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.Collection;

public interface PostRepository {

    Collection<Post> getTodayPosts();

    Collection<Post> getPostsWithPhoto();

    Collection<Post> getPostsWithCarName(String name);
}
