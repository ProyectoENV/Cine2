package com.example.proyectocine.Api.Deserializers;

import com.example.proyectocine.Objetos.Pelicula;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MyDeserializers implements JsonDeserializer<Pelicula> {
    @Override
    public Pelicula deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String Titulo = json.getAsJsonObject().get("Title").getAsString();
        String Director = json.getAsJsonObject().get("Director").getAsString();
        int año = json.getAsJsonObject().get("Year").getAsInt();
        String Sinopsis = json.getAsJsonObject().get("Plot").getAsString();
        String Genero = json.getAsJsonObject().get("Genre").getAsString();
        String Imagen = json.getAsJsonObject().get("Poster").getAsString();

        Pelicula peli = new Pelicula(Titulo,Director,Sinopsis,Genero,Imagen, año);


        return peli;
    }

}
