package com.svalero.shoesapp.contract;

import com.svalero.shoesapp.domain.Shop;


public interface ShopDetailsContract {
    interface Model {
        interface OnLoadOneShopListener {
            void onLoadOneShopSuccess(Shop shop);
            void onLoadOneShopError(int stringId);
        }
        void loadOneShop(long shopId, OnLoadOneShopListener listener);
    }
    interface Presenter {
        void loadOneShop(long shopId);
    }

    interface View {
        void listOneShop(Shop shop);

        void showMessage(String message);
        void showMessage(int stringId);
    }


}

