package com.example.accessingdatarest;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProxyPersonService implements PersonService {

  private static final String ALL_PEOPLE_KEY = "proxy:people:all";

  private final PersonService target;

  public ProxyPersonService(@Qualifier("personServiceImpl") PersonService target) {
    this.target = target;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Person> findAll() {
    if (Cache.containsKey(ALL_PEOPLE_KEY)) {
      return (List<Person>) Cache.getCacheValue(ALL_PEOPLE_KEY);
    }

    List<Person> people = target.findAll();
    Cache.setCacheValue(ALL_PEOPLE_KEY, people);
    return people;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Person> findByLastName(String lastName) {
    String cacheKey = buildLastNameKey(lastName);

    if (Cache.containsKey(cacheKey)) {
      return (List<Person>) Cache.getCacheValue(cacheKey);
    }

    List<Person> people = target.findByLastName(lastName);
    Cache.setCacheValue(cacheKey, people);
    return people;
  }

  @Override
  public Optional<Person> findById(Long id) {
    return target.findById(id);
  }

  @Override
  public Person save(Person person) {
    Person savedPerson = target.save(person);
    clearCache();
    return savedPerson;
  }

  @Override
  public Optional<Person> update(Long id, Person person) {
    Optional<Person> updatedPerson = target.update(id, person);
    updatedPerson.ifPresent(value -> clearCache());
    return updatedPerson;
  }

  @Override
  public boolean deleteById(Long id) {
    boolean deleted = target.deleteById(id);

    if (deleted) {
      clearCache();
    }

    return deleted;
  }

  private String buildLastNameKey(String lastName) {
    return "proxy:people:lastName:" + lastName.trim().toLowerCase();
  }

  private void clearCache() {
    Cache.clear();
  }
}
