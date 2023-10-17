package org.example;

import org.example.controller.ServerConfig;
import org.example.mongodb.MongoDBConfig;

public class Main {

    public static void main(String[] args) {
        MongoDBConfig.initMongoDB();
        ServerConfig.initServer();
    }
}