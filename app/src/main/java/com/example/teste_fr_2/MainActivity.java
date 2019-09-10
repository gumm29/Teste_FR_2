package com.example.teste_fr_2;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    Spinner friends;
    Button btnDatePicker, btnTimePicker, btnMap, btnRequest, profile_to_aprove, profile_requested, logout, img_profile_btn;
    TextView txtDate, txtTime, place_to_play, lat, user, profile_name, req_aprove;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private AlertDialog alerta;
    private GoogleMap mMap;
    EditText locationSearch;
    Address address;
    LatLng sydney, latLng;
    String nome_loc = null;
    List<Address> addressList = null;
    String id, jogo_id;

    Intent intent;

    private final int SELECT_PHOTO = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final String Date = simpledateformat.format(calander.getTime());
        Log.i("data", Date);
        final String[] date = Date.split(" ");
        Log.i("data - split", date[0]);
        Log.i("hour - split", date[1]);

//        final ArrayList<String> teste_log = null;
        final ParseQuery<ParseObject> teste_jogo = new ParseQuery<ParseObject>("Jogo");

//        String teste_ganhador = teste_jogo.whereEqualTo( "Ganhador", ParseUser.getCurrentUser().getUsername() ).toString();
//        Log.i("ganhador", teste_ganhador);
//        if(!teste_ganhador.equals( ParseUser.getCurrentUser().getUsername())) {
//        teste_jogo.whereEqualTo("JogadorUm", ParseUser.getCurrentUser().getUsername());
            teste_jogo.whereEqualTo( "Response", "Aceito" );
            teste_jogo.findInBackground( new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseObject object : objects) {

                                Log.i( "teste", "hj 2 " );
                                String resposta = object.getString( "Response" );

                                String jogador1 = object.getString( "JogadorUm" );
                                String jogador2 = object.getString( "JogadorDois" );

                                String ganhador = object.getString( "Ganhador" );
                                String perdedor = object.getString( "Perdedor" );
                                //                            if (object.getString( "Response" ) == "Aceito") {
                                Log.i( "teste", "hj 3 " );

                                String parseHora = object.getString( "Hora" );
                                String parseData = object.getString( "Data" );

                                String teste_data = parseData + " " + parseHora;
                                Log.i( "data em date", teste_data );

                                SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );
                                Log.i( "testedta", parseData );
                                if (parseData.equals( date[0] )) {
                                    Log.i( "t", "hjooooo" );

                                    id = object.getObjectId();
                                    if ((jogador1.equals( ParseUser.getCurrentUser().getUsername())) || (jogador2.equals( ParseUser.getCurrentUser().getUsername() ))) {

//                                        if (ParseUser.getCurrentUser().getUsername().equals( ganhador ) ){
                                            try {
                                                Date data_jogo = hourFormat.parse( teste_data );
                                                Date data = new Date( data_jogo.getTime() - 1800000 );


                                                Calendar calander_ = Calendar.getInstance();
                                                SimpleDateFormat simpledateformat = new SimpleDateFormat( "HH:mm" );
                                                final String string_Date = simpledateformat.format( calander_.getTime() );
                                                final String menos30min = simpledateformat.format( data );
                                                final String jogo = simpledateformat.format( data_jogo );
                                                Log.i( "data", string_Date );

                                                Log.i( "data - 30", menos30min );
                                                Log.i( "data jogo", jogo );

                                                Date date_atual = simpledateformat.parse( string_Date );
                                                Date date_menos30min = simpledateformat.parse( menos30min );
                                                Date date_jogo = simpledateformat.parse( jogo );


                                                if (date_atual.after( date_menos30min ) && (date_atual.before( date_jogo ))) {

                                                    Log.i( "data - 30", data.toString() );
                                                    Log.i( "data jogo", data_jogo.toString() );
//                                                    String parseHora = object.getString( "Hora" );
                                                    teste_jogo.whereEqualTo( "objectId", id );
                                                        if (ParseUser.getCurrentUser().getUsername().equals( ganhador ) || ParseUser.getCurrentUser().getUsername().equals(perdedor)){
                                                            Log.i( "deu certo", "ganhador e perdedor nao retorna para o jogo");
                                                            break;
                                                        }else {
                                                            Log.i( "jogo ", "hoje" );
                                                            Intent intentEntrar = new Intent( MainActivity.this, Game.class );
                                                            intentEntrar.putExtra( "id", id );
                                                            Log.i( "id", id );
                                                            startActivity( intentEntrar );
                                                        }


                                                }
                                            } catch (java.text.ParseException e1) {
                                                e1.printStackTrace();
                                            }

                                    }
                                }
                            }
                        }
                    }
                }
            } );
//        }

//        final ParseQuery<ParseObject> teste_jogo2 = new ParseQuery<ParseObject>("Jogo");
//        teste_jogo2.whereEqualTo("JogadorDois", ParseUser.getCurrentUser().getUsername());
//        teste_jogo2.whereEqualTo( "Response", "Aceito" );
//        teste_jogo2.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    if (objects.size() > 0) {
//                        for (ParseObject object : objects) {
//                            Log.i("teste", "hj 2 ");
//                            String resposta = object.getString( "Response" );
////                            if (object.getString( "Response" ) == "Aceito") {
//                            Log.i("teste", "hj 3 ");
//
//                            String parseHora = object.getString( "Hora" );
//                            String parseData = object.getString( "Data" );
//
//                            String teste_data = parseData + " " + parseHora;
//                            Log.i( "data em date", teste_data );
//
//                            SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );
//                            Log.i("testedta", parseData);
//                            if (parseData.equals( date[0]) ) {
//                                Log.i("t", "hjooooo");
//
//                                try {
//                                    Date data_jogo = hourFormat.parse( teste_data );
//                                    Date data = new Date(data_jogo.getTime() - 1800000);
//
//
//                                    Calendar calander_ = Calendar.getInstance();
//                                    SimpleDateFormat simpledateformat = new SimpleDateFormat( "HH:mm" );
//                                    final String string_Date = simpledateformat.format( calander_.getTime() );
//                                    final String menos30min = simpledateformat.format( data );
//                                    final String jogo = simpledateformat.format( data_jogo );
//                                    Log.i( "data", string_Date );
//
//                                    Log.i("data - 30", menos30min);
//                                    Log.i("data jogo", jogo);
//
//                                    Date date_atual = simpledateformat.parse( string_Date );
//                                    Date date_menos30min = simpledateformat.parse( menos30min );
//                                    Date date_jogo = simpledateformat.parse( jogo);
//
//                                    if (date_atual.after( date_menos30min ) && (date_atual.before(date_jogo)) ) {
//
//                                        Log.i("data - 30", data.toString());
//                                        Log.i("data jogo", data_jogo.toString());
//
//                                        Log.i( "jogo ", "hoje" );
//                                        Intent intentEntrar = new Intent( MainActivity.this, Game.class );
//                                        startActivity( intentEntrar );
//
//                                    }
//                                } catch (java.text.ParseException e1) {
//                                    e1.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });



//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();

        //escrever em drawable

        View header = navigationView.getHeaderView(0);
        profile_name = header.findViewById(R.id.profileName);
        profile_to_aprove = header.findViewById(R.id.profile);
        profile_requested = header.findViewById(R.id.profile2);
        logout = header.findViewById(R.id.logout);

// upload de imagem de perfil
        img_profile_btn = header.findViewById( R.id.img_profile_btn );
        imageView = header.findViewById( R.id.imageView );

        img_profile_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foto = new Intent (Intent.ACTION_PICK);
                foto.setType("image/*");
                startActivityForResult( foto,SELECT_PHOTO );

            }
        } );




        logout.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                ParseUser.logOut();
                Intent intent = new Intent(MainActivity.this, EntrarUsuario.class);
                startActivity( intent );
            }
        } );

//       teste intent requisiçoes

        profile_to_aprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_aprove = new Intent(MainActivity.this, ToAprove.class);
                startActivity(to_aprove);
            }
        });

        profile_requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requested = new Intent(MainActivity.this, Requested.class);
                startActivity(requested);
            }
        });

        //teste numero de requisiçoes

        ParseQuery<ParseObject> teste = new ParseQuery<ParseObject>("Jogo");
        teste.whereEqualTo("JogadorDois", ParseUser.getCurrentUser().getUsername());
        teste.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        profile_to_aprove.setText("Requisiçoes para aprovar: " + String.valueOf(objects.size()));
                    }else{
                        profile_to_aprove.setText("Requisiçoes para aprovar: 0");
                    }
                }
            }
        });

        ParseQuery<ParseObject> teste2 = new ParseQuery<ParseObject>("Jogo");
        teste2.whereEqualTo("JogadorUm", ParseUser.getCurrentUser().getUsername());
        teste2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        profile_requested.setText("Requisiçoes enviadas: " + String.valueOf(objects.size()));
                    }else{
                        profile_requested.setText("Requisiçoes enviadas: 0");
                    }
                }
            }
        });

//        teste de map

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

//        other app
        user = findViewById(R.id.logged);
        friends = findViewById(R.id.friend_spinner);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Cadastro_FR");
        query.whereEqualTo("Apelido", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        user.setText("Olá " + ParseUser.getCurrentUser().getUsername());

                        profile_name.setText(ParseUser.getCurrentUser().getUsername());

                    }
                }
            }
        });


//      ****spinner
        final ArrayList<String> usernames = new ArrayList<>();
        final ArrayAdapter drop = new ArrayAdapter(this, android.R.layout.simple_spinner_item, usernames);
        drop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ParseQuery<ParseObject> test = ParseQuery.getQuery("Cadastro_FR");
        test.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {
                            usernames.add(object.getString("Apelido"));
                        }
                        friends.setAdapter(drop);
                    }
                }
            }
        });

        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        btnRequest = findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtDate.getText().toString().equals( "" ) || txtDate.getText().toString() == null || txtTime.getText().toString().equals( "" ) || txtTime.getText().toString() == null || nome_loc.equals( "" ) || nome_loc == null  ) {
                    Log.i("errado", "nome vazio");
                    Toast.makeText( MainActivity.this, "Preencha novamente, alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
                }else {
                    ParseObject request = new ParseObject( "Jogo" );
                    request.put( "JogadorUm", ParseUser.getCurrentUser().getUsername() );
                    request.put( "JogadorDois", friends.getSelectedItem().toString() );
                    request.put( "Data", txtDate.getText() );
                    request.put( "Hora", txtTime.getText() );
                    request.put( "Local", latLng.toString() );
                    request.put( "Nome_local", nome_loc );
                    request.saveInBackground( new SaveCallback() {
                        @Override
                        public void done(ParseException ex) {
                            if (ex == null) {
                                Log.i( "Parse Result", "Successful!" );

                                requisicao_enviada();
                                txtDate.setText( "" );
                                txtTime.setText("");
                                locationSearch.setText( "" );
                                addressList.clear();
                                mMap.clear();



                                ParseQuery<ParseObject> teste = new ParseQuery<ParseObject>( "Jogo" );
                                teste.whereEqualTo( "JogadorDois", ParseUser.getCurrentUser().getUsername() );
                                teste.findInBackground( new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            if (objects.size() > 0) {
                                                profile_to_aprove.setText( "Requisiçoes para aprovar: " + String.valueOf( objects.size() ) );
                                            }
                                        }
                                    }
                                } );

                                ParseQuery<ParseObject> teste2 = new ParseQuery<ParseObject>( "Jogo" );
                                teste2.whereEqualTo( "JogadorUm", ParseUser.getCurrentUser().getUsername() );
                                teste2.findInBackground( new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (e == null) {
                                            if (objects.size() > 0) {
                                                profile_requested.setText( "Requisiçoes enviadas: " + String.valueOf( objects.size() ) );
                                            }
                                        }
                                    }
                                } );
                            } else {
                                Log.i( "Parse Result", "Failed" + ex.toString() );
                            }
                        }
                    } );
                }
            }
        });
    }


// fazer logica para a foto ficar pequena
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);

//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        selectedImage.compress( Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                        byte[] teste = bos.toByteArray();
//                        ParseObject object = new ParseObject( "Cadastro_FR" );
//                        ParseFile file  = new ParseFile( "picture_1.jpeg", teste );
//                        // Upload the image into Parse Cloud
//                        ParseUser user = ParseUser.getCurrentUser();
//                        user.put("profilePic",file);
//
//                        user.saveInBackground(new SaveCallback() {
//                            @Override
//                            public void done(ParseException e) {
//                                Log.i("image", "upload");
//                            }
//                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), " para sair cliqe em logout",
                    Toast.LENGTH_SHORT).show();

        return false;
        // Disable back button..............
    }



    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            monthOfYear = monthOfYear + 1;
                            if (dayOfMonth<10 )  {
                                if(monthOfYear<10) {
                                    txtDate.setText( "0" + dayOfMonth + "-" + "0" + monthOfYear + "-" + year );
                                } else if (monthOfYear>=10){
                                    txtDate.setText( "0" + dayOfMonth + "-" + monthOfYear + "-" + year );
                                }
                            }
                            else if (dayOfMonth>=10){
//                                monthOfYear = monthOfYear + 1;
                                if(monthOfYear<10){
                                    txtDate.setText( dayOfMonth + "-" + "0" + monthOfYear + "-" + year );
                                }else if (monthOfYear >= 10){
                                    txtDate.setText( dayOfMonth + "-" + monthOfYear + "-" + year );
                                }
                            } else {
                                txtDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if (minute<10){
                                txtTime.setText(hourOfDay + ":" + "0" + minute);
                            }else{
                                txtTime.setText(hourOfDay + ":" + minute);
                            }

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Calendar calander = Calendar.getInstance();
            SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-M-yyyy HH:mm");
            final String Date = simpledateformat.format(calander.getTime());
            Log.i("data", Date);
            final String[] date = Date.split(" ");
            Log.i("data - split", date[0]);
            Log.i("hour - split", date[1]);


//        final ArrayList<String> teste_log = null;
            final ParseQuery<ParseObject> teste_jogo = new ParseQuery<ParseObject>("Jogo");
            teste_jogo.whereEqualTo("JogadorUm", ParseUser.getCurrentUser().getUsername());
//        teste_jogo.whereEqualTo( "Response", "True" );
            teste_jogo.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseObject object : objects) {
                                String resposta = object.getString( "Response" );
                                if (resposta == "Aceito") {

//                                String parseData = object.getString( "Data" );
//                                String parseHora = object.getString( "Hora" );
//                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

                                    String parseHora = object.getString( "Hora" );
                                    String parseData = object.getString( "Data" );

                                    String teste_data = parseData + " " + parseHora;
                                    Log.i( "data em date", teste_data );

                                    SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-M-yyy HH:mm" );

                                    if (parseData.equals( date[0] )) {

                                        try {
                                            Date data_jogo = hourFormat.parse( teste_data );
                                            Date data = new Date(data_jogo.getTime() - 1800000);


                                            Calendar calander_ = Calendar.getInstance();
                                            SimpleDateFormat simpledateformat = new SimpleDateFormat( "dd-M-yyyy HH:mm" );
                                            final String Date = simpledateformat.format( calander_.getTime() );
                                            Log.i( "data", Date );

                                            Log.i("data - 30", data.toString());
                                            Log.i("data jogo", data_jogo.toString());

                                            if (data.before( calander_.getTime() ) && (data_jogo.after(calander_.getTime())) ) {


                                                Log.i( "jogo ", "hoje" );
                                                Intent intentEntrar = new Intent( MainActivity.this, Game.class );
                                                startActivity( intentEntrar );


                                            }
                                        } catch (java.text.ParseException e1) {
                                            e1.printStackTrace();
                                        }
                                    }


//                                Calendar data_hj = Calendar.getInstance();
//                                Date p = dateFormat.parse( parseData,  new ParsePosition(5 ) );
//                                if (parseData.equals(  date[0])){
//
//                                    Log.i("jogo ", "hoje");
//                                    Intent intentEntrar = new Intent(MainActivity.this, Game.class);
//                                    startActivity(intentEntrar);

//                                    try {
//                                        Date data = hourFormat.parse(parseHora);
//                                        data.getTime();
//                                        Log.i("data em date", data.toString());
//                                    } catch (java.text.ParseException e1) {
//                                        e1.printStackTrace();
//                                    }
//                                }
//                                try {
//                                    dateFormat.parse( parseData,  new ParsePosition(5 ) );
//                                } catch (java.text.ParseException e1) {
//                                    e1.printStackTrace();
//                                }
//                                Log.i("data", parseData);
//                                Calendar teste = Calendar.getInstance();
//                                try {
//                                    data_hj.getTime(dateFormat.parse( parseData ));
//                                    Log.i("data", "try catch");
//                                    Date teste = dateFormat.parse( parseData );
//                                    if (teste == data_hj.getTime() ){
//                                        Log.i("data", "tem h=jogo hj");
////                                    }
//                                    hourFormat.parse( parseHora );
//                                } catch (java.text.ParseException e1) {
//                                    e1.printStackTrace();
//                                };


//                                if (convertDate.equals(data_hj)){
//                                    Log.i("data", "tem encontro no dia de hj");
//                                }else{
//                                   java.util.Date convertHour = hourFormat.parse( parseHora);
//                                   Log.i("data", "nao tem tem encontro no dia de hj");
//                                }


//                                convertHour = dateFormat.parse(parseHora);
//                                convertHour_now = dateFormat.parse(date[1]);


//                                Calendar n = Calendar.getInstance();
//                                n.setTime( convertHour_now );
//                                n.add( Calendar.HOUR, -2 );


                                }
                            }
                        }
                    }
//            }
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem myItem = menu.findItem(R.id.profile);
//        myItem.setTitle("bla");
//        return true;
//    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        Log.i("teste", "abrir");
//        final int id = item.getItemId();
//
//        if (id == R.id.profile) {
//            // Handle the camera action
//            Log.i("teste", "abrir");
//        } else if (id == R.id.req_enviadas) {
//            Log.i("teste", "abrir");
//
//        } else if (id == R.id.req_to_aprove) {
//            Intent to_aprove = new Intent(MainActivity.this, ToAprove.class);
//            startActivity(to_aprove);
//            Log.i("teste", "abrir");
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    LocationManager locationManager;
    LocationListener locationListener;

    //    map
    public void onMapSearch(View view) {
        Location locate = null;
//        nome_local = findViewById( R.id.lugar_mapa );
        locationSearch = findViewById(R.id.lugar_mapa);
        String location = locationSearch.getText().toString();
//        List<Address> addressList = null;

        if (location != null || !location.equals("")) {

            Geocoder geocoder = new Geocoder(this);

            try {
                addressList = geocoder.getFromLocationName( location, 1 );
                 if ( addressList.size() == 0){
                     Toast.makeText( MainActivity.this, "Preencha novamente, alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
                 }
                if (addressList == null){
                    Toast.makeText( MainActivity.this, "Preencha novamente, alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
                    return ;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText( MainActivity.this, "Preencha novamente, alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
                return;
            }
            if ( addressList.size() == 0){
                Toast.makeText( MainActivity.this, "Preencha novamente, alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
            }else {
                address = addressList.get( 0 );
//                addressList.get( -1 );
                latLng = new LatLng( address.getLatitude(), address.getLongitude() );
                mMap.addMarker( new MarkerOptions().position( latLng ).title( "Marker" ) );
                mMap.animateCamera( CameraUpdateFactory.newLatLngZoom( latLng, 15 ) );
                nome_loc = locationSearch.getText().toString();
                Log.i( "loc", locationSearch.getText().toString() );
            }


            //            faze logica de polygon

//            mMap.addPolyline(new PolylineOptions()
//                    .add(new LatLng(address.getLatitude(), address.getLongitude()), new LatLng(-23.525880, -46.478560))
//                    .width(5)
//                    .color(Color.RED));
        }

//        else{
//            Geocoder geocoder = new Geocoder(this);
//
//            try {
//                addressList = geocoder.getFromLocationName( location, 1 );
//            } catch (IOException e) {
////                Toast.makeText( MainActivity.this, "teste alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
//                e.printStackTrace();
//                return;
//            }
//            addressList.clear();
//            latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//            nome_loc = locationSearch.getText().toString();
//            Log.i("loc",locationSearch.getText().toString());
//
//
//            Toast.makeText( MainActivity.this, "Preencha novamente, alguma informação esta errada :( ", Toast.LENGTH_SHORT ).show();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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




//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                mMap.clear();
//                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(userLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
//
//                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//                try{
//                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
//
//                    if(listAddresses != null && listAddresses.size() <0){
//                        Log.i("pLACE iNFO",listAddresses.get(0).toString());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//            }
//        };
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
//            mMap.setMyLocationEnabled(true);
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void requisicao_enviada(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        //define o titulo
        builder.setTitle( "" );
        //define a mensagem
        builder.setMessage( "Requisição enviada!" );
        //define um botão como positivo

//        buildel
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}

