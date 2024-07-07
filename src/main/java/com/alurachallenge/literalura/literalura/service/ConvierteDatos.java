package com.alurachallenge.literalura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obtenerListaDeDatos(String json, Class<T> clase) {
        try {
            // Extraer la lista de libros desde la propiedad "results"
            String resultsJson = objectMapper.readTree(json).get("results").toString();
            return objectMapper.readValue(resultsJson, objectMapper.getTypeFactory().constructCollectionType(List.class, clase));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
