package com.fussyvegan.scanner;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class API6Client {

    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://fussyvegan.com.au/app_fastfood_ca/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}

