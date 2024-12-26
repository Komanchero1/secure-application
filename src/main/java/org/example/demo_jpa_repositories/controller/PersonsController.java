package org.example.demo_jpa_repositories.controller;

import jakarta.annotation.security.RolesAllowed;
import org.example.demo_jpa_repositories.entity.Persons;
import org.example.demo_jpa_repositories.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//класс обрабатывает HTTP-запросы начинающиеся с "/api/persons" и возвращает ответы
@RestController
@RequestMapping("/api/persons")
public class PersonsController {

    //этот экземпляр будет обеспечивать доступ к данным и разделяя
    // ответственность между компонентами
    private final PersonsRepository personsRepository;

    @Autowired
    public PersonsController(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }


    // Метод возвращает всех Persons по городу в виде строки
    @GetMapping("/city/{city}") // http://localhost:8080/api/persons/city/Moscow
    @Secured("ROLE_READ")
    public String getPersonsByCity(@PathVariable String city) {
        List<Persons> persons = personsRepository.findByCityOfResidence(city);

        //проверяется список на наличие объекта
        if (persons.isEmpty()) {
            return "Проверьте название города, пользователь не найден";
        }

        // Используем StringBuilder для построения ответа
        StringBuilder response = new StringBuilder();

        for (Persons person : persons) {
            response.append(person.toString()).append("\n");
        }
        return response.toString();//возвращается объект в виде строки
    }


    // Метод возвращает Persons по возрасту, меньше указанного в виде строки
    @GetMapping("/age/less-than/{age}") // http://localhost:8080/api/persons/age/less-than/20
    @PreAuthorize("hasRole('READ') or hasRole('WRITE')")
    public String getPersonsYoungerThan(@PathVariable int age) {
        List<Persons> persons = personsRepository.findByAgeLessThanOrderByAgeAsc(age);

        //проверяется список на наличие объекта
        if (persons.isEmpty()) {
            return "Пользователь не найден";
        }

        StringBuilder response = new StringBuilder();

        for (Persons person : persons) {
            response.append(person.toString()).append("\n");
        }
        return response.toString();
    }


    // метод возвращает Persons по совпадению имени и фамилии,  в виде строки
    @GetMapping("/name/{name}/surname/{surname}") // http://localhost:8080/api/persons/name/Olga/surname/Karpova
    @RolesAllowed("WRITE")
    public String getPersonByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        //из базы данных по заданным параметрам извлекаются объекты если они есть и сохраняются в person
        Optional<Persons> person = personsRepository.findByNameAndSurname(name, surname);

        //из списка объекты возвращаются по запросу пользователя если нет возвращается
        // значение записанное в orElse
        return person.map(Persons::toString).orElse("Проверьте правильность имени и фамилии,пользователь не найден"); // Возвращаем строку с информацией о человеке или сообщение об отсутствии
    }


    //метод предназначен для получения информации о пользователе по имени
    @GetMapping("/name")    //http://localhost:8080/api/persons/name?name=Ivan
    public String getPersonByName(@RequestParam String name) {

        //получает текущую аутентификацию пользователя из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяется, аутентифицирован ли пользователь
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Доступ запрещен"; // Если не аутентифицирован
        }

        // Проверяется, совпадает ли имя аутентифицированного пользователя с переданным
        if (!authentication.getName().equals(name)) {
            return "Доступ запрещен, проверьте написание запроса"; // Если имя не совпадает
        }

        // Если имя совпадает, ищем пользователя
        Optional<Persons> person = personsRepository.findByName(name);
        return person.map(Persons::toString).orElse("пользователь не найден");
    }

}

