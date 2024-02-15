package ru.job4j.cars.repository;

import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

public interface OwnerRepository {

    Owner create(Owner owner);

    void update(Owner owner);

    void delete(int id);

    Collection<Owner> findAll();

    Optional<Owner> findById(int id);
}
