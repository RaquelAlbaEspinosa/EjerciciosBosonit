package com.bosonit.CRUD.prueba.application;

import com.bosonit.CRUD.prueba.controller.dto.StudentInputDto;
import com.bosonit.CRUD.prueba.controller.dto.StudentOutputDto;

public interface StudentService {
    StudentOutputDto addStudent (StudentInputDto student);
    StudentOutputDto getStudentById(int id);
    void deleteStudentById(int id);
    Iterable<StudentOutputDto> getAllStudents(int pageNumber, int pageSize);
    StudentOutputDto updateStudent(StudentInputDto student);
}
