package com.fussyvegan.scanner;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClientRegister {

    private static Retrofit retrofit = null;

   public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fussyvegan.com.au/app_authenticate/rest/login.php?api_key=45090dcae2aYMK")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}

