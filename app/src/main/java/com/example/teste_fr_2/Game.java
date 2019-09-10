package com.example.teste_fr_2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

public class Game extends AppCompatActivity implements LocationListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    //    public EditText apelido;
//    public EditText senha;
    double double_lat, double_lng, double_lat_jogador_2, double_lng_jogador_2;
    private GoogleMap mMap;
    LatLng marker, t, player_2;
    String teste, teste_j_2, id;
    LocationManager locationManager;
    LocationListener locationListener;
    TextView jogador, hora, local, data___, game_countdown;
    CircleOptions circulo;
    int counter;
    Intent intent;
    String jogo_id;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.game );

        jogador = findViewById( R.id.jogador );
        hora = findViewById( R.id.hora);
        local = findViewById( R.id.local);
        data___ = findViewById( R.id.data);
        game_countdown = findViewById( R.id.game_countdown);

        intent = getIntent();
        jogo_id = intent.getExtras().getString("id");

        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, this );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-M-yyyy HH:mm");
        final String Date = simpledateformat.format(calander.getTime());
        Log.i("data", Date);
        final String[] date = Date.split(" ");

        ParseQuery<ParseObject> teste = new ParseQuery<ParseObject>("Jogo");
//        teste.whereEqualTo("JogadorDois", ParseUser.getCurrentUser().getUsername());
        teste.whereEqualTo("objectId", jogo_id);
//        teste.whereEqualTo( "Response", "Aceito" );
        teste.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            String resposta = object.getString( "Response" );
//                            if (resposta == "Aceito") {
                                id = object.getObjectId();

                                Log.i("pagina jogo", "1");

                                String parseData = object.getString( "Data" );
                                String parseHora = object.getString( "Hora" );
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
//
                                String teste_data = parseData + " " + parseHora;
                                Log.i( "data em date", teste_data );
//
                                SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );
                                Log.i("testedta", parseData);
//                                if (parseData.equals( date[0]) ) {

                                    String t = object.getString( "Local" );
                                    Log.i("localização", t);
                                    String[] locSplit1 = t.split( ":" );
                                    Log.i("localização", locSplit1[0]);
                                    Log.i("localização", locSplit1[1]);
                                    String[] locSplit2 = locSplit1[1].split(",");
                                    int lat = locSplit2[0].length();
                                    int lng = locSplit2[1].length();
                                    lng = lng - 1;
                                    String lat_ok = locSplit2[0].substring( 2,lat);
                                    String lng_ok = locSplit2[1].substring( 0,lng );

                                    Log.i("localização", locSplit2[0].substring( 2,lat));
                                    Log.i("localização", locSplit2[1].substring( 0,lng ));

                                    double_lat = Double.parseDouble( lat_ok);
                                    double_lng = Double.parseDouble( lng_ok);

                                    String um = String.valueOf( double_lat );
                                    String dois = String.valueOf( double_lng );
                                    Log.i("localização", um );
                                    Log.i("localização", dois );

                                    new CountDownTimer(30000, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                            game_countdown.setText( String.valueOf( counter ) );
                                            counter++;
                                        }

                                        public void onFinish() {
                                            game_countdown.setText( "FINISH!!" );
                                        }
                                    }.start();

//                                }

//                            }
                        }
                    }
                }
            }
        });

//        apelido = findViewById(R.id.entrar_apelido);
//        senha = findViewById(R.id.entrar_senha);
//
//        Button entrar = findViewById(R.id.btn_entrar);
//        entrar.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                ParseUser.logInInBackground(apelido.getText().toString(), senha.getText().toString(), new LogInCallback() {
//                    @Override
//                    public void done(ParseUser user, ParseException e) {
//                        if(user != null){
//                            Log.i("Ok", "certo");
//                            Intent intentEntrar = new Intent(EntrarUsuario.this, MainActivity.class);
//                            startActivity(intentEntrar);
//                        }else{
//                            e.printStackTrace();
//                            Toast.makeText(EntrarUsuario.this, "Apelido o senha erradas! :(" , Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Location location;
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final String Date = simpledateformat.format(calander.getTime());
        Log.i("data", Date);
        final String[] date = Date.split(" ");

//        locationManager = (LocationManager) this.getSystemService( Context.LOCATION_SERVICE);


        final ParseQuery<ParseObject> teste = new ParseQuery<ParseObject>("Jogo");
//        teste.whereEqualTo("JogadorDois", ParseUser.getCurrentUser().getUsername());
//        teste.whereEqualTo( "Response", "Aceito" );
        teste.whereEqualTo("objectId", jogo_id);
        teste.findInBackground( new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            String resposta = object.getString( "Response" );
//                            if (resposta == "Aceito") {
                                String parseHora = object.getString( "Hora" );
                                String parseData = object.getString( "Data" );

                                String teste_data = parseData + " " + parseHora;
                                Log.i( "data em date", teste_data );

                                SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );
                                if (parseData.equals(  date[0])){

                                    try {
                                        Date data_jogo = hourFormat.parse( teste_data );
                                        Date data = new Date(data_jogo.getTime() - 1800000);


                                        Calendar calander_ = Calendar.getInstance();
                                        SimpleDateFormat simpledateformat = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
                                        final String Date = simpledateformat.format( calander_.getTime() );


                                        Log.i( "data", Date );

                                        Log.i("data - 30", data.toString());
                                        Log.i("data jogo", data_jogo.toString());

                                        if (data.before( calander_.getTime() ) && (data_jogo.after(calander_.getTime())) &&parseData.equals(  date[0]) ) {

                                            String[] min = data_jogo.toString().split( ":" );
                                            Log.i("min", min[1]);
                                            int minuto = Integer.parseInt( min[1] );
//                                            minuto = minuto * 100;
//
                                            String[] hora_atual = Date.split( ":" );
                                            Log.i("rest", hora_atual[1]);
                                            int contdown_min = Integer.parseInt( hora_atual[1] );
//                                            contdown_min = contdown_min * 10000;

                                            int restante = (minuto - contdown_min)*1000*60;

                                            jogador.setText( object.getString( "JogadorDois" ) );
                                            hora.setText( object.getString( "Hora" ) );
                                            local.setText( object.getString( "Nome_local" ) );
                                            data___.setText( object.getString( "Data" ) );

                                            counter = (minuto - contdown_min)*60;
                                            new CountDownTimer(restante, 1000) {
                                                public void onTick(long millisUntilFinished) {
                                                    game_countdown.setText( String.valueOf( counter) );
                                                    counter --;
                                                }

                                                public void onFinish() {
                                                    game_countdown.setText( "FINISH!!" );
                                                }
                                            }.start();

                                            String t = object.getString( "Local" );
                                            Log.i("localização", t);
                                            String[] locSplit1 = t.split( ":" );
                                            Log.i("localização", locSplit1[0]);
                                            Log.i("localização", locSplit1[1]);
                                            String[] locSplit2 = locSplit1[1].split(",");
                                            int lat = locSplit2[0].length();
                                            int lng = locSplit2[1].length();
                                            lng = lng - 1;
                                            String lat_ok = locSplit2[0].substring( 2,lat);
                                            String lng_ok = locSplit2[1].substring( 0,lng );

                                            Log.i("localização", locSplit2[0].substring( 2,lat));
                                            Log.i("localização", locSplit2[1].substring( 0,lng ));

                                            double_lat = Double.parseDouble( lat_ok);
                                            double_lng = Double.parseDouble( lng_ok);

                                            String um = String.valueOf( double_lat );
                                            String dois = String.valueOf( double_lng );
                                            Log.i("localização", um );
                                            Log.i("localização", dois );


                                            marker = new LatLng(double_lat,double_lng );
                                            mMap.addMarker( new MarkerOptions().position(marker));
                                            mMap.addPolyline(new PolylineOptions()
                                                    .width(500000000)
                                                    .color( Color.RED));

                                            circulo = new CircleOptions();
                                            circulo.center( marker );
                                            circulo.radius( 50 );
                                            circulo.strokeColor( Color.GREEN );
                                            circulo.fillColor( 0x30ff0000 );

                                            mMap.addCircle( circulo );
                                            Log.i("circulo", String.valueOf( circulo.getCenter().latitude ) );

                                            //                                    markeer 2

                                            teste_j_2 = object.getString("Loc_jogador_dois");

                                            if (teste_j_2 != null){
                                                //                                        Log.i( "testeeee", teste_j_2 );
                                                Log.i("uuuummmm","teste");
                                                String[] locSplit_jogador_2 = teste_j_2.split( ":" );
                                                Log.i( "localização_lat", locSplit_jogador_2[0] );
                                                Log.i( "localização_lng", locSplit_jogador_2[1] );
                                                String[] locSplit2_jogador_2 = locSplit_jogador_2[1].split( "," );
                                                //
                                                Log.i( "localização_lat_@", locSplit2_jogador_2[0] );
                                                Log.i( "localização_lng_@", locSplit2_jogador_2[1] );

                                                int lat_jogador_2 = locSplit2_jogador_2[0].length();
                                                int lng_jogador_2 = locSplit2_jogador_2[1].length();
                                                int new_lng_jogador_2 = lng_jogador_2 - 1;
                                                String lat_ok_jogador_2 = locSplit2_jogador_2[0].substring( 2,lat_jogador_2 );
                                                String lng_ok_jogador_2 = locSplit2_jogador_2[1].substring( 0,new_lng_jogador_2 );
                                                //
                                                Log.i( "localização Lat certa", lat_ok_jogador_2 );
                                                Log.i( "localização Lng certa", lng_ok_jogador_2 );
                                                //
                                                double_lat_jogador_2 = Double.parseDouble( lat_ok_jogador_2 );
                                                double_lng_jogador_2 = Double.parseDouble( lng_ok_jogador_2 );

                                                player_2 = new LatLng( double_lat_jogador_2, double_lng_jogador_2 );
                                                mMap.addMarker( new MarkerOptions().position( player_2 ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_AZURE ) ) );
                                            }
//                                            if (data.before( calander_.getTime() ) && (data_jogo.after(calander_.getTime())) ) {
//
//
//                                                Log.i( "jogo ", "hoje" );
//                                                Intent intentEntrar = new Intent( MainActivity.this, Game.class );
//                                                startActivity( intentEntrar );
//
//
//                                            }





//                                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                                    Log.i("posicao", userLocation.toString());

//                                    locationListener = new LocationListener() {
//                                        @Override
//                                        public void onLocationChanged(Location location) {
////                                            mMap.clear();
//                                            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//
//                                            mMap.addMarker(new MarkerOptions().position(userLocation).icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                                            mMap.moveCamera( CameraUpdateFactory.newLatLng(userLocation));
//
//                                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//                                            float[] distance = new float[1];
////                                    new LatLng( location.getLatitude(),location.getLongitude(), marker.latitude, marker.longitude, distance );
//                                            Location.distanceBetween(location.getLatitude(),location.getLongitude(), circulo.getCenter().latitude, circulo.getCenter().longitude, distance);
//                                            if(distance[0] > circulo.getRadius()) {
//                                                Log.i( "teste", "nao chegou" );
//                                            }else{
//                                                Log.i( "teste", "nao chegou" );
//                                            }
//                                            Log.i("posicao", userLocation.toString());
//
//
//                                            try{
//                                                List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
//
//                                                if(listAddresses != null && listAddresses.size() <0){
//                                                    Log.i("pLACE iNFO",listAddresses.get(0).toString());
//
//                                                    Log.i("posicao", userLocation.toString());
//                                                }
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                                        }
//
//                                        @Override
//                                        public void onProviderEnabled(String s) {
//
//                                        }
//
//                                        @Override
//                                        public void onProviderDisabled(String s) {
//
//                                        }
//                                    };

                                        }

                                    }   catch (java.text.ParseException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
//                        }
                    }}}
        });

        locationManager = (LocationManager) this.getSystemService( Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.moveCamera( CameraUpdateFactory.newLatLng(userLocation));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                float[] distance = new float[1];
//                                    new LatLng( location.getLatitude(),location.getLongitude(), marker.latitude, marker.longitude, distance );
                Location.distanceBetween(location.getLatitude(),location.getLongitude(), circulo.getCenter().latitude, circulo.getCenter().longitude, distance);
                if(distance[0] > circulo.getRadius()){

                }



                try{
                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);

                    if(listAddresses != null && listAddresses.size() <0){
                        Log.i("pLACE iNFO",listAddresses.get(0).toString());


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);







    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }


    @Override
    public void onLocationChanged(final Location location) {

        t = new LatLng(location.getLatitude(),location.getLongitude());
        teste = t.toString();
        Log.i("posicao", teste );
        Log.i("testecirculo", String.valueOf( circulo.getCenter().latitude ) );

        Log.i("circulo", circulo.toString());
        final float[] distance = new float[2];
        Location.distanceBetween( t.latitude, t.longitude,  circulo.getCenter().latitude, circulo.getCenter().longitude, distance);

//        Log.i("loc", String.valueOf( distance ) );
//        if( distance[0] > circulo.getRadius()  ){
//            Toast.makeText(getBaseContext(), "Ainda nao chegou - teste", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getBaseContext(), "Chegou", Toast.LENGTH_LONG).show();
//        }

        final ParseQuery<ParseObject> teste_jogo = new ParseQuery<ParseObject>("Jogo");
        teste_jogo.whereEqualTo( "objectId", jogo_id );
        teste_jogo.whereDoesNotExist( "Ganhador" );
        teste_jogo.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (final ParseObject object : objects) {
//                            String g = object.getString( "Ganhador" );
//                            Log.i("valor campo ganhador", g);

                            String parseHora = object.getString( "Hora" );
                            String parseData = object.getString( "Data" );

                            String teste_data = parseData + " " + parseHora;
                            Log.i( "data em date", teste_data );

                            SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );

                            try {
                                Date data = hourFormat.parse( teste_data );
                                Date data_limite_atraso = new Date(data.getTime());
//                                + 3600000);
                                Log.i("teste de data atrasado", data_limite_atraso.toString());
                                final Date hora_atual = new Date();
//
//                                Calendar calander_ = Calendar.getInstance();
//                                SimpleDateFormat simpledateformat = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
//                                final String Date = simpledateformat.format( calander_.getTime() );
//                                Log.i( "data", Date );

                                if (hora_atual.before( data )) {
                                    Log.i( "deu a hora", "na hora" );
                                    if (distance[0] > circulo.getRadius()) {
//                                        Toast.makeText( getBaseContext(), "Ainda nao chegou - oooo", Toast.LENGTH_LONG ).show();
                                    } else {
                                        Toast.makeText( getBaseContext(), "Chegou antes do horario " + ParseUser.getCurrentUser().getUsername() , Toast.LENGTH_LONG ).show();

                                        ParseUser.getCurrentUser().getUsername();


                                        teste_jogo.findInBackground( new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if(e==null && objects != null){
                                                    for(ParseObject score: objects){
                                                        score.put("Ganhador", ParseUser.getCurrentUser().getUsername());
                                                        score.saveInBackground();
                                                    }
                                                }

                                                String jogador;
                                                String jogador_atual = ParseUser.getCurrentUser().getUsername();
                                                if (ParseUser.getCurrentUser().getUsername().equals(  object.getString( "JogadorUm" ))){
//                                                    jogador = object.getString( "JogadorDois" );
                                                    Log.i("jogador um", "jog ");
                                                    ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>("Cadastro_FR");
                                                    pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                    Log.i("jogador um", "jog 2");
                                                    pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                                                          @Override
                                                                                          public void done(List<ParseObject> objects, ParseException e) {
                                                                                              if(e==null && objects != null){
                                                                                                  for(ParseObject score: objects){
                                                                                                      String t = score.getString( "Pontos" );
                                                                                                      int novo_ponto = Integer.parseInt(t) +1;
                                                                                                      String ponto_atualizado = String.valueOf( novo_ponto );
                                                                                                      score.put("Pontos", ponto_atualizado );
                                                                                                      score.saveInBackground();
                                                                                                  }
                                                                                              }
                                                                                          }
                                                                                      }
                                                    );
                                                    Log.i("jogador um", "jog 1");
                                                    popUp( "Parabéns " + ParseUser.getCurrentUser().getUsername() + ", você chegou no horário \n e ganhou 1 ponto!! :)");
//                                                    try {
//                                                        TimeUnit.SECONDS.sleep(15);
//                                                    } catch (InterruptedException e1) {
//                                                        e1.printStackTrace();
//                                                    }

                                                }else if (ParseUser.getCurrentUser().getUsername().equals(  object.getString( "JogadorDois" ))){

//                                                      jogador = object.getString( "JogadorDois" );
                                                        Log.i("jogador dois", "jog ");
                                                        ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>("Cadastro_FR");
                                                        pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                        Log.i("jogador atual", jogador_atual);
                                                        pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                                                              @Override
                                                                                              public void done(List<ParseObject> objects, ParseException e) {
                                                                                                  if(e==null && objects != null){
                                                                                                      for(ParseObject score: objects){
                                                                                                          String te = score.getString( "Pontos" );
                                                                                                          int novo_ponto = Integer.parseInt(te) +1;
                                                                                                          String ponto_atualizado = String.valueOf( novo_ponto );
                                                                                                          Log.i("meus pontos", te);
                                                                                                          score.put("Pontos", ponto_atualizado );
                                                                                                          score.saveInBackground();
                                                                                                      }
                                                                                                  }
                                                                                              }
                                                                                          }
                                                        );

//                                                    popUp( "teste um dois" );


//                                                    try {
//                                                        TimeUnit.SECONDS.sleep(15);
//                                                    } catch (InterruptedException e1) {
//                                                        e1.printStackTrace();
//                                                    }
//
//
////                                                    finish();
//
//                                                        Log.i("jogador um", "tttttttttttttt");
//                                                    Intent intent =  new Intent(Game.this, MainActivity.class);
//                                                    startActivity(intent);
                                                    popUp( "Parabéns " + ParseUser.getCurrentUser().getUsername() + ", você chegou no horário \n e ganhou 1 ponto!! :)");
                                                }

                                            }
                                        } );

                                    }
                                } else if ((hora_atual.after(data)) && (hora_atual.before(data_limite_atraso))) {
                                    Log.i( "metade deu a hora", "atrasado - teste" );
//                                    Toast.makeText( getBaseContext(), "ta atrasado, mas ainda no limite", Toast.LENGTH_LONG ).show()

                                    if (distance[0] > circulo.getRadius()) {
//                                        Toast.makeText( getBaseContext(), "Ainda nao chegou atrasado- oooo", Toast.LENGTH_LONG ).show();
                                    } else {
                                        Toast.makeText( getBaseContext(), "Chegou atrasado do horario", Toast.LENGTH_LONG ).show();

                                        teste_jogo.findInBackground( new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if (e == null && objects != null) {
                                                    for (ParseObject score : objects) {
                                                        score.put( "Perdedor", ParseUser.getCurrentUser().getUsername() );
                                                        score.saveInBackground();
                                                    }
                                                }

                                                String jogador_atual = ParseUser.getCurrentUser().getUsername();
                                                if (ParseUser.getCurrentUser().getUsername().equals( object.getString( "JogadorUm" ) )) {
                                                    //                                                    jogador = object.getString( "JogadorDois" );
                                                    Log.i( "jogador um", "jog " );
                                                    ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>( "Cadastro_FR" );
                                                    pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                    Log.i( "jogador um", "jog 2" );
                                                    pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                        @Override
                                                        public void done(List<ParseObject> objects, ParseException e) {
                                                            if (e == null && objects != null) {
                                                                for (ParseObject score : objects) {
                                                                    String t = score.getString( "Pontos" );
                                                                    int novo_ponto = Integer.parseInt( t ) - 1;
                                                                    String ponto_atualizado = String.valueOf( novo_ponto );
                                                                    score.put( "Pontos", ponto_atualizado );
                                                                    score.saveInBackground();
                                                                }
                                                            }
                                                        }
                                                    } );
                                                    Log.i( "jogador um", "jog 1" );
                                                    popUp( "Não foi dessa vez " + ParseUser.getCurrentUser().getUsername() + ", você não chegou no horário \n e perdeu 1 ponto!! :)");
                                                } else if (ParseUser.getCurrentUser().getUsername().equals( object.getString( "JogadorDois" ) )) {

                                                    //                                                      jogador = object.getString( "JogadorDois" );
                                                    Log.i( "jogador dois", "jog " );
                                                    ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>( "Cadastro_FR" );
                                                    pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                    Log.i( "jogador atual", jogador_atual );
                                                    pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                        @Override
                                                        public void done(List<ParseObject> objects, ParseException e) {
                                                            if (e == null && objects != null) {
                                                                for (ParseObject score : objects) {
                                                                    String te = score.getString( "Pontos" );
                                                                    int novo_ponto = Integer.parseInt( te ) - 1;
                                                                    String ponto_atualizado = String.valueOf( novo_ponto );
                                                                    Log.i( "meus pontos", te );
                                                                    score.put( "Pontos", ponto_atualizado );
                                                                    score.saveInBackground();
                                                                }
                                                            }
                                                        }
                                                    } );
//                                                    finish();
//
//                                                    Log.i( "jogador um", "tttttttttttttt" );
//                                                    Intent intent = new Intent( Game.this, MainActivity.class );
//                                                    startActivity( intent );
                                                    popUp( "Não foi dessa vez " + ParseUser.getCurrentUser().getUsername() + ", você não chegou no horário \n e perdeu 1 ponto!! :)");
                                                }
                                            }

                                            ;


                                        } );
                                    }
//                                } );
//                                    }
//                                    finish();
//                                    Intent intentEntrar = new Intent( Game.this, MainActivity.class );
//                                    startActivity( intentEntrar );
                                        } else if (hora_atual.after(data)) {
//                                            _limite_atraso

                                            String jogador_atual = ParseUser.getCurrentUser().getUsername();
                                            if (distance[0] > circulo.getRadius()) {

                                                teste_jogo.findInBackground( new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {
                                                        if (e == null && objects != null) {
                                                            for (ParseObject score : objects) {
                                                                score.put( "Perdedor", ParseUser.getCurrentUser().getUsername() );
                                                                score.saveInBackground();
                                                            }
                                                        }

                                                        Toast.makeText( getBaseContext(), "vc perdeu", Toast.LENGTH_LONG ).show();
                                                        String jogador_atual = ParseUser.getCurrentUser().getUsername();
                                                        if (ParseUser.getCurrentUser().getUsername().equals( object.getString( "JogadorUm" ) )) {
                                                            //                                                    jogador = object.getString( "JogadorDois" );
                                                            Log.i( "jogador um", "jog " );
                                                            ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>( "Cadastro_FR" );
                                                            pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                            Log.i( "jogador um", "jog 2" );
                                                            pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                                @Override
                                                                public void done(List<ParseObject> objects, ParseException e) {
                                                                    if (e == null && objects != null) {
                                                                        for (ParseObject score : objects) {
                                                                            String t = score.getString( "Pontos" );
                                                                            int novo_ponto = Integer.parseInt( t ) - 1;
                                                                            String ponto_atualizado = String.valueOf( novo_ponto );
                                                                            score.put( "Pontos", ponto_atualizado );
                                                                            score.saveInBackground();
                                                                        }
                                                                    }
                                                                }
                                                            } );
//                                                            finish();
//                                                            Intent intentEntrar = new Intent( Game.this, MainActivity.class );
//                                                            startActivity( intentEntrar );
//                                                            Log.i( "jogador um", "jog 1" );
                                                            popUp( "Não foi dessa vez " + ParseUser.getCurrentUser().getUsername() + ", você não chegou no horário \n e perdeu 1 ponto!! :)");


                                                        } else if (ParseUser.getCurrentUser().getUsername().equals( object.getString( "JogadorDois" ) )) {

                                                            if (e == null && objects != null) {
                                                                for (ParseObject score : objects) {
                                                                    score.put( "Perdedor", ParseUser.getCurrentUser().getUsername() );
                                                                    score.saveInBackground();
                                                                }
                                                            }

                                                            //                                                      jogador = object.getString( "JogadorDois" );
                                                            Log.i( "jogador dois", "jog " );
                                                            ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>( "Cadastro_FR" );
                                                            pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                            Log.i( "jogador atual", jogador_atual );
                                                            pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                                @Override
                                                                public void done(List<ParseObject> objects, ParseException e) {
                                                                    if (e == null && objects != null) {
                                                                        for (ParseObject score : objects) {
                                                                            String te = score.getString( "Pontos" );
                                                                            int novo_ponto = Integer.parseInt( te ) - 1;
                                                                            String ponto_atualizado = String.valueOf( novo_ponto );
                                                                            Log.i( "meus pontos", te );
                                                                            score.put( "Pontos", ponto_atualizado );
                                                                            score.saveInBackground();
                                                                        }
                                                                    }
                                                                }
                                                            } );

//                                                            finish();
//                                                            Intent intentEntrar = new Intent( Game.this, MainActivity.class );
//                                                            startActivity( intentEntrar );
                                                            popUp( "Não foi dessa vez " + ParseUser.getCurrentUser().getUsername() + ", você não chegou no horário \n e perdeu 1 ponto!! :)");
                                                        }
                                                    }
                                                } );
                                            }


                                            } else {
                                    Toast.makeText( getBaseContext(), "Chegou atrasado do horario", Toast.LENGTH_LONG ).show();

                                    teste_jogo.findInBackground( new FindCallback<ParseObject>() {
                                        @Override
                                        public void done(List<ParseObject> objects, ParseException e) {
                                            if (e == null && objects != null) {
                                                for (ParseObject score : objects) {
                                                    score.put( "Perdedor", ParseUser.getCurrentUser().getUsername() );
                                                    score.saveInBackground();
                                                }
                                            }

                                            if (ParseUser.getCurrentUser().getUsername().equals( object.getString( "JogadorUm" ) )) {
                                                //                                                    jogador = object.getString( "JogadorDois" );
                                                String jogador_atual = ParseUser.getCurrentUser().getUsername();
                                                Log.i( "jogador um", "jog " );
                                                ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>( "Cadastro_FR" );
                                                pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                Log.i( "jogador um", "jog 2" );
                                                pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {
                                                        if (e == null && objects != null) {
                                                            for (ParseObject score : objects) {
                                                                String t = score.getString( "Pontos" );
                                                                int novo_ponto = Integer.parseInt( t ) - 1;
                                                                String ponto_atualizado = String.valueOf( novo_ponto );
                                                                score.put( "Pontos", ponto_atualizado );
                                                                score.saveInBackground();
                                                            }
                                                        }
                                                    }
                                                } );
//                                                finish();
//                                                Intent intentEntrar = new Intent( Game.this, MainActivity.class );
//                                                startActivity( intentEntrar );
                                                popUp( "Não foi dessa vez " + ParseUser.getCurrentUser().getUsername() + ", você não chegou no horário \n e perdeu 1 ponto!! :)");
                                                Log.i( "jogador um", "jog 1" );
                                            } else if (ParseUser.getCurrentUser().getUsername().equals( object.getString( "JogadorDois" ) )) {

                                                //                                                      jogador = object.getString( "JogadorDois" );
                                                String jogador_atual = ParseUser.getCurrentUser().getUsername();
                                                Log.i( "jogador dois", "jog " );
                                                ParseQuery<ParseObject> pontos_jogador2 = new ParseQuery<ParseObject>( "Cadastro_FR" );
                                                pontos_jogador2.whereEqualTo( "Nome", jogador_atual );
                                                Log.i( "jogador atual", jogador_atual );
                                                pontos_jogador2.findInBackground( new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {
                                                        if (e == null && objects != null) {
                                                            for (ParseObject score : objects) {
                                                                String te = score.getString( "Pontos" );
                                                                int novo_ponto = Integer.parseInt( te ) - 1;
                                                                String ponto_atualizado = String.valueOf( novo_ponto );
                                                                Log.i( "meus pontos", te );
                                                                score.put( "Pontos", ponto_atualizado );
                                                                score.saveInBackground();
                                                            }
                                                        }
                                                    }
                                                } );

//                                                finish();
//                                                Intent intentEntrar = new Intent( Game.this, MainActivity.class );
//                                                startActivity( intentEntrar );
                                                popUp( "Não foi dessa vez " + ParseUser.getCurrentUser().getUsername() + ", você não chegou no horário \n e perdeu 1 ponto!! :)");
                                            }
                                        }

                                    } );
                                }
                            } catch (java.text.ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }


                ParseQuery<ParseObject> test_object = new ParseQuery( "Jogo" );
                test_object.whereEqualTo( "objectId", jogo_id );
                test_object.whereEqualTo( "JogadorDois", ParseUser.getCurrentUser().getUsername() );
                test_object.findInBackground( new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects != null) {
                            for (ParseObject loc : objects) {
                                loc.put( "Loc_jogador_dois", teste );
                                loc.saveInBackground();
                            }
                        }
                    }
                } );

                ParseQuery<ParseObject> test_object_um = new ParseQuery( "Jogo" );
                test_object_um.whereEqualTo( "objectId", jogo_id );
                test_object_um.whereEqualTo( "JogadorUm", ParseUser.getCurrentUser().getUsername() );
                test_object_um.findInBackground( new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects != null) {
                            for (ParseObject loc_um : objects) {
                                loc_um.put( "Loc_jogador_um", teste );
                                loc_um.saveInBackground();
                            }
                        }
                    }
                } );

//
            }
        });

        final ParseQuery<ParseObject> teste_jogo_n = new ParseQuery<ParseObject>("Jogo");
        teste_jogo_n.whereEqualTo( "objectId", jogo_id );
        teste_jogo_n.whereExists( "Ganhador" );
        teste_jogo_n.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (final ParseObject object : objects) {
                            finish();
                            Intent intentEntrar = new Intent( Game.this, MainActivity.class );
                            startActivity( intentEntrar );
                        }
                    }
                }
            }});
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void popUp(String texto ) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
//        builder.setTitle("Requisição");
        //define a mensagem
        builder.setMessage(texto);

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
