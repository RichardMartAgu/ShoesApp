package com.svalero.shoesapp.api;

import static com.svalero.shoesapp.api.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopApi {

    public static ShopInterface buildInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ShopInterface.class);
    }
}
