package com.svalero.shoesapp.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.svalero.shoesapp.R;
import com.svalero.shoesapp.contract.ShopRegisterContract;
import com.svalero.shoesapp.domain.Shop;
import com.svalero.shoesapp.presenter.ShopRegisterPresenter;
import com.svalero.shoesapp.utils.ValidatorUtil;

public class ShopRegisterView extends AppCompatActivity implements Style.OnStyleLoaded, OnMapClickListener, ShopRegisterContract.View {

    private MapView mapView;
    private PointAnnotationManager pointAnnotationManager;
    private GesturesPlugin gesturesPlugin;
    private Point currentPoint;
    private ShopRegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_register);
        presenter = new ShopRegisterPresenter(this, this);

        mapView = findViewById(R.id.add_mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, this);
        initializePointAnnotationManager();

        gesturesPlugin = GesturesUtils.getGestures(mapView);
        gesturesPlugin.addOnMapClickListener(this);

        Point point = (Point.fromLngLat(-4.25, 41.29));
        setCameraPosition(point);
    }

    public void createShop(View view) {
        EditText addName = findViewById(R.id.add_shop_name);
        EditText addLongitude = findViewById(R.id.add_shop_longitude);
        EditText addLatitude = findViewById(R.id.add_shop_latitude);

        if (ValidatorUtil.areEditTextsValid(addName, addLongitude, addLatitude)) {
            String name = addName.getText().toString();
            double latitude = Double.parseDouble(addLatitude.getText().toString());
            double longitude = Double.parseDouble(addLongitude.getText().toString());


            Shop shop = new Shop(0, name, latitude, longitude);
            presenter.registerShop(shop);


        } else {
            showMessage(R.string.field_incomplete);
        }
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
        EditText airportLatitude = findViewById(R.id.add_shop_latitude);
        airportLatitude.setText(String.valueOf(point.latitude()));
        EditText airportLongitude = findViewById(R.id.add_shop_longitude);
        airportLongitude.setText(String.valueOf(point.longitude()));
        return false;
    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {

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
}