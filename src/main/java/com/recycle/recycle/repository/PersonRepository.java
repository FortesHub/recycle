package com.recycle.recycle.repository;

import com.recycle.recycle.domain.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Optional<Person> findPersonById(String id);

}
