package com.svalero.shoesapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.svalero.shoesapp.IndexView;
import com.svalero.shoesapp.R;
import com.svalero.shoesapp.contract.ShopDeleteContract;
import com.svalero.shoesapp.contract.ShopDetailsContract;
import com.svalero.shoesapp.contract.ShopEditContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.presenter.ShopDeletePresenter;
import com.svalero.shoesapp.presenter.ShopDetailsPresenter;

public class ShopDetailsView extends AppCompatActivity implements ShopDetailsContract.View, ShopDeleteContract.View, ShopEditContract.View, Style.OnStyleLoaded {

    private MapView mapViewDetails;
    private PointAnnotationManager pointAnnotationManager;
    private long shopId;
    private String name;
    private double latitude;
    private double longitude;
    ShopDetailsContract.Presenter detailsPresenter = new ShopDetailsPresenter(this);
    ShopDeleteContract.Presenter deletePresenter = new ShopDeletePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);

        shopId = getIntent().getLongExtra("shop_item_id", 2);
        detailsPresenter.loadOneShop(shopId);

        mapViewDetails = findViewById(R.id.add_mapViewDetails);
        mapViewDetails.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);

    }

    public void listOneShop(Shop shop) {

        TextView shopIdView = findViewById(R.id.shop_details_id);
        TextView shopName = findViewById(R.id.shop_details_name);
        TextView shopLatitude = findViewById(R.id.shop_details_latitude);
        TextView shopLongitude = findViewById(R.id.shop_details_longitude);

        String airportIdText = String.valueOf(shopId);
        shopIdView.setText(airportIdText);
        shopName.setText(shop.getName());
        shopLatitude.setText(String.valueOf(shop.getLatitude()));
        shopLongitude.setText(String.valueOf(shop.getLongitude()));

        Point point = (Point.fromLngLat(shop.getLongitude(), shop.getLatitude()));
        setCameraPosition(point);

    }

    public void deleteOneShop(View view) {

        Snackbar snackbar = Snackbar.make(view, R.string.question_delete, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.question_delete_button, view1 -> {

                    deletePresenter.deleteOneShop(shopId);

                    Intent intent = new Intent(ShopDetailsView.this, ShopListView.class);
                    String message = getString(R.string.delete_sucefull);
                    intent.putExtra("Snackbar", message);
                    startActivity(intent);

                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light));
        snackbar.show();

    }

    public void goEditOneShop(View view) {

        String shopId = ((TextView) findViewById(R.id.shop_details_id)).getText().toString();
        String name = ((TextView) findViewById(R.id.shop_details_name)).getText().toString();
        String latitude = ((TextView) findViewById(R.id.shop_details_latitude)).getText().toString();
        String longitude = ((TextView) findViewById(R.id.shop_details_longitude)).getText().toString();

        Intent intent = new Intent(this, ShopEditView.class);
        intent.putExtra("shop_details_id", shopId);
        intent.putExtra("shop_details_name", name);
        intent.putExtra("shop_details_latitude", latitude);
        intent.putExtra("shop_details_longitude", longitude);

        view.getContext().startActivity(intent);

    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {
    }

    private void setCameraPosition(Point point) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(11.0)
                .bearing(-17.6)
                .build();
        mapViewDetails.getMapboxMap().setCamera(cameraPosition);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
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

    @Override
    public void showMessage(String message) {
        View view = findViewById(R.id.coordinatorLayout);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showMessage(int stringId) {

        Intent intent = new Intent(ShopDetailsView.this, ShopListView.class);
        intent.putExtra("Snackbar", stringId);
        startActivity(intent);
    }
}
