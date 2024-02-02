package com.svalero.shoesapp.presenter;

import android.content.Context;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.contract.ShopEditContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.model.ShopEditModel;

public class ShopEditPresenter implements ShopEditContract.Presenter, ShopEditContract.Model.OnLoadEditOneShopListener {

    private ShopEditContract.View view;
    private ShopEditContract.Model model;

    public ShopEditPresenter(ShopEditContract.View view, Context context) {
        this.view = view;
        this.model = new ShopEditModel(context);
    }

    @Override
    public void onLoadEditOneShopSuccess() {
        view.showMessage(R.string.edit_ok);
    }

    @Override
    public void onLoadEditOneShopError(int stringId) {
        view.showMessage(stringId);
    }

    @Override
    public void editOneShop(long shopId, Shop shop) {
        model.loadEditOneShop(shopId, shop, this);
    }
}
