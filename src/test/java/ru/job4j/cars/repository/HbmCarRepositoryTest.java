package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.cars.repository.TestConfiguration.crudRepository;
import static ru.job4j.cars.repository.TestConfiguration.sf;

class HbmCarRepositoryTest {

    private final CarRepository carRepository = new HbmCarRepository(crudRepository);
    private static EngineRepository engineRepository = new HbmEngineRepository(crudRepository);

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private static Engine createEngine() {
        Engine engine = new Engine();
        engine.setName("engine");
        return engineRepository.create(engine);
    }

    @Test
    public void whenCreateCarThenReturnSameCar() {
        Car car = new Car();
        car.setName("test");
        car.setEngine(createEngine());
        Car result = carRepository.create(car);
        assertThat(result).isEqualTo(car);
    }

    @Test
    public void whenUpdateCarThenGetUpdatedCar() {
        Car car = new Car();
        car.setName("test");
        car.setEngine(createEngine());
        carRepository.create(car);
        car.setName("update");
        carRepository.update(car);
        Car result = carRepository.findById(car.getId()).get();
        assertThat(result.getName()).isEqualTo("update");
    }

    @Test
    public void whenDeleteCarThenCarNotFound() {
        Car car = new Car();
        car.setName("test");
        car.setEngine(createEngine());
        carRepository.create(car);
        carRepository.delete(car.getId());
        Optional<Car> result = carRepository.findById(car.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenAdd1CarThenGetListOf1Car() {
        Car car = new Car();
        car.setName("test");
        car.setEngine(createEngine());
        carRepository.create(car);
        assertThat(carRepository.findAll()).size().isEqualTo(1);
    }

    @Test
    public void whenAddCarThenFindByIdReturnSameCar() {
        Car car = new Car();
        car.setName("test");
        car.setEngine(createEngine());
        carRepository.create(car);
        assertThat(carRepository.findById(car.getId())).get().isEqualTo(car);
    }
}