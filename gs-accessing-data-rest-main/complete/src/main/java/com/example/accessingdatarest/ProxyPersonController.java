package com.example.accessingdatarest;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy/people")
public class ProxyPersonController {

  private final PersonService personService;

  public ProxyPersonController(PersonService personService) {
    this.personService = personService;
  }

  @GetMapping
  public List<Person> findAll(@RequestParam(required = false) String lastName) {
    if (lastName != null && !lastName.isBlank()) {
      return personService.findByLastName(lastName);
    }

    return personService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Person> findById(@PathVariable Long id) {
    return personService.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Person> create(@RequestBody Person person) {
    Person savedPerson = personService.save(person);
    return ResponseEntity.created(URI.create("/proxy/people/" + savedPerson.getId())).body(savedPerson);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person) {
    return personService.update(id, person)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (personService.deleteById(id)) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.notFound().build();
  }
}
