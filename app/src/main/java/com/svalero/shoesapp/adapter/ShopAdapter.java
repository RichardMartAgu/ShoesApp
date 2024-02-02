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
                .inflate(R.layout.airport_list_items, parent, false);
        return new ShopHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopHolder holder, int position) {
        Shop airport = this.shop.get(position);

        holder.shopId.setText(String.valueOf(airport.getId()));
        holder.shopName.setText(airport.getName());
    }

    @Override
    public int getItemCount() {
        return shop.size();
    }

    public class ShopHolder extends RecyclerView.ViewHolder {

        public TextView airportId;
        public TextView airportName;
        public Button detailsButton;
        public View parentView;

        public ShopHolder(@NonNull View view) {
            super(view);
            parentView = view;

            airportId = view.findViewById(R.id.airport_item_id);
            airportName = view.findViewById(R.id.airport_item_name);
            detailsButton = view.findViewById(R.id.button_details_airport);
            detailsButton.setOnClickListener(v -> goDetailsAirport(view));

        }

    }

    public void goDetailsAirport(View itemView) {
        long airportId = Long.parseLong(((TextView) itemView.findViewById(R.id.airport_item_id)).getText().toString());
        Intent intent = new Intent(itemView.getContext(), AirportDetailsView.class);
        intent.putExtra("airport_item_id", airportId);
        itemView.getContext().startActivity(intent);
    }
}
