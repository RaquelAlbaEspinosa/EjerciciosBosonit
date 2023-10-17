package org.example.controller.application;

import org.example.controller.dto.OperacionInputDto;
import org.example.model.Operacion;
import org.example.model.OperacionHist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class OperacionServiceImpl implements OperacionService{
    private final File fileOperacion = new File("C:/Users/raquel.alba/Documents/operacion-crud-csv/src/main/resources/operacion.csv");
    private final File fileOperacionHist = new File("C:/Users/raquel.alba/Documents/operacion-crud-csv/operacionHist.csv");
    private final BufferedReader bufferedReader = new BufferedReader(new FileReader(fileOperacion));
    private final BufferedReader bufferedReader2 = new BufferedReader(new FileReader(fileOperacionHist));

    public OperacionServiceImpl() throws FileNotFoundException {
    }

    @Override
    public void addOperacion() {
        try {
            File tempFile = new File("C:/Users/raquel.alba/Documents/operacion-crud-csv/src/main/resources/tempFile.csv");
            String line;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOperacionHist, true));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
            Predicate<String> checkTipoCompra = x -> x.split(", ")[1].equals("compra");
            List<String> existingIds = new ArrayList<>();
            String histLine;
            while ((histLine = bufferedReader2.readLine()) != null) {
                String[] histParts = histLine.split(", ");
                String histId = histParts[0].trim();
                existingIds.add(histId);
            }
            while ((line = bufferedReader.readLine()) != null) {
                String[] attributes = line.split(", ");
                String id = attributes[0].trim();
                if (checkTipoCompra.test(line)) {
                    if (!existingIds.contains(id)) {
                        bufferedWriter.write(generateOperacionHist(attributes));
                        bufferedWriter.newLine();
                    }
                } else {
                    writer.write(generateOperacion(attributes));
                    writer.newLine();
                }
            }
                writer.close();
                bufferedWriter.close();
                bufferedReader.close();
                fileOperacion.delete();
                tempFile.renameTo(fileOperacion);
        } catch (Exception e) {
            System.out.println("No se ha podido añadir la operación \n" + e);
        }
    }
    @Override
    public Iterable<String> findAllOperacion(int pageNumber, int pageSize) {
        try {
            List<String> operaciones = new ArrayList<>();
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null || (line = bufferedReader2.readLine()) != null){
                lineNumber++;

                if (lineNumber > (pageNumber - 1) * pageSize && lineNumber <= pageNumber * pageSize && !line.isBlank()) {
                    operaciones.add(line);
                } else if (lineNumber > pageNumber * pageSize) {
                    break;
                }
            }
            bufferedReader2.close();
            bufferedReader.close();
            return operaciones;
        } catch (Exception e){
            System.out.println("No se pudo formar el cursor\n" + e);
        }
        return null;
    }

    @Override
    public String findOperacionById(String id) {
        try {
            List<String> operaciones = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null || (line = bufferedReader2.readLine()) != null) {
                operaciones.add(line);
            }
            Predicate<String> findId = x -> x.split(",")[0].equals(id);
            return operaciones.stream().filter(findId).findFirst().orElseThrow();
        } catch (Exception e){
            return "No se ha encontrado la operacion con id: " + id;
        }
    }

    @Override
    public void updateOperacion(String id, OperacionInputDto operacionInputDto) {
        Operacion operacion = new Operacion(operacionInputDto);
        File tempFile = new File("C:/Users/raquel.alba/Documents/operacion-crud-csv/src/main/resources/tempFile.csv");
        File tempFileHist = new File("tempFileHist.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
             BufferedWriter writerHist = new BufferedWriter(new FileWriter(tempFileHist, true))) {
            String line;
            while ((line = bufferedReader.readLine()) != null || (line = bufferedReader2.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] attributes = line.split(", ");
                    if (attributes[0].equals(id)) {
                        attributes[1] = operacion.getTipo();
                    }
                    if (attributes[1].equals("compra")) {
                        writerHist.write(generateOperacionHist(attributes));
                        writerHist.newLine();
                    } else {
                        writer.write(generateOperacion(attributes));
                        writer.newLine();
                    }
                }
            }
            bufferedReader.close();
            bufferedReader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileOperacionHist.delete();
        fileOperacion.delete();

        tempFileHist.renameTo(fileOperacionHist);
        tempFile.renameTo(fileOperacion);
    }


    @Override
    public void deleteOperacion(String id) {
        File tempFile = new File("C:/Users/raquel.alba/Documents/operacion-crud-csv/src/main/resources/tempFile.csv");
        File tempFileHist = new File("C:/Users/raquel.alba/Documents/operacion-crud-csv/tempFileHist.csv");
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
                BufferedWriter writerHist = new BufferedWriter(new FileWriter(tempFileHist, true))
             ) {
            String line;
            while ((line = bufferedReader.readLine()) != null || (line = bufferedReader2.readLine()) != null) {
                String[] attributes = line.split(", ");
                if (!attributes[0].equals(id)) {
                    if(attributes[1].equals("compra")){
                        writerHist.write(generateOperacionHist(attributes));
                        writerHist.newLine();
                    } else {
                        writer.write(generateOperacion(attributes));
                        writer.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                bufferedReader2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileOperacionHist.delete();
        fileOperacion.delete();

        tempFileHist.renameTo(fileOperacionHist);
        tempFile.renameTo(fileOperacion);
    }
    private String generateOperacionHist (String[] attributes){
        Operacion operacion = new Operacion(new OperacionInputDto(attributes[1]));
        if(attributes.length > 2) {
            operacion.setHoraEntrada(attributes[2]);
        }
        OperacionHist operacionHist = new OperacionHist(operacion.getTipo(), operacion.getHoraEntrada());
        return String.join(", ", attributes[0],
                operacionHist.getTipo(), operacionHist.getHoraEntrada(), operacionHist.getHoraProcesamiento());
    }
    private String generateOperacion (String[] attributes){
        Operacion operacion = new Operacion(new OperacionInputDto(attributes[1]));
        if(attributes.length > 2) {
            operacion.setHoraEntrada(attributes[2]);
        }
        return String.join(", ", attributes[0], operacion.getTipo(), operacion.getHoraEntrada());
    }
}
