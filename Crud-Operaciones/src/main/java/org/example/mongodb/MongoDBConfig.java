package org.example.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.model.Operacion;
import org.example.model.OperacionHist;

import java.beans.BeanProperty;
import java.util.Objects;

public class MongoDBConfig {
    @BeanProperty
    public static MongoClient mongoClient(){
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/crud_Operacion");

            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            return MongoClients.create(mongoClientSettings);
        } catch (MongoException e){
            System.out.println("No se pudo conectar a la base de datos de MongoDB.\\" + e);
            return null;
        }
    }
    public static void initMongoDB(){
         MongoDatabase db = Objects.requireNonNull(mongoClient()).getDatabase("crud_Operacion");
         MongoCollection<Operacion> operacionMongoCollection = db.getCollection("operacion", Operacion.class);
         MongoCollection<OperacionHist> operacionHistMongoCollection = db.getCollection("operacionHist", OperacionHist.class);
         Objects.requireNonNull(mongoClient()).close();
    }
}
