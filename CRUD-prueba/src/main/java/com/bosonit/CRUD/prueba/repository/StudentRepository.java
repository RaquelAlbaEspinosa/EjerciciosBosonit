package com.bosonit.CRUD.prueba.repository;

import com.bosonit.CRUD.prueba.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
