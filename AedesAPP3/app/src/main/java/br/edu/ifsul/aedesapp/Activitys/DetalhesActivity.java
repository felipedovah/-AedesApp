package br.edu.ifsul.aedesapp.Activitys;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ifsul.aedesapp.R;

public class DetalhesActivity extends AppCompatActivity implements OnMapReadyCallback{
    LatLng local;
    GoogleMap map;
    Integer statusid;
    TextView txtdetalhe;
    TextView txtstatus;
    ImageView imageDetalhe;
    Integer denunciaid;
    String imagemUrl;
    String data;
    Double latitude;
    MapView mMapView;
    Double longitude;
    MarkerOptions marker = new MarkerOptions();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        txtdetalhe = findViewById(R.id.detalheid);
        imageDetalhe = findViewById(R.id.imagemDetalhe);
        txtstatus = findViewById(R.id.statusDenuncia);
        mMapView = findViewById(R.id.mapView2);

        data = getIntent().getExtras().getString("data");
        latitude = getIntent().getExtras().getDouble("latitude");
        longitude = getIntent().getExtras().getDouble("longitude");
        imagemUrl = getIntent().getExtras().getString("foto");
        denunciaid = getIntent().getExtras().getInt("denunciaid");
        statusid = getIntent().getExtras().getInt("statusid");

        if(statusid == 1){
            txtstatus.setText("Sua ocorrência está em analise.");
        }else if(statusid == 2){
            txtstatus.setText("Sua ocorrência foi analisada, onde se constatou o indício de criadouro de mosquito e as ações necessárias já foram realizadas. Muito obrigado pela sua colaboração!");
        }else if(statusid == 3){
            txtstatus.setText("Não há indícios de foco do mosquito no local.");
        }else if(statusid == 4){
            txtstatus.setText("Não foi possivel verificar o local.");
        }else{
            txtstatus.setText(" Sua ocorrência está em analise.");
        }

        txtdetalhe.setText("Denuncia: " + denunciaid.toString());
        Picasso.with(this).load(imagemUrl).fit().into(imageDetalhe);


        local = new LatLng(latitude, longitude);

        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        /*mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });*/

        //map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Sua denuncia está aqui."));

    }

    public void setPosicao(LatLng local) {
        this.local = local;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        marker.position(local);
        marker.title("Localização Atual");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        map.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(local).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        setPosicao(marker.getPosition());
    }
}
