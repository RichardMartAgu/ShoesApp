package com.svalero.shoesapp.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.svalero.shoesapp.IndexView;
import com.svalero.shoesapp.R;
import com.svalero.shoesapp.contract.ShopEditContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.presenter.ShopEditPresenter;
import com.svalero.shoesapp.utils.ValidatorUtil;

import java.util.Objects;

public class ShopEditView extends AppCompatActivity implements Style.OnStyleLoaded, OnMapClickListener, ShopEditContract.View {
    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private GesturesPlugin gesturesPlugin;
    private Point currentPoint;
    private long shopId;
    private String editName;
    double editLongitude;
    double editLatitude;
    private ShopEditContract.Presenter editPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_edit);
        editPresenter = new ShopEditPresenter(this, this);
        Intent intent = getIntent();

        shopId = Long.parseLong(Objects.requireNonNull(intent.getStringExtra("shop_details_id")));
        Log.d("Shop", "DetailsID " + shopId) ;
        editName = intent.getStringExtra("shop_details_name");
        editLongitude = Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("shop_details_latitude")));
        editLatitude = Double.parseDouble(Objects.requireNonNull(intent.getStringExtra("shop_details_longitude")));


        EditText nameView = findViewById(R.id.edit_shop_name);
        EditText longitudeView = findViewById(R.id.edit_shop_longitude);
        EditText latitudeView = findViewById(R.id.edit_shop_latitude);

        nameView.setText(editName);
        longitudeView.setText(String.valueOf(editLongitude));
        latitudeView.setText(String.valueOf(editLatitude));

        mapView = findViewById(R.id.edit_mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
        initializePointAnnotationManager();

        gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(this);

        Point point = (Point.fromLngLat(-4.25, 41.29));
        setCameraPosition(point);

    }

    public void editOneShop(View view) {
        Snackbar snackbar = Snackbar.make(view, "Seguro que quiere editar el registro?", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Editar definitivamente", view1 -> {

                    EditText editName = findViewById(R.id.edit_shop_name);
                    EditText editLongitude = findViewById(R.id.edit_shop_longitude);
                    EditText editLatitude = findViewById(R.id.edit_shop_latitude);

                    if (ValidatorUtil.areEditTextsValid(editName, editLongitude, editLatitude)) {
                        String name = editName.getText().toString();
                        double longitude = Double.parseDouble(editLongitude.getText().toString());
                        double latitude = Double.parseDouble(editLatitude.getText().toString());

                        Shop shop = new Shop(0, name, latitude, longitude);
                        Log.d("Shop", "DetailsID " + shopId) ;
                        editPresenter.editOneShop(shopId, shop);

                    } else {
                        showMessage("Por favor, completa todos los campos");
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light));
        snackbar.show();

    }


    private void initializePointAnnotationManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    private void addMarker(double latitude, double longitude, String title) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude))
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker))
                .withTextField(title);
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    @Override
    public boolean onMapClick(@NonNull Point point) {
        pointAnnotationManager.deleteAll();
        currentPoint = point;
        addMarker(point.latitude(), point.longitude(), getString(R.string.here));
        EditText shopLatitude = findViewById(R.id.edit_shop_latitude);
        shopLatitude.setText(String.valueOf(point.latitude()));
        EditText shopLongitude = findViewById(R.id.edit_shop_longitude);
        shopLongitude.setText(String.valueOf(point.longitude()));

        return false;
    }

    private void setCameraPosition(Point point) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(5.0)
                .bearing(-17.6)
                .build();
        mapView.getMapboxMap().setCamera(cameraPosition);
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {

    }


    @Override
    public void showMessage(String message) {
        View view = findViewById(R.id.coordinatorLayout);
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showMessage(int stringId) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(stringId), Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.go_list, view1 -> {
                    Intent intent = new Intent(this, ShopListView.class);
                    startActivity(intent);
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_blue_light));
        snackbar.show();
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
}

