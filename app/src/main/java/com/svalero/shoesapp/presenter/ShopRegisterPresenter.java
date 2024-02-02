package com.svalero.shoesapp.presenter;

import android.content.Context;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.contract.ShopRegisterContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.model.ShopRegisterModel;

public class ShopRegisterPresenter implements ShopRegisterContract.Presenter, ShopRegisterContract.Model.OnRegisterShopListener {

    private ShopRegisterContract.View view;
    private ShopRegisterContract.Model model;

    public ShopRegisterPresenter(ShopRegisterContract.View view, Context context) {
        this.view = view;
        model = new ShopRegisterModel(context);
    }

    @Override
    public void registerShop(Shop shop) {
        model.registerShop(this, shop);
    }

    @Override
    public void onRegisterShopSuccess() {
        view.showMessage(R.string.register_ok);
    }

    @Override
    public void onRegisterShopError(int stringId) {
        view.showMessage(stringId);
    }


}