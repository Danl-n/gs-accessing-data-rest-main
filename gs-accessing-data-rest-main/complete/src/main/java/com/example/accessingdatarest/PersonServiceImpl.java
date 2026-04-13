package com.example.accessingdatarest;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service("personServiceImpl")
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;

  public PersonServiceImpl(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public List<Person> findAll() {
    return StreamSupport.stream(personRepository.findAll().spliterator(), false).toList();
  }

  @Override
  public List<Person> findByLastName(String lastName) {
    return personRepository.findByLastName(lastName);
  }

  @Override
  public Optional<Person> findById(Long id) {
    return personRepository.findById(id);
  }

  @Override
  public Person save(Person person) {
    return personRepository.save(person);
  }

  @Override
  public Optional<Person> update(Long id, Person person) {
    return personRepository.findById(id)
        .map(existing -> {
          existing.setFirstName(person.getFirstName());
          existing.setLastName(person.getLastName());
          return personRepository.save(existing);
        });
  }

  @Override
  public boolean deleteById(Long id) {
    if (!personRepository.existsById(id)) {
      return false;
    }

    personRepository.deleteById(id);
    return true;
  }
}
