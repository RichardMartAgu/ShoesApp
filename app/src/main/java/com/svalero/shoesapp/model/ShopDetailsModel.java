package com.svalero.shoesapp.model;

import static android.os.Build.VERSION_CODES.R;

import android.util.Log;

import com.svalero.shoesapp.api.ShopApi;
import com.svalero.shoesapp.api.ShopInterface;
import com.svalero.shoesapp.contract.ShopDetailsContract;
import com.svalero.shoesapp.domain.Shop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDetailsModel implements ShopDetailsContract.Model {

    public void loadOneShop(long shopId, OnLoadOneShopListener listener) {
        ShopInterface api = ShopApi.buildInstance();
        Call<Shop> getShopCall = api.getShopById(shopId);
        getShopCall.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (response.isSuccessful()) {
                    Shop shop = response.body();
                    listener.onLoadOneShopSuccess(shop);
                } else {
                    Log.e("getShopById", "Error al buscar por Id");
                    listener.onLoadOneShopError(R.string.error_search_by_id);
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.e("getAirportById", "Error en la solicitud: " + t.getMessage());
                listener.onLoadOneShopError(R.string.error_server);
            }
        });
    }


}
