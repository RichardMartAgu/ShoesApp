package com.svalero.shoesapp.contract;


import com.svalero.shoesapp.domain.Shop;

public interface ShopRegisterContract {

    interface Model {
        interface OnRegisterShopListener {
            void onRegisterShopSuccess();
            void onRegisterShopError(int stringId);
        }
        void registerShop(OnRegisterShopListener listener, Shop shop);
    }
    interface View {
        void showMessage(int stringId);
        void showMessage(String message);
    }

    interface Presenter {
        void registerShop(Shop shop);
    }
}