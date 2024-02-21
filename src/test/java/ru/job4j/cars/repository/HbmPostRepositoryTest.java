package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.cars.repository.TestConfiguration.crudRepository;
import static ru.job4j.cars.repository.TestConfiguration.sf;

class HbmPostRepositoryTest {

    private final PostRepository postRepository = new HbmPostRepository(crudRepository);
    private static UserRepository userRepository = new HbmUserRepository(crudRepository);
    private static PhotoRepository photoRepository = new HbmPhotoRepository(crudRepository);
    private static CarRepository carRepository = new HbmCarRepository(crudRepository);
    private static EngineRepository engineRepository = new HbmEngineRepository(crudRepository);

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Photo").executeUpdate();
            session.createQuery("DELETE FROM Post").executeUpdate();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.createQuery("DELETE FROM Engine").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static User createUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        return userRepository.create(user);
    }

    @Test
    public void whenCreatePostThenReturnSamePost() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        Post result = postRepository.create(post);
        assertThat(result).isEqualTo(post);
    }

    @Test
    public void whenUpdatePostThenGetUpdatedPost() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        postRepository.create(post);
        post.setDescription("update");
        postRepository.update(post);
        Post result = postRepository.findById(post.getId()).get();
        assertThat(result.getDescription()).isEqualTo("update");
    }

    @Test
    public void whenDeletePostThenPostNotFound() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        postRepository.create(post);
        postRepository.delete(post.getId());
        Optional<Post> result = postRepository.findById(post.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenAdd1PostThenGetListOf1Post() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        postRepository.create(post);
        assertThat(postRepository.findAll()).size().isEqualTo(1);
    }

    @Test
    public void whenAddPostThenFindByIdReturnSamePost() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        postRepository.create(post);
        assertThat(postRepository.findById(post.getId())).get().isEqualTo(post);
    }

    @Test
    public void whenCreatePostTodayThenReturnSamePost() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        postRepository.create(post);
        Collection<Post> result = postRepository.getTodayPosts();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void whenCreatePostWithPhotoThenReturnSamePost() {
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        postRepository.create(post);
        Photo photo = new Photo();
        photo.setName("photo");
        photo.setPath("/path");
        photo.setPost(post);
        photoRepository.create(photo);
        Collection<Post> result = postRepository.getPostsWithPhoto();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void whenCreatePostThenReturnPostWithCarName() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.create(engine);
        Car car = new Car();
        car.setName("car");
        car.setEngine(engine);
        carRepository.create(car);
        Post post = new Post();
        post.setDescription("test");
        post.setCreated(LocalDateTime.now());
        post.setUser(createUser());
        post.setCar(car);
        postRepository.create(post);
        Collection<Post> result = postRepository.getPostsWithCarName("car");
        assertThat(result.size()).isEqualTo(1);
    }
}