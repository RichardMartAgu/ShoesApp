package com.svalero.shoesapp.model;

import static android.os.Build.VERSION_CODES.R;

import android.util.Log;

import com.svalero.shoesapp.api.ShopApi;
import com.svalero.shoesapp.api.ShopInterface;
import com.svalero.shoesapp.contract.ShopDeleteContract;
import com.svalero.shoesapp.domain.Shop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDeleteModel implements ShopDeleteContract.Model {


    @Override
    public void loadDeleteOneShop(long shopId, ShopDeleteContract.Model.OnLoadDeleteOneShopListener listener) {
        ShopInterface api = ShopApi.buildInstance();
        Call<Shop> deleteShopCall = api.deleteShopById(shopId);
        deleteShopCall.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if (response.isSuccessful()) {
                    listener.onLoadDeleteOneShopSuccess();

                } else {
                    Log.e("deleteShopById", "Error al borrar");
                    listener.onLoadDeleteOneShopError(R.string.error_to_delete);
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Log.e("deleteShopById", "Error en la solicitud: " + t.getMessage());
                listener.onLoadDeleteOneShopError(R.string.error_server);
            }
        });
    }
}
