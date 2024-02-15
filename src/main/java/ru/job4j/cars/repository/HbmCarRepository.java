package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCarRepository implements CarRepository {

    private final CrudRepository crudRepository;

    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    @Override
    public void delete(int id) {
        crudRepository.run(
                "DELETE FROM Car WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Car> findAll() {
        return crudRepository.query("FROM Car", Car.class);
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "FROM Car WHERE id = :fId", Car.class,
                Map.of("fId", id)
        );
    }
}
