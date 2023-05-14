package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args->{
            Student marian = new Student(
                    1L,
                    "Adam",
                    "amezydlo@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 4),
                    21
            );


            Student alex = new Student(
                    2L,
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 4),
                    21
            );


            repository.saveAll(
                    List.of(marian, alex)
            );

        };
    }
}
