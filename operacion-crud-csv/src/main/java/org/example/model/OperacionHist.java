package org.example.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OperacionHist {
    private String tipo;
    private String horaEntrada;
    private String horaProcesamiento;

    public OperacionHist(String tipo, String horaEntrada) {
        this.tipo = tipo;
        this.horaEntrada = horaEntrada;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.horaProcesamiento = dateFormat.format(Calendar.getInstance().getTime());
    }

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

    public String getHoraProcesamiento() {
        return horaProcesamiento;
    }

    public void setHoraProcesamiento(String horaProcesamiento) {
        this.horaProcesamiento = horaProcesamiento;
    }
}
