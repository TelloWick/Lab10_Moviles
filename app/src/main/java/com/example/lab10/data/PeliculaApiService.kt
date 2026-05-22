package com.example.lab10.data

import retrofit2.Response
import retrofit2.http.*

interface PeliculaApiService {

    @GET("pelicula")
    suspend fun selectPeliculas(): ArrayList<PeliculaModel>

    @GET("pelicula/{id}")
    suspend fun selectPelicula(
        @Path("id") id:String
    ): Response<PeliculaModel>

    @Headers("Content-Type: application/json")
    @POST("pelicula")
    suspend fun insertPelicula(
        @Body pelicula: PeliculaModel
    ): Response<PeliculaModel>

    @PUT("pelicula/{id}")
    suspend fun updatePelicula(
        @Path("id") id:String,
        @Body pelicula: PeliculaModel
    ): Response<PeliculaModel>

    @DELETE("pelicula/{id}")
    suspend fun deletePelicula(
        @Path("id") id:String
    ): Response<PeliculaModel>
}