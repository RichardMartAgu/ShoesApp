package com.svalero.shoesapp.model;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;

import android.content.Context;
import android.util.Log;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.api.ShopApi;
import com.svalero.shoesapp.api.ShopInterface;
import com.svalero.shoesapp.contract.ShopRegisterContract;
import com.svalero.shoesapp.domain.Shop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopRegisterModel implements ShopRegisterContract.Model {
    private Context context;

    public ShopRegisterModel(Context context) {
        this.context = context;

    }

    public void registerShop(OnRegisterShopListener listener, Shop shop) {
        ShopInterface api = ShopApi.buildInstance();
        Call<Shop> addShopCall = api.addShop(shop);
        addShopCall.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (response.code() == HTTP_CREATED) {
                    Log.e("addShop", response.message());
                    listener.onRegisterShopSuccess();

                } else if (response.code() == HTTP_BAD_REQUEST) {
                    Log.e("addShop", response.message());
                    listener.onRegisterShopError(R.string.error_validation);
                }

            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {

                Log.e("addShop", "Error en la solicitud: " + t.getMessage());
                listener.onRegisterShopError(R.string.error_server);
            }
        });
    }

}
