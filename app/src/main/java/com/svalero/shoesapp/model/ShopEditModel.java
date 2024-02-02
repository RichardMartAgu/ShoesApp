package com.svalero.shoesapp.model;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.api.ShopApi;
import com.svalero.shoesapp.api.ShopInterface;
import com.svalero.shoesapp.contract.ShopEditContract;
import com.svalero.shoesapp.domain.Shop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopEditModel implements ShopEditContract.Model {
    private Context context;

    public ShopEditModel(Context context) {
        this.context = context;
    }

    public void loadEditOneShop(long shopId, Shop shop, OnLoadEditOneShopListener listener) {
        ShopInterface api = ShopApi.buildInstance();
        Call<Shop> editShopCall = api.editShopById(shopId, shop);
        editShopCall.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (response.code() == HTTP_NO_CONTENT) {
                    Log.e("editAirport", response.message());
                    listener.onLoadEditOneShopSuccess();
                    Toast.makeText(context, "Editado con éxito", Toast.LENGTH_SHORT).show();
                } else if (response.code() == HTTP_BAD_REQUEST) {
                    listener.onLoadEditOneShopError(R.string.error_validation);

                } else if (response.code() == HTTP_NOT_FOUND) {
                    listener.onLoadEditOneShopError(R.string.error_shop_not_found);
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.e("editShop", "Error en la solicitud: " + t.getMessage());
                listener.onLoadEditOneShopError(R.string.error_server);

            }
        });
    }
}


