package org.example.controller.application;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.controller.dto.OperacionInputDto;
import org.example.model.Operacion;
import org.example.model.OperacionHist;
import org.example.mongodb.MongoDBConfig;

import java.util.ArrayList;
import java.util.List;

public class OperacionServiceImpl implements OperacionService{
    private static MongoClient mongoClient;

    public static MongoClient getInstance(){
        if (mongoClient == null){
            return MongoDBConfig.mongoClient();
        }
        return mongoClient;
    }

    private static final MongoDatabase db = getInstance().getDatabase("crud_Operacion");
    private static final MongoCollection<Document> operacionMongoCollection = db.getCollection("operacion");
    private static final MongoCollection<Document> operacionHistMongoCollection = db.getCollection("operacionHist");

    @Override
    public String addOperacion(OperacionInputDto operacionInputDto) {
        try {
            Operacion operacion = new Operacion(operacionInputDto);
            Document operacionDoc = new Document("tipo", operacion.getTipo())
                        .append("horaEntrada", operacion.getHoraEntrada());
            OperacionHist operacionHist = new OperacionHist(operacion.getTipo(), operacion.getHoraEntrada());
            Document operacionHistDoc = new Document("tipo", operacionHist.getTipo())
                    .append("horaEntrada", operacionHist.getHoraEntrada())
                    .append("horaProcesamiento", operacionHist.getHoraProcesamiento());
            //si el tipo es venta me lo añade a la tabla de operacion
            if(operacionInputDto.getTipo().equals("venta")) {
                operacionMongoCollection.insertOne(operacionDoc);
                return operacionDoc.toJson();
            }
            //si el tipo es compra me lo añade a la tabla de operacionHist
            else if(operacionInputDto.getTipo().equals("compra")) {
                operacionHistMongoCollection.insertOne(operacionHistDoc);
                return operacionHistDoc.toJson();
            }
            else {
                return "Las operaciones deben ser de tipo compra o venta";
            }
        } catch (Exception e) {
            System.out.println("No se ha podido añadir la operación\\" + e);
            return "No se ha podido añadir";
        }
    }
    @Override
    public Iterable<String> findAllOperacion(int pageNumber, int pageSize) {
        int skip = (pageNumber - 1) * pageSize;
        try (MongoCursor<Document> cursor2 = operacionHistMongoCollection.find()
                .skip(skip)
                .limit(pageSize / 2)
                .iterator();
             MongoCursor<Document> cursor = operacionMongoCollection.find()
                .skip(skip)
                .limit(pageSize / 2)
                .iterator()
        ) {
            List<String> operaciones = new ArrayList<>();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                operaciones.add(document.toJson());
            }
            while (cursor2.hasNext()) {
                Document document = cursor2.next();
                operaciones.add(document.toJson());
            }
            return operaciones;
        } catch (Exception e) {
            System.out.println("No se pudo formar el cursor\\" + e);
        }
        return null;
    }

    @Override
    public String findOperacionById(String id) {
        ObjectId objectId = new ObjectId(id);
        Document operacionDocument = operacionMongoCollection.find(new Document("_id", objectId)).first();
        Document operacionHistDocument = operacionHistMongoCollection.find(new Document("_id", objectId)).first();
        if(operacionDocument != null) {
            return operacionDocument.toJson();
        } else if (operacionHistDocument != null){
            return operacionHistDocument.toJson();
        } else {
            return "No se ha encontrado la operación con id: " + id;
        }
    }

    @Override
    public String updateOperacion(String id, OperacionInputDto operacionInputDto) {
        ObjectId objectId = new ObjectId(id);
            if(operacionInputDto.getTipo().equals("venta")){
                operacionHistMongoCollection.updateOne(
                        new Document("_id", objectId),
                        new Document("$set", new Document("tipo", operacionInputDto.getTipo()))
                );
                Document updatedDocument = operacionHistMongoCollection.find(new Document("_id", objectId)).first();
                if(updatedDocument != null) {
                    operacionHistMongoCollection.deleteOne(
                            new Document("_id", objectId)
                    );
                    operacionMongoCollection.insertOne(
                            new Document("tipo", updatedDocument.getString("tipo") != null)
                                    .append("horaEntrada", updatedDocument.getString("horaEntrada"))
                    );

                    return updatedDocument.toJson();
                } else {
                    return "No se encontró el documento";
                }
            } else if (operacionInputDto.getTipo().equals("compra")){
                operacionMongoCollection.updateOne(
                        new Document("_id", objectId),
                        new Document("$set", new Document("tipo", operacionInputDto.getTipo()))
                );
                Document updatedDocument = operacionMongoCollection.find(new Document("_id", objectId)).first();
                if(updatedDocument != null) {
                    operacionMongoCollection.deleteOne(
                            new Document("_id", objectId)
                    );
                    OperacionHist operacionHist = new OperacionHist(updatedDocument.getString("tipo"),
                            updatedDocument.getString("horaEntrada"));
                    operacionHistMongoCollection.insertOne(
                            new Document("tipo", operacionHist.getTipo())
                                    .append("horaEntrada", operacionHist.getHoraEntrada())
                                    .append("horaProcesamiento", operacionHist.getHoraProcesamiento())
                    );
                    return updatedDocument.toJson();
                } else {
                    return "No se encontró el documento";
                }
            } else {
                return "No se pudo encontrar la operacion con id: " + id;
            }
    }

    @Override
    public void deleteOperacion(String id) {
        ObjectId objectId = new ObjectId(id);
        if(operacionMongoCollection.find(new Document("_id", objectId))
                .first() != null) {
            operacionMongoCollection.deleteOne(new Document("_id", objectId));
        } else if(operacionHistMongoCollection.find(new Document("_id", objectId))
                .first() != null) {
            operacionHistMongoCollection.deleteOne(new Document("_id", objectId));
        }
    }
}
