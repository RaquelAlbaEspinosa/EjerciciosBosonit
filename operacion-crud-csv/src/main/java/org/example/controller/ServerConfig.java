package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpServer;
import org.example.controller.dto.OperacionInputDto;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerConfig {
    private static OperacionController operacionController;

    public static OperacionController getInstance(){
        if (operacionController == null){
            return new OperacionController();
        }
        return operacionController;
    }
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void initServer(){
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/operacion/add", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        String response = getInstance().addOperacion();

                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        try (OutputStream responseBody = exchange.getResponseBody()) {
                            responseBody.write(response.getBytes());
                        }
                    } catch (Exception e){
                        System.out.println( "Error en el response " + e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            });
            server.createContext("/operacion/all", exchange -> {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        String query = exchange.getRequestURI().getQuery();
                        Map<String, String> queryParams = parseQuery(query);

                        int pageNumber = Integer.parseInt(queryParams.getOrDefault("pageNumber", "1"));
                        int pageSize = Integer.parseInt(queryParams.getOrDefault("pageSize", "4"));

                        Iterable<String> response = getInstance().getAllOperacion(pageNumber, pageSize);

                        String responseJson = objectMapper.writeValueAsString(response);
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(200, responseJson.getBytes().length);
                        try (OutputStream responseBody = exchange.getResponseBody()) {
                            responseBody.write(responseJson.getBytes());
                        }
                    } catch (Exception e) {
                        System.out.println("Error en el response " + e);
                        exchange.sendResponseHeaders(500, -1);
                    }
                }
            });
            server.createContext("/operacion/byId", exchange -> {
                if ("GET".equals(exchange.getRequestMethod())) {
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    if(parts.length >= 3) {
                        String id = parts[3];
                        try {
                            String response = getInstance().getOperacionById(id);
                            exchange.getResponseHeaders().set("Content-Type", "application/json");
                            exchange.sendResponseHeaders(200, response.getBytes().length);
                            try (OutputStream responseBody = exchange.getResponseBody()) {
                                responseBody.write(response.getBytes());
                            }
                        } catch (Exception e) {
                            System.out.println("Error en el response " + e);
                            exchange.sendResponseHeaders(500, -1);
                        }
                    }
                }
            });
            server.createContext("/operacion/update", exchange -> {
                if ("PUT".equals(exchange.getRequestMethod())) {
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    if(parts.length >= 3) {
                        String id = parts[3];
                        InputStream requestBody = exchange.getRequestBody();

                        String requestBodyString = new BufferedReader(new InputStreamReader(requestBody))
                                .lines().collect(Collectors.joining("\n"));

                        OperacionInputDto operacionInputDto = null;
                        try {
                            operacionInputDto = objectMapper.readValue(requestBodyString, OperacionInputDto.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String response = getInstance().updateOperacion(id, operacionInputDto);
                            exchange.getResponseHeaders().set("Content-Type", "application/json");
                            exchange.sendResponseHeaders(200, response.getBytes().length);
                            try (OutputStream responseBody = exchange.getResponseBody()) {
                                responseBody.write(response.getBytes());
                            }
                        } catch (Exception e) {
                            System.out.println("Error en el response " + e);
                            exchange.sendResponseHeaders(500, -1);
                        }
                    }
                }
            });
            server.createContext("/operacion/delete", exchange -> {
                if ("DELETE".equals(exchange.getRequestMethod())) {
                    String path = exchange.getRequestURI().getPath();
                    String[] parts = path.split("/");
                    if(parts.length >= 3) {
                        String id = parts[3];
                        try {
                            String response = getInstance().deleteOperacion(id);
                            exchange.getResponseHeaders().set("Content-Type", "application/json");
                            exchange.sendResponseHeaders(200, response.getBytes().length);
                            try (OutputStream responseBody = exchange.getResponseBody()) {
                                responseBody.write(response.getBytes());
                            }
                        } catch (Exception e) {
                            System.out.println("Error en el response " + e);
                            exchange.sendResponseHeaders(500, -1);
                        }
                    }
                }
            });
                server.start();
        } catch (IOException e){
            System.out.println("No se ha podido conectar al servidor\\" + e);
        }
    }
    private static Map<String, String> parseQuery(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }
}
