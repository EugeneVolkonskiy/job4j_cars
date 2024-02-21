package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.cars.repository.TestConfiguration.crudRepository;
import static ru.job4j.cars.repository.TestConfiguration.sf;

class HbmOwnerRepositoryTest {

    private final OwnerRepository ownerRepository = new HbmOwnerRepository(crudRepository);
    private static UserRepository userRepository = new HbmUserRepository(crudRepository);

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Owner").executeUpdate();
            session.createQuery("DELETE FROM User").executeUpdate();
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
    public void whenCreateOwnerThenReturnSameOwner() {
        Owner owner = new Owner();
        owner.setName("test");
        owner.setUser(createUser());
        Owner result = ownerRepository.create(owner);
        assertThat(result).isEqualTo(owner);
    }

    @Test
    public void whenUpdateOwnerThenGetUpdatedOwner() {
        Owner owner = new Owner();
        owner.setName("test");
        owner.setUser(createUser());
        ownerRepository.create(owner);
        owner.setName("update");
        ownerRepository.update(owner);
        Owner result = ownerRepository.findById(owner.getId()).get();
        assertThat(result.getName()).isEqualTo("update");
    }

    @Test
    public void whenDeleteOwnerThenOwnerNotFound() {
        Owner owner = new Owner();
        owner.setName("test");
        owner.setUser(createUser());
        ownerRepository.create(owner);
        ownerRepository.delete(owner.getId());
        Optional<Owner> result = ownerRepository.findById(owner.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenAdd1OwnerThenGetListOf1Owner() {
        Owner owner = new Owner();
        owner.setName("test");
        owner.setUser(createUser());
        ownerRepository.create(owner);
        assertThat(ownerRepository.findAll()).size().isEqualTo(1);
    }

    @Test
    public void whenAddOwnerThenFindByIdReturnSameOwner() {
        Owner owner = new Owner();
        owner.setName("test");
        owner.setUser(createUser());
        ownerRepository.create(owner);
        assertThat(ownerRepository.findById(owner.getId())).get().isEqualTo(owner);
    }
}