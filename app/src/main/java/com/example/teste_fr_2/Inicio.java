package com.example.teste_fr_2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

public class Inicio extends Activity {

    Button btn_cadastrar, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        if (ActivityCompat.checkSelfPermission(Inicio.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Inicio.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Inicio.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_login = findViewById(R.id.btn_login);

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Inicio.this,
                        CadastrarUsuario.class);
                startActivity(myIntent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(Inicio.this,
                        EntrarUsuario.class);
                startActivity(myIntent2);
            }
        });
    }

}
