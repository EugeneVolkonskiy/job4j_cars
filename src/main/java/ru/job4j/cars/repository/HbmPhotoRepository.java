package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPhotoRepository implements PhotoRepository {

    private final CrudRepository crudRepository;

    @Override
    public Photo create(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    @Override
    public Optional<Photo> findById(int id) {
        return crudRepository.optional(
                "FROM Photo WHERE id = :fId", Photo.class,
                Map.of("fId", id)
        );
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE FROM Photo WHERE id = :fId",
                Map.of("fId", id)
        );
    }
}
