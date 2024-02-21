package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.cars.repository.TestConfiguration.crudRepository;
import static ru.job4j.cars.repository.TestConfiguration.sf;

class HbmPhotoRepositoryTest {

    private final PhotoRepository photoRepository = new HbmPhotoRepository(crudRepository);

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Photo").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenCreatePhotoThenReturnSamePhoto() {
        Photo photo = new Photo();
        photo.setName("name");
        photo.setPath("/path");
        Photo result = photoRepository.create(photo);
        assertThat(result).isEqualTo(photo);
    }

    @Test
    public void whenDeletePhotoThenPhotoNotFound() {
        Photo photo = new Photo();
        photo.setName("name");
        photo.setPath("/path");
        photoRepository.create(photo);
        photoRepository.delete(photo.getId());
        Optional<Photo> result = photoRepository.findById(photo.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenAddPhotoThenFindByIdReturnSamePhoto() {
        Photo photo = new Photo();
        photo.setName("name");
        photo.setPath("/path");
        photoRepository.create(photo);
        assertThat(photoRepository.findById(photo.getId())).get().isEqualTo(photo);
    }
}