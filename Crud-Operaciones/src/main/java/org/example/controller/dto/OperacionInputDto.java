package org.example.controller.dto;

public class OperacionInputDto {
    private String tipo;
    public OperacionInputDto(String tipo) {
        this.tipo = tipo;
    }
    public OperacionInputDto() {
        super();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
