package org.example.demo_jpa_repositories.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;



//класс-сущность JPA, которая представляет собой объект в базе данных
//он аннотирован как @Entity, что указывает на то, что он должен быть
// сопоставлен с таблицей в базе данных

@Entity
@IdClass(PersonId.class)
public class Persons {

    @Id
    private String name;

    @Id
    private String surname;

    @Id
    private int age;

    private String phoneNumber;

    private String cityOfResidence;

    public Persons() {
    }

    public Persons(String name, String surname, int age, String phoneNumber, String cityOfResidence) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.cityOfResidence = cityOfResidence;
    }
    //переопредeляем метод
    @Override
    public String toString() {
        return name +" " + surname + " возраст: " +
                age + ", " + "номер телефона: " + phoneNumber +
                              ", проживает в " + cityOfResidence + " : "  +  "\n";

    }


    //геттеры и сеттеры
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getAge() {
        return this.age;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getCityOfResidence() {
        return this.cityOfResidence;
    }
}
