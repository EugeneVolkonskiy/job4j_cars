package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.cars.repository.TestConfiguration.crudRepository;
import static ru.job4j.cars.repository.TestConfiguration.sf;

class HbmUserRepositoryTest {

    private final UserRepository userRepository = new HbmUserRepository(crudRepository);

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenCreateUserThenReturnSameUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        User result = userRepository.create(user);
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void whenUpdateUserThenGetUpdatedUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        user.setLogin("update");
        userRepository.update(user);
        User result = userRepository.findById(user.getId()).get();
        assertThat(result.getLogin()).isEqualTo("update");
    }

    @Test
    public void whenDeleteUserThenUserNotFound() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        userRepository.delete(user.getId());
        Optional<User> result = userRepository.findById(user.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenAdd1UserThenGetListOf1User() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        assertThat(userRepository.findAllOrderById()).size().isEqualTo(1);
    }

    @Test
    public void whenAddUserThenFindByIdReturnSameUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        assertThat(userRepository.findById(user.getId())).get().isEqualTo(user);
    }

    @Test
    public void whenAdd2UsersThenFindByLikeLoginReturnListOf2Users() {
        User user1 = new User();
        user1.setLogin("test123");
        user1.setPassword("test123");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("test256");
        user2.setPassword("test256");
        userRepository.create(user2);
        List<User> result = userRepository.findByLikeLogin("test");
        assertThat(result).size().isEqualTo(2);
    }

    @Test
    public void whenAdd2UsersThenFindByLoginReturnUser() {
        User user1 = new User();
        user1.setLogin("test1");
        user1.setPassword("test123");
        userRepository.create(user1);
        User user2 = new User();
        user2.setLogin("test2");
        user2.setPassword("test256");
        userRepository.create(user2);
        User result = userRepository.findByLogin("test1").get();
        assertThat(result).isEqualTo(user1);
    }
}