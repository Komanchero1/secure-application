package org.example.demo_jpa_repositories.repository;

import org.example.demo_jpa_repositories.entity.Persons;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


//интерфейс предоставляет методы для выполнения операций с данными с использованием JPA
public interface PersonsRepository extends JpaRepository<Persons, Integer> {

    //  метод для проверки существования объекта
    List<Persons> findByNameAndSurnameAndAgeAndPhoneNumberAndCityOfResidence(
            String name, String surname, int age, String phoneNumber, String cityOfResidence);


    // Метод для получения всех Persons по городу
    List<Persons> findByCityOfResidence(String city);

    // Метод для получения Persons по возрасту, меньше указанного, и сортировка по возрастанию
    List<Persons> findByAgeLessThanOrderByAgeAsc(int age);

    // Метод для получения Persons по имени и фамилии, возвращающий Optional
    Optional<Persons> findByNameAndSurname(String name, String surname);

}

