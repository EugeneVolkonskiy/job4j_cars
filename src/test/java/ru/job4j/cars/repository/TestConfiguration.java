package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class TestConfiguration {

    public static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    public static SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    public static CrudRepository crudRepository = new CrudRepository(sf);
}
