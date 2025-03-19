package com.example.a2t_modelo_examen_moviles;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConfig {
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
        retrofit = new Retrofit.Builder().baseUrl(APIConfig.UrlBase).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}