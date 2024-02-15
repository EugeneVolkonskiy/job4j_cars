package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository {

    Car create(Car car);

    void update(Car car);

    void delete(int id);

    Collection<Car> findAll();

    Optional<Car> findById(int id);
}
