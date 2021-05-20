package com.example.proyectocine.Api.ApiServices;


import com.example.proyectocine.Objetos.Pelicula;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmService {
    @GET("?type=movie")
    Call<Pelicula> getFilm(@Query("t") String titulo, @Query("apikey") String key);
    /*@GET("?type=movie&t={Titulo}&apikey={key}")
    Call<Pelicula> getFilm(@Path("Titulo") String titulo, @Path("Key") String key);*/

    /*@GET("weather")
    Call<Pelicula> getFilm(@Query("q") String city, @Query("appid") String key, @Query("units") String value);

    @GET("weather")
    Call<Pelicula> getFilm(@Query("q") String city, @Query("appid") String key, @Query("units") String value, @Query("lang") String lang);*/
}
