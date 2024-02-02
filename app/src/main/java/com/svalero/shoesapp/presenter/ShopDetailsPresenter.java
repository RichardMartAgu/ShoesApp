package com.svalero.shoesapp.presenter;


import com.svalero.shoesapp.contract.ShopDetailsContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.model.ShopDetailsModel;

public class ShopDetailsPresenter implements ShopDetailsContract.Presenter, ShopDetailsContract.Model.OnLoadOneShopListener {

    private ShopDetailsContract.View view;
    private ShopDetailsContract.Model model;

    public ShopDetailsPresenter(ShopDetailsContract.View view) {
        this.view = view;
        model = new ShopDetailsModel();
    }

    @Override
    public void loadOneShop(long shopId) {
        model.loadOneShop(shopId, this);
    }

    @Override
    public void onLoadOneShopSuccess(Shop shop) {
        view.listOneShop(shop);
    }

    @Override
    public void onLoadOneShopError(int stringId) {
        view.showMessage(stringId);
    }

}