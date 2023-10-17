package org.example.model;

import org.example.controller.dto.OperacionInputDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Operacion {
    private String tipo;
    private String horaEntrada;

    public Operacion(OperacionInputDto operacionInputDto) {
        this.tipo = operacionInputDto.getTipo();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.horaEntrada = dateFormat.format(Calendar.getInstance().getTime());
    }
    public Operacion(){}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}
