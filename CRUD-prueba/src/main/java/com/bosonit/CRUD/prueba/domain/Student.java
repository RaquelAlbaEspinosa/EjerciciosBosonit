package com.bosonit.CRUD.prueba.domain;

import com.bosonit.CRUD.prueba.controller.dto.StudentInputDto;
import com.bosonit.CRUD.prueba.controller.dto.StudentOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //le decimos que esta clase va a ser una tabla en la base de datos (nombre de clase = nombre de la tabla y atributos = columnas)
//crean código repetitivo:
@Data //getters, setters, toString, equals, hashcode
@NoArgsConstructor //crea un constructor vacío
@AllArgsConstructor //crea un constructor con todos los parámetros
public class Student {
    @Id //indica que va a ser un primary key en la tabla
    @GeneratedValue //genera automáticamente el id si no lo ponemos nosotros(por defecto, sin métodos, son valores enteros que empiezan por 1)
    int id;
    String name;
    String lastName;

    //para poder añadir Students a partir del Input
    public Student (StudentInputDto studentInputDto){
        this.id = studentInputDto.getId();
        this.name = studentInputDto.getName();
        this.lastName = studentInputDto.getLastName();
    }

    //para poder mostrar Students a través del Output
    public StudentOutputDto studentToStudentOutputDto(){
        return new StudentOutputDto(
                this.id,
                this.name,
                this.lastName
        );
    }
}
