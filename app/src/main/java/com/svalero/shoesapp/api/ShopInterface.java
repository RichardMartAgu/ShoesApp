package com.svalero.shoesapp.api;

import com.svalero.shoesapp.domain.Shop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShopInterface {

    @GET("shops")
    Call<List<Shop>> getShops();

    @GET("shop/{shopId}")
    Call<Shop> getShopById(@Path("shopId") long shopId);

    @POST("shop")
    Call<Shop> addShop(@Body Shop shop);

    @DELETE("shop/{shopId}")
    Call<Shop> deleteShopById(@Path("shopId") long shopId);

    @PUT("shop/{shopId}")
    Call<Shop> editShopById(@Path("shopId") long shopId, @Body Shop shop);
}
