# 2T_Modelo_Examen_Moviles
 
## PASOS PARA IMPLEMENTAR LA API CON RETROFIT
1. **Implementar las dependecias en Gradle (Module:App)**
```
/*Dependencias api*/
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10")
```
2. **Creamos el archivo network_security_config.xml**  
Tiene que estar dentro de la carpeta .xml
```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.10.1.50</domain>
        <domain includeSubdomains="true">https://biblioteca.guappi.com/</domain>
    </domain-config>
</network-security-config>
```

3. **Manipular el manifesto**  
Incluir dentro del manifesto en Application
```
android:networkSecurityConfig="@xml/network_security_config"
```
Incluir en el manifesto ANTES de Application
```
<uses-permission android:name="android.permission.INTERNET" />
```

4. **Crear la clase de APIConfig**
```
public class ApiConfig {
    public static final String protocolo = "https://";
    // URL de la api de David
    public static final String UrlBase = protocolo + "biblioteca.guappi.com/";
    // Token de entrada a la api
    public static final String token = "xxxxxxx";

    public static Retrofit retrofit = null;

    public static Retrofit recogerClienteRetrofit(){
        /*
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        */
        retrofit = new Retrofit.Builder().baseUrl(ApiConfig.UrlBase).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}

```

5. **Crear el modelo de datos**  
Es importante que los nombres de los atributos de la clase sean iguales a los del JSON para que no haya errores de parseo y que los tipos de datos sean iguales.

6. **Crear la interfaz de la API**
```
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

```

### Cómo implementar la API dentro de los modelos
Dentro de cada clase implementaremos los métdos de ésta manera.
```
public static Call<List<Libro>> getAllLibro() {
        ApiServiceLibro serviceLibro = ApiConfig.recogerClienteRetrofit().create(ApiServiceLibro.class);
        return serviceLibro.getAllLibros();
    }
```
```
public static boolean agregarLibro(Libro nuevoLibro){
        try{
            ApiServiceLibro serviceLibros = ApiConfig.recogerClienteRetrofit().create(ApiServiceLibro.class);
            serviceLibros.createLibro(nuevoLibro).enqueue(new Callback<Libro>() {
                @Override
                public void onResponse(Call<Libro> call, Response<Libro> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Libro creado con ID: " + nuevoLibro.getId());
                    }else{
                        System.out.println("Error de parseado del JSON");
                    }
                }
                @Override
                public void onFailure(Call<Libro> call, Throwable t) {
                    System.out.println("Error de red: failure en la conexión con la API al agregarLibro");
                    System.out.println(t);
                }
            });
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
```

## PASOS PARA IMPLEMENTAR UN GLIDE
1. **Implementar las dependencias**
```
implementation ("com.github.bumptech.glide:glide:4.13.0")
```
2. **Activar el permiso de Internet**  
Dentro del AndroidManifest pegar el permiso de internet
```
<uses-permission android:name="android.permission.INTERNET" />
```
3. **Usar el método de Glide donde sea necesario**  
En caso de que estemos usando una imagen de una Api es importante tener en cuenta que hay que pasarle la URL completa, así que hay que concatenar la urlBase en muchos casos.
```
Glide.with(contexto).load(urlImagen).into(imageView);
```
```
if(listado.get(position).getImagen() == null){
            Glide.with(contexto).load("https://bookstoreromanceday.org/wp-content/uploads/2020/08/book-cover-placeholder.png").into(holder.imagen);
        }else{
            Glide.with(contexto).load(ApiConfig.UrlBase + listado.get(position).getImagen()).into(holder.imagen);
        }
```
