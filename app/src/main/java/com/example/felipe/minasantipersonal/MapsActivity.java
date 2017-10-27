package com.example.felipe.minasantipersonal;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String evento;
    private Retrofit retrofit;
    LatLng coordenada,colombia;
    private Spinner spinner;
    private List lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        spinner = (Spinner) findViewById(R.id.spinner);


        //spinner = (Spinner) View.findViewById(R.id.spinner);

        String[] te = {"Todo","Accidente por MAP","Accidente por MUSE","Arsenal almacenada","Desminado militar en operaciones",
                "Incautaciones","Municiones sin explotar", "Producción de Minas (Fábrica)","Sospecha de campo minado"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, te));


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        evento = getIntent().getExtras().getString("evento");

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());



        if (status == ConnectionResult.SUCCESS){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,(Activity)getApplicationContext(),10);
            dialog.show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        retrofit = new Retrofit.Builder().baseUrl("http://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SitiosServvice service = retrofit.create(SitiosServvice.class);
        Call<List<Minas>> sitioRespuestaCall = service.obtenerListadeSitios();



        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        // Add a marker in Colombia and move the camera

        colombia = new LatLng(4.0000000, -72.0000000);
        float zoomLevel = 5;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombia, zoomLevel));

        sitioRespuestaCall.enqueue(new Callback<List<Minas>>() {
            @Override
            public void onResponse(Call<List<Minas>> call, Response<List<Minas>> response) {

                lista = response.body();
                //List<String> yt = new ArrayList<String>();

                recorrer(spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        mMap.clear();

                        String option = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();

                        recorrer(option);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Another interface callback
                    }


                });

            }

            @Override
            public void onFailure(Call<List<Minas>> call, Throwable t) {

                Log.e("ERROR", " OnFaillure: "+ t.getMessage());
            }
        });
    }

    public void acercade(View view){

        AlertDialog.Builder uBuilder = new AlertDialog.Builder(MapsActivity.this);
        View aView = getLayoutInflater().inflate(R.layout.acercade, null);
        uBuilder.setView(aView);
        final AlertDialog dialog = uBuilder.create();
        dialog.show();

        ImageButton cerrar = (ImageButton) aView.findViewById(R.id.close);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void recorrer(String option){

        for(int i=0; i < lista.size(); i++){

            Minas d = (Minas) lista.get(i);

            double latitud = Double.parseDouble(d.getLatitudcabecera());
            double longitud = Double.parseDouble(d.getLongitudcabecera());
            String tipoEvento = (d.getTipoevento());
            String municipio = (d.getMunicipio());
            String anno = (d.getAno());


            coordenada = new LatLng(latitud, longitud);

            if(option.equals("Todo")){

                dibujarMarcador(tipoEvento, municipio, anno);

            }

            if(tipoEvento.equals(option)){

                dibujarMarcador(tipoEvento, municipio, anno);

            }

        }

    }

    public void dibujarMarcador (String tipoEvento, String municipio, String anno){

        if(tipoEvento.equals("Accidente por MAP")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        else if(tipoEvento.equals("Accidente por MUSE")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }

        else if(tipoEvento.equals("Arsenal almacenada")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        }

        else if(tipoEvento.equals("Desminado militar en operaciones")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        else if(tipoEvento.equals("Incautaciones")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        else if(tipoEvento.equals("Sospecha de campo minado")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        } else{
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Municipio: " + municipio + " Año:" + anno).snippet(tipoEvento)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        }

    }

    public void cerrar(View view){
        finish();
    }
}