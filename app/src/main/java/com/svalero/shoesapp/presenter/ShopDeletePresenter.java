package com.svalero.shoesapp.presenter;

import com.svalero.shoesapp.contract.ShopDeleteContract;
import com.svalero.shoesapp.model.ShopDeleteModel;

public class ShopDeletePresenter implements ShopDeleteContract.Presenter, ShopDeleteContract.Model.OnLoadDeleteOneShopListener {
    private ShopDeleteContract.View view;
    private ShopDeleteContract.Model model;

    public ShopDeletePresenter(ShopDeleteContract.View view) {
        this.view = view;
        this.model = new ShopDeleteModel();
    }

    @Override
    public void onLoadDeleteOneShopSuccess() {
    }

    @Override
    public void onLoadDeleteOneShopError(int stringId) {
        view.showMessage(stringId);
    }

    @Override
    public void deleteOneShop(long shopId) {
        model.loadDeleteOneShop(shopId, this);
    }
}
