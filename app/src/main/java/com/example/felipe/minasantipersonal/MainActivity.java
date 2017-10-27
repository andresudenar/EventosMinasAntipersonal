package com.example.felipe.minasantipersonal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sCM(View view){

        ConnectivityManager connectivity = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info_wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo info_datos = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (String.valueOf(info_wifi.getState()).equals("CONNECTED") ||String.valueOf(info_datos.getState()).equals("CONNECTED") ){

            Intent inten = new Intent (this,MapsActivity.class);

            inten.putExtra("evento", "Sospecha de Campo Minado");

            startActivity(inten);

        }else {

            AlertDialog.Builder uBuilder = new AlertDialog.Builder(MainActivity.this);
            View aView = getLayoutInflater().inflate(R.layout.error, null);
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

    }

    public void acercade(View view){

        AlertDialog.Builder uBuilder = new AlertDialog.Builder(MainActivity.this);
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

    public  void cerrar(View view){
        finish();
    }
}
