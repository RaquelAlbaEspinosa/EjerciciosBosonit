package com.bosonit.CRUD.prueba.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentOutputDto {
    int id;
    String name;
    String lastName;
}
