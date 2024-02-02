package com.svalero.shoesapp.model;

import android.util.Log;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.api.ShopApi;
import com.svalero.shoesapp.api.ShopInterface;
import com.svalero.shoesapp.contract.ShopListContract;
import com.svalero.shoesapp.domain.Shop;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListModel implements ShopListContract.Model {

    @Override
    public void loadAllShop(ShopListContract.Model.OnLoadAllShopListener listener) {
        ShopInterface api = ShopApi.buildInstance();
        Call<List<Shop>> getShopCall = api.getShops();
        getShopCall.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                Log.e("getShop", response.message());
                List<Shop> shops = response.body();
                listener.onLoadAllShopSuccess(shops);
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                Log.e("getShop", t.getMessage());
                listener.onLoadAllShopError(R.string.error_server);
            }
        });
    }
}
