package com.svalero.shoesapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import com.svalero.shoesapp.R;
import com.svalero.shoesapp.adapter.ShopAdapter;
import com.svalero.shoesapp.contract.ShopListContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.presenter.ShopListPresenter;

import java.util.ArrayList;
import java.util.List;

public class ShopListView extends AppCompatActivity implements ShopListContract.View {
    private List<Shop> shop;
    private ShopAdapter adapter;
    private ShopListContract.Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane_list);

        shop = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.airplane_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new ShopAdapter(shop,this);
        recyclerView.setAdapter(adapter);

        presenter = new ShopListPresenter(this);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::loadStationsData);



        Intent intent = getIntent();
        if (intent.hasExtra("Snackbar")) {
            String message = intent.getStringExtra("Snackbar");
            View view = findViewById(R.id.coordinatorLayout);
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }

    }

    private void loadStationsData() {
        new Handler().postDelayed(() -> {
            presenter.loadAllShop();
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadAllShop();
    }

    public void goRegisterAirplane(View view) {
        Intent intent = new Intent(this, ShopRegisterView.class);
        startActivity(intent);
    }

    @Override
    public void listAirplane(List<Shop> shop) {
        this.shop.clear();
        this.shop.addAll(shop);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.volver) {
            Intent intent = new Intent(this, IndexView.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.airports) {
            Intent intent = new Intent(this, AirportListView.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.airplanes) {
            Intent intent = new Intent(this, AirplaneListView.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.fav_airplanes) {
            Intent intent = new Intent(this, FavoritesAirplaneListView.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void  showMessage(String message) {
        View view = findViewById(R.id.coordinatorLayout);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showMessage(int stringId) {
        View view = findViewById(R.id.coordinatorLayout);
        Snackbar.make(view, getResources().getString(stringId), Snackbar.LENGTH_SHORT).show();
    }


}
