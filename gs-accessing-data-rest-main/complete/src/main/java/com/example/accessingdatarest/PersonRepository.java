package com.example.accessingdatarest;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, CrudRepository<Person,Long> {

  @Cacheable("people")
  List<Person> findByLastName(@Param("name") String name);

  @Override
  @Cacheable("people")
  Iterable<Person> findAll();

  @Override
  @CacheEvict(value = "people", allEntries = true)
  <S extends Person> S save(S entity);

  @Override
  @CacheEvict(value = "people", allEntries = true)
  void deleteById(Long id);
}
