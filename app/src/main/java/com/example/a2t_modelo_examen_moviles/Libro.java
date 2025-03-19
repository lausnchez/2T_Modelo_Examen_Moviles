package com.example.a2t_modelo_examen_moviles;

import java.util.List;

import retrofit2.Call;

public class Libro {
    /*
    {
      "id": 4,
      "titulo": "De amor y de sombra",
      "descripcion": "Otra brillante obra de Isabel Allende",
      "imagen": "wRCzwBxwsKKHRiyyesylfPkmdGGQpYJzVxWZkQUk.png",
      "autor": {
        "id": 2,
        "nombre": "Isabel Allende"
      },
      "genero": {
        "id": 10,
        "genero": "Biografía"
      }
    }
    * */

    private int id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private int genero_id;
    private int autor_id;


    //-----------------------------------------------------

    public int getAutorId() {
        return autor_id;
    }

    public void setAutorId(int autor_id) {
        this.autor_id = autor_id;
    }

    public int getGeneroID() {
        return genero_id;
    }

    public void setGeneroId(int generoID) {
        this.genero_id = generoID;
    }


    public Libro(int id, String titulo, String descripcion, String imagen, int autorID, int generoID) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.autor_id = autorID;
        this.genero_id = generoID;
    }

    public Libro(String titulo, String descripcion, String imagen, int autorID, int generoID) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.autor_id = autorID;
        this.genero_id = generoID;
    }

    public Libro(String titulo, String descripcion, int autorID, int generoID) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor_id = autorID;
        this.genero_id = generoID;
    }

    public Libro(int id, String titulo, String descripcion, int autorID, int generoID) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor_id = autorID;
        this.genero_id = generoID;
    }

    public Libro(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Libro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    // Métodos de la api
    public static Call<List<Libro>> getAllLibro() {
        ApiServiceLibro serviceLibro = APIConfig.recogerClienteRetrofit().create(ApiServiceLibro.class);
        return serviceLibro.getAllLibros();
    }
}
