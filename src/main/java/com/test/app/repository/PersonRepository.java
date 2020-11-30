package com.test.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.app.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	Person findByUsername(String username);
	
}
