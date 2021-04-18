package com.example.proyecto_cine.Api;

import com.example.proyecto_cine.Api.Deserializers.MyDeserializers;
import com.example.proyecto_cine.Objetos.Pelicula;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static final String BASE_URL = "http://www.omdbapi.com/";

    private static Retrofit retrofit = null;

    public static final String APPKEY = "df78f184";



    public static Retrofit getApi(){
        if (retrofit == null) {

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Pelicula.class, new MyDeserializers());

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }

}
