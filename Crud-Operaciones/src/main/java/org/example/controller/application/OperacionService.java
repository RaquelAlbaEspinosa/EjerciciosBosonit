package org.example.controller.application;

import org.example.controller.dto.OperacionInputDto;

public interface OperacionService {
    String addOperacion (OperacionInputDto operacionInputDto);
    Iterable<String> findAllOperacion (int pageNumber, int pageSize);
    String findOperacionById (String id);
    String updateOperacion (String id, OperacionInputDto operacionInputDto);
    void deleteOperacion (String id);
}
