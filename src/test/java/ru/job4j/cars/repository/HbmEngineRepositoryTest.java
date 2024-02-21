package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.job4j.cars.repository.TestConfiguration.crudRepository;
import static ru.job4j.cars.repository.TestConfiguration.sf;

class HbmEngineRepositoryTest {

    private final EngineRepository engineRepository = new HbmEngineRepository(crudRepository);

    @AfterEach
    public void clearTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Engine").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenCreateEngineThenReturnSameEngine() {
        Engine engine = new Engine();
        engine.setName("test");
        Engine result = engineRepository.create(engine);
        assertThat(result).isEqualTo(engine);
    }

    @Test
    public void whenUpdateEngineThenGetUpdatedEngine() {
        Engine engine = new Engine();
        engine.setName("test");
        engineRepository.create(engine);
        engine.setName("update");
        engineRepository.update(engine);
        Engine result = engineRepository.findById(engine.getId()).get();
        assertThat(result.getName()).isEqualTo("update");
    }

    @Test
    public void whenDeleteEngineThenEngineNotFound() {
        Engine engine = new Engine();
        engine.setName("test");
        engineRepository.create(engine);
        engineRepository.delete(engine.getId());
        Optional<Engine> result = engineRepository.findById(engine.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void whenAdd1EngineThenGetListOf1Engine() {
        Engine engine = new Engine();
        engine.setName("test");
        engineRepository.create(engine);
        assertThat(engineRepository.findAll()).size().isEqualTo(1);
    }

    @Test
    public void whenAddEngineThenFindByIdReturnSameEngine() {
        Engine engine = new Engine();
        engine.setName("test");
        engineRepository.create(engine);
        assertThat(engineRepository.findById(engine.getId())).get().isEqualTo(engine);
    }
}