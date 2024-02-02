package com.svalero.shoesapp.contract;


import com.svalero.shoesapp.domain.Shop;

public interface ShopEditContract {

    interface Model {
        void loadEditOneShop(long shopId, Shop shop, OnLoadEditOneShopListener listener);
        interface OnLoadEditOneShopListener {
            void onLoadEditOneShopSuccess();
            void onLoadEditOneShopError(int stringId);
        }
    }
    interface Presenter {
        void editOneShop(long shopId,Shop shop);
    }

    interface View {
        void showMessage(int shopId);
        void showMessage(String message);
        }
    }

