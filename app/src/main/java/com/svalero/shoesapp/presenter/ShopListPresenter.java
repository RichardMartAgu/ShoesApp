package com.svalero.shoesapp.presenter;

import com.svalero.shoesapp.contract.ShopListContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.model.ShopListModel;

import java.util.List;

public class ShopListPresenter implements ShopListContract.Presenter, ShopListContract.Model.OnLoadAllShopListener {

    private ShopListContract.View view;
    private ShopListContract.Model model;

    public ShopListPresenter(ShopListContract.View view) {
        this.view = view;
        model = new ShopListModel();
    }


    @Override
    public void onLoadAllShopSuccess(List<Shop> shop) {
        view.listShop(shop);
    }

    @Override
    public void onLoadAllShopError(int stringId) {
        view.showMessage(stringId);
    }


    @Override
    public void loadAllShop() {
        model.loadAllShop(this);
    }
}

