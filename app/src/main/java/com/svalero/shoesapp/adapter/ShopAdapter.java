package com.svalero.shoesapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.views.ShopDetailsView;
import com.svalero.shoesapp.views.ShopListView;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {

    private List<Shop> shop;


    public ShopAdapter(List<Shop> shop) {
        this.shop = shop;

    }

    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_list_item, parent, false);
        return new ShopHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopHolder holder, int position) {
        Shop shop = this.shop.get(position);

        holder.shopId.setText(String.valueOf(shop.getId()));
        holder.shopName.setText(shop.getName());
    }

    @Override
    public int getItemCount() {
        return shop.size();
    }

    public class ShopHolder extends RecyclerView.ViewHolder {

        public TextView shopId;
        public TextView shopName;
        public Button detailsButton;
        public View parentView;

        public ShopHolder(@NonNull View view) {
            super(view);
            parentView = view;

            shopId = view.findViewById(R.id.shop_item_id);
            shopName = view.findViewById(R.id.shop_item_name);
            detailsButton = view.findViewById(R.id.button_details_shop);
            detailsButton.setOnClickListener(v -> goDetailsShop(view));

        }

    }

    public void goDetailsShop(View itemView) {
        long shopID = Long.parseLong(((TextView) itemView.findViewById(R.id.shop_item_id)).getText().toString());
        Intent intent = new Intent(itemView.getContext(), ShopDetailsView.class);
        intent.putExtra("shop_item_id", shopID);
        itemView.getContext().startActivity(intent);
    }
}
