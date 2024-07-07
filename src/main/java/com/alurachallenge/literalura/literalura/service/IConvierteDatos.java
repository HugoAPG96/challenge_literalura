package com.alurachallenge.literalura.literalura.service;

import java.util.List;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
    <T> List<T> obtenerListaDeDatos(String json, Class<T> clase);
}
