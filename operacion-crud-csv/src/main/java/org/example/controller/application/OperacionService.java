package org.example.controller.application;

import org.example.controller.dto.OperacionInputDto;

public interface OperacionService {
    void addOperacion ();
    Iterable<String> findAllOperacion (int pageNumber, int pageSize);
    String findOperacionById (String id);
    void updateOperacion (String id, OperacionInputDto operacionInputDto);
    void deleteOperacion (String id);
}
