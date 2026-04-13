package com.example.accessingdatarest;

import java.util.List;
import java.util.Optional;

public interface PersonService {

  List<Person> findAll();

  List<Person> findByLastName(String lastName);

  Optional<Person> findById(Long id);

  Person save(Person person);

  Optional<Person> update(Long id, Person person);

  boolean deleteById(Long id);
}
