package com.bosonit.CRUD.prueba.application;

import com.bosonit.CRUD.prueba.controller.dto.StudentInputDto;
import com.bosonit.CRUD.prueba.controller.dto.StudentOutputDto;
import com.bosonit.CRUD.prueba.domain.Student;
import com.bosonit.CRUD.prueba.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServicelmpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Override
    public StudentOutputDto addStudent (StudentInputDto student){
        return studentRepository.save(new Student(student))
                .studentToStudentOutputDto();
    }
    @Override
    public StudentOutputDto getStudentById(int id){
        return studentRepository.findById(id).orElseThrow()
                .studentToStudentOutputDto();
    }
    @Override
    public void deleteStudentById(int id){
        studentRepository.findById(id).orElseThrow();
        studentRepository.deleteById(id);
    }
    @Override
    public List<StudentOutputDto> getAllStudents(int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return studentRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Student::studentToStudentOutputDto).toList();
    }
    @Override
    public StudentOutputDto updateStudent(StudentInputDto student){
        studentRepository.findById(student.getId()).orElseThrow();
        return studentRepository.save(new Student(student))
                .studentToStudentOutputDto();
    }
    /*
    El método findById devuelve un Optional
    El método orElseThrow nos devuelve el dato si el Optional contiene datos o una excepción NoSuchElementException si no
    El repo funciona con clases Student por lo que los Dtos siempre deben ser transformados en Student
     */
}
