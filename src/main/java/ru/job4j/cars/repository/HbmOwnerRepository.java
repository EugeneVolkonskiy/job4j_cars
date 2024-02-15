package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmOwnerRepository implements OwnerRepository {

    private final CrudRepository crudRepository;

    @Override
    public Owner create(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    @Override
    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE FROM Owner WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Owner> findAll() {
        return crudRepository.query("FROM Owner", Owner.class);
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "FROM Owner WHERE id = :fId", Owner.class,
                Map.of("fId", id)
        );
    }
}
