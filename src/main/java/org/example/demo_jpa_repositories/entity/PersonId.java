package org.example.demo_jpa_repositories.entity;

import java.io.Serializable;

//этот класс  составной первичный ключ для класса Persons
// он реализует интерфейс Serializable значит его можно сериализовать и десериализовать
public class PersonId implements Serializable {

    private String name;
    private String surname;
    private int age;
}
