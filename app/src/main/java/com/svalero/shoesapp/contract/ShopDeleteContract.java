package com.svalero.shoesapp.contract;

public interface ShopDeleteContract {

    interface Model {
        void loadDeleteOneShop(long shopId, OnLoadDeleteOneShopListener listener);
        interface OnLoadDeleteOneShopListener {
            void onLoadDeleteOneShopSuccess();
            void onLoadDeleteOneShopError(int stringId);
        }
    }
    interface Presenter {
        void deleteOneShop(long shopId);
    }

    interface View {
        void showMessage(int stringId);
        void showMessage(String message);
    }
}
