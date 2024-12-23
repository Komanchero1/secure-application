package org.example.demo_jpa_repositories.controller;

import org.example.demo_jpa_repositories.entity.Persons;
import org.example.demo_jpa_repositories.repository.PersonsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//класс обрабатывает HTTP-запросы начинающиеся с "/api/persons" и возвращает ответы
@RestController
@RequestMapping("/api/persons")
public class PersonsController {

    private final PersonsRepository personsRepository;

    public PersonsController(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }


    // метод возвращает всех Persons по городу в виде строки
    @GetMapping("/city/{city}") // http://localhost:8080/api/persons/city/Moscow
    public String getPersonsByCity(@PathVariable String city) {
        List<Persons> persons = personsRepository.findByCityOfResidence(city);
        // Используем StringBuilder для построения ответа
        StringBuilder response = new StringBuilder();
        for (Persons person : persons) {
            response.append(person.toString()).append("\n");
        }
        return response.toString();
    }


    // метод возвращает Persons по возрасту, меньше указанного в виде строки
    @GetMapping("/age/less-than/{age}")//http://localhost:8080/api/persons/age/less-than/20

    public String getPersonsYoungerThan(@PathVariable int age) {
        List<Persons> persons = personsRepository.findByAgeLessThanOrderByAgeAsc(age);

        StringBuilder response = new StringBuilder();

        for (Persons person : persons) {
            response.append(person.toString()).append("\n").append("\n");
        }
        return response.toString();
    }


    // метод возвращает Persons по совпадению имени и фамилии,  в виде строки
    @GetMapping("/name/{name}/surname/{surname}") // http://localhost:8080/api/persons/name/Olga/surname/Karpova
    public String getPersonByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        Optional<Persons> person = personsRepository.findByNameAndSurname(name, surname);
        return person.map(Persons::toString).orElse("Пользователь не найден"); // Возвращаем строку с информацией о человеке или сообщение об отсутствии
    }

}

