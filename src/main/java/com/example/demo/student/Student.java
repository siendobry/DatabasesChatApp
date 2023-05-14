package com.example.demo.student;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    private String name;
    private String email;
    private LocalDate dov;
    private Integer age;



    public Student() {
    }

    public Student(Long id,
                   String name,
                   String email,
                   LocalDate dov,
                   Integer age
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dov = dov;
        this.email = email;
    }

    public Student(String name,
                   String email,
                   LocalDate dov,
                   Integer age
    ) {
        this.name = name;
        this.age = age;
        this.dov = dov;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getDov() {
        return dov;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDov(LocalDate dov) {
        this.dov = dov;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dov=" + dov +
                ", email='" + email + '\'' +
                '}';
    }
}
