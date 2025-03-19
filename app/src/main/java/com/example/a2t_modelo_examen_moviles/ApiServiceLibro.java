package com.example.a2t_modelo_examen_moviles;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServiceLibro {

    // Recoger todos los libros
    @GET("api/libros")
    Call<List<Libro>> getAllLibros();

    // Recoge un libro por id
    @GET("api/libros/{id}")
    Call<Libro> getLibroById(@Path("id") int id);

    // Buscar libros por titulo
    @GET("api/libros/search/{titulo}")
    Call<List<Libro>> searchByBookTitle(@Path("titulo") String titulo);

    // Crea un libro
    @POST("api/libros")
    Call<Libro> createLibro(@Body Libro new_libro);

    // Updatea un libro por id
    @PUT("api/libros/{id}")
    Call<Libro> updateLibro(@Body Libro update_libro, @Path("id") int id);

    // Borra un libro por id
    @DELETE("api/libros/{id}")
    Call<Libro> deleteLibro(@Path("id") int id);
}
