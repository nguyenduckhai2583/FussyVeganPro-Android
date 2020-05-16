package com.fussyvegan.scanner;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API2Client {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(boolean isVegan, String country) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://fussyvegan.com.au/app_fastfood_";

        if (isVegan) {
            url = url  + country+  "_vegan/rest/";

        } else {
            url = url  + country+  "/rest/";
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }


    public static Retrofit getClientPro(boolean isVegan) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://fussyvegan.com.au/app_products";

        if (isVegan) {
            url = url   +  "_vegan/rest/";

        } else {
            url = url  +  "/rest/";
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

    public static Retrofit getClientProNew() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://fussyvegan.com.au/app_foodchains/rest/";

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}

