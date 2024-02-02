package com.svalero.shoesapp.contract;

import com.svalero.airadmin.domain.Airplane;
import com.svalero.shoesapp.domain.Shop;

import java.util.List;

public interface ShopListContract {

    interface Model {
        interface OnLoadAllShopListener {
            void onLoadAllShopSuccess(List<Shop> shop);

            void onLoadAllShopError(int stringId);
        }

        void loadAllShop(OnLoadAllShopListener listener);
    }

    interface View {
        void listAirplane(List<Shop> shop);
        void showMessage(int stringId);

        void showMessage(String message);
    }

    interface Presenter {
        void loadAllShop();
    }
}

