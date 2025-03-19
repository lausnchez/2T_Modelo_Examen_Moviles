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
Este segundo bloque es de David, la segunda línea es para optimizar la carga de imágenes.
```
implementation ("com.github.bumptech.glide:glide:4.12.0")
annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
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

## PASOS PARA IMPLEMENTAR UN RECYCLER VIEW
1. **Crear el Recycler View**  
Dentro del xml de una actividad (o un fragment) creamos un objeto de tipo RecyclerView.  
Es necesario poner bien las constraints para que funcione bien.
```
<androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_autor"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginStart="1dp"
        android:layout_marginTop="1dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
2. **Crear in Item para el RecyclerView**
Debe estar dentro de la carpeta de Layout.  
 Esto es un ejemplo de un Item con un único TextView.

 ```
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:paddingLeft="5dp">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <TextView
                android:id="@+id/lbl_listado_autor"
                android:layout_width="wrap_content"
                android:layout_height="50dp"
                android:layout_marginTop="5dp"
                android:width="230dp"
                android:gravity="left"
                android:paddingLeft="15dp"
                android:paddingTop="12dp"
                android:text="Autor"
                android:textColor="@color/design_default_color_error"
                android:textSize="17dp"
                android:textStyle="bold"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

        </androidx.constraintlayout.widget.ConstraintLayout>

    </LinearLayout>

</androidx.cardview.widget.CardView>
 ```

 3. **Crear un Adapter**  
 Es necesario crear un adapter para poder conectar el RecyclerView a los datos y el item donde se van a mostrar.  
 Esta plantilla tiene todo lo básico para generar una adapter desde 0, sólo hay que cambiar los nombres de la clase, el item, y el tipo de listado.
 ```
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.GenericViewHolder> {
    private static List<GenericItem> itemList;
    private Context context;

    public GenericAdapter(List<GenericItem> items, Context context) {
        this.context = context;
        this.itemList = items;
        this.notifyDataSetChanged();
    }

    // Método para actualizar la lista de datos
    public static void updateItemList(List<GenericItem> newList) {
        GenericAdapter.itemList = newList;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new GenericViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public GenericViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_id);
        }
    }
}

 ```

 4. **Configurar el RecyclerView dentro de la clase donde se vaya a usar**  
Primero deberemos localizar el RecyclerView, y crear una instancia del adapter para asignáresela al RecyclerView. Preferiblemente asignaremos el Layout y la Decoration antes que el Adapter para evitar problemas de renderizado.
 ```
RecyclerView rView = vista.findViewById(R.id.recyclerView);

RecyclerView.LayoutManager lManager = new LinearLayoutManager(contexto);
rView.setLayoutManager(lManager);

DividerItemDecoration deco = new DividerItemDecoration(contexto, DividerItemDecoration.VERTICAL);
rView.addItemDecoration(deco);

List<String> listado = response.body();
Adapter adapter = new AdapterGenero(listado, contexto);
rView.setAdapter(adapter);

 ```
