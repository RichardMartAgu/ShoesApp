package com.svalero.shoesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.svalero.shoesapp.views.ShopListView;

public class IndexView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
    }

    public void goListAirport(View view) {
        Intent intent = new Intent(this, ShopListView.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(this, IndexView.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.shop) {
            Intent intent = new Intent(this, ShopListView.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}