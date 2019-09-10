package com.example.teste_fr_2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToAprove extends AppCompatActivity {

    ListView txt_to_aprove;
    ImageView img;
    private AlertDialog alerta;
    String id ;

//    ListView list;
//    String[] web = {
//            "Google Plus",
//            "Twitter",
//            "Windows",
//            "Bing",
//            "Itunes",
//            "Wordpress",
//            "Drupal"
//    } ;
//    Integer[] imageId = {
//            R.drawable.ic_menu_camera,
//            R.drawable.ic_menu_gallery,
//            R.drawable.ic_menu_share,
//            R.drawable.ic_menu_camera,
//            R.drawable.ic_menu_gallery,
//            R.drawable.ic_menu_share,
//            R.drawable.ic_menu_share
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_aprove);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        CustomList adapter = new
//                CustomList(this, web, imageId);
//        list=(ListView)findViewById(R.id.list);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
////                Toast.makeText(this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
//
//            }
//        });



        txt_to_aprove = findViewById(R.id.to_aprove);

        final ArrayList<String> usernames = new ArrayList<String>();
        final ArrayList<Integer> image = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,usernames);
        txt_to_aprove.setAdapter(arrayAdapter);

        final Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        final String Date = simpledateformat.format(calander.getTime());
        Log.i("data", Date);
        final String[] date = Date.split(" ");
        Log.i("data - split", date[0]);
        Log.i("hour - split", date[1]);

        final ParseQuery<ParseObject> teste_jogo = new ParseQuery<ParseObject>("Jogo");
        teste_jogo.whereEqualTo("JogadorDois", ParseUser.getCurrentUser().getUsername());
        teste_jogo.addDescendingOrder("JogadorDois");
        teste_jogo.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject object : objects) {

                            String parseHora = object.getString( "Hora" );
                            String parseData = object.getString( "Data" );

                            String teste_data = parseData + " " + parseHora;
                            Log.i( "data em date", teste_data );

                            SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );

//                            if (parseData.equals( date[0] )) {

                                try {
                                    Date data_jogo = hourFormat.parse( teste_data );
                                    Date data = new Date( data_jogo.getTime() );


                                    Calendar calander_ = Calendar.getInstance();
                                    SimpleDateFormat simpledateformat = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
                                    final String Date = simpledateformat.format( calander_.getTime() );
                                    Log.i( "data", Date );

                                    Log.i( "data - 30", data.toString() );
                                    Log.i( "data jogo", data_jogo.toString() );

                                    if ((data_jogo.after( calander_.getTime() ))) {

                                        Log.i( "data - 30", data.toString() );
                                        Log.i( "data jogo", data_jogo.toString() );

                                        String resposta = object.getString( "Response" );

                                        if (object.getString( "Response" ) == "" || object.getString( "Response" ) == null) {
                                            usernames.add( object.getString( "JogadorUm" ) + "/" + object.getString( "Nome_local" ) + "/" + object.getString( "Data" ) + " " + object.getString( "Hora" ) + "    " + "Aguardando resposta" );
                                            id = object.getObjectId();
                                        } else {
                                            usernames.add( object.getString( "JogadorUm" ) + "/" + object.getString( "Nome_local" ) + "/" + object.getString( "Data" ) + " " + object.getString( "Hora" ) + "    " + object.getString( "Response" ) );
                                            id = object.getObjectId();
                                        }
                                        txt_to_aprove.setAdapter( arrayAdapter );
//                                        Intent intentEntrar = new Intent( ToAprove.this ,MainActivity.class );
//                                        intentEntrar.putExtra( "id", id );
                                    }
                                } catch (java.text.ParseException e1) {
                                    e1.printStackTrace();
                                }
//                            }


// else if (object.getString( "Response" ) == null || object.getString( "Response" ) =="" ){
//                                usernames.add(object.getString("JogadorUm")+ "/" + object.getString( "Nome_local" )+ "/" + object.getString("Data")+ " " +object.getString("Hora")+"    "+ object.getString( "Response" ));
//                                o = object.getObjectId();
//                            }else if (object.getString( "Response" ) == "Recusado"){
//                                usernames.add(object.getString("JogadorUm")+ "/" + object.getString( "Nome_local" )+ "/" + object.getString("Data")+ " " +object.getString("Hora")+"    "+ object.getString( "Response" ));
//                                o = object.getObjectId();
//                            }

                        }
                    }
                }
//                }
            }
            });



//        ParseQuery<ParseObject> teste = new ParseQuery<ParseObject>("Jogo");
//        teste.whereEqualTo("JogadorDois", ParseUser.getCurrentUser().getUsername());
//        teste.addAscendingOrder("JogadorDois");
//        teste.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    if (objects.size() > 0) {
//                        for (ParseObject object : objects) {

//                        }



//                    }
//                }
//            }
//        });

        txt_to_aprove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapters, View childView, int position, long arg3) {
                // TODO Auto-generated method stub
                String teste__ = adapters.getItemAtPosition(position).toString();
                String[] testeSplit = teste__.split( "/");
                String[] testeSplit2 = testeSplit[2].split( " " );

                String dialog = testeSplit[0] + "\n" + testeSplit[1] + "\n" + testeSplit2[0] + "\n" + testeSplit2[1];
//                Toast.makeText(ToAprove.this,adapters.getItemAtPosition(position).toString()+" clicked",Toast.LENGTH_SHORT).show();
                exemplo_simples(position, dialog );
            }
        });
    }



    String response = null;

    private void exemplo_simples(int arg1, String teste__ ) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Requisição");
        //define a mensagem
        builder.setMessage(teste__);
        //define um botão como positivo
        builder.setPositiveButton("Positivo "+arg1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ToAprove.this, "Aceito", Toast.LENGTH_SHORT).show();
                response = "Aceito";
                final ParseQuery<ParseObject> object = new ParseQuery("Jogo");
                object.whereEqualTo( "objectId", id );
                object.findInBackground( new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null && objects != null){
                            for(ParseObject score: objects){
                                score.put("Response", response);
                                score.saveInBackground();
                            }
                            startActivity(getIntent());
                        }
                    }

                } );
//
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ToAprove.this, "Negado", Toast.LENGTH_SHORT).show();
                response = "Recusado";
                final ParseQuery<ParseObject> object = new ParseQuery("Jogo");
                object.whereEqualTo( "objectId", id );
                object.findInBackground( new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null && objects != null){
                            for(ParseObject score: objects){
                                score.put("Response", response);
                                score.saveInBackground();
                            }
                            startActivity(getIntent());
                        }
                    }
                } );
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(ToAprove.this, MainActivity.class);
        startActivity(intent);
    }
}
