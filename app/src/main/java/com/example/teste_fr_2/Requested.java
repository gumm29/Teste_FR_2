package com.example.teste_fr_2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Requested extends AppCompatActivity {

    ListView txt_to_aprove;
    private AlertDialog alerta;
    String o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.requested );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        txt_to_aprove = findViewById( R.id.requested );
        final ArrayList<String> usernames = new ArrayList<String>();
        final ArrayList<String> testeUser = new ArrayList<>();
//        usernames.add("JogadorUm");
        final ArrayAdapter arrayAdapter = new ArrayAdapter( this, android.R.layout.simple_expandable_list_item_1, usernames );
        txt_to_aprove.setAdapter( arrayAdapter );

        final Date[] data_jogo = {null};

        ParseQuery<ParseObject> teste = new ParseQuery<ParseObject>( "Jogo" );
        teste.whereEqualTo( "JogadorUm", ParseUser.getCurrentUser().getUsername() );
        teste.addAscendingOrder( "JogadorDois" );
        teste.findInBackground( new FindCallback<ParseObject>() {
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
                                data_jogo[0] = hourFormat.parse( teste_data );
                                Date data = new Date( data_jogo[0].getTime() );


                                Calendar calander_ = Calendar.getInstance();
                                SimpleDateFormat simpledateformat = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
                                final String Date = simpledateformat.format( calander_.getTime() );
                                Log.i( "data", Date );

//                                                       Log.i( "data - 30", data.toString() );
//                                                       Log.i( "data jogo", data_jogo.toString() );

                                if ((data_jogo[0].after( calander_.getTime() ))) {

//                                                           Log.i( "data - 30", data.toString() );
//                                                           Log.i( "data jogo", data_jogo.toString() );

                                    String resposta = object.getString( "Response" );

                                    if (object.getString( "Response" ) == "" || object.getString( "Response" ) == null) {
                                        usernames.add( object.getString( "JogadorUm" ) + "/" + object.getString( "Nome_local" ) + "/" + object.getString( "Data" ) + " " + object.getString( "Hora" ) + "    " + "Aguardando resposta" );
                                        o = object.getObjectId();
                                    } else {
                                        usernames.add( object.getString( "JogadorUm" ) + "/" + object.getString( "Nome_local" ) + "/" + object.getString( "Data" ) + " " + object.getString( "Hora" ) + "    " + object.getString( "Response" ) );
                                        o = object.getObjectId();
                                    }
                                    txt_to_aprove.setAdapter( arrayAdapter );
                                }
                            } catch (java.text.ParseException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                }
            }
        } );

        txt_to_aprove.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapters, View childView, final int position, long arg3) {
                // TODO Auto-generated method stub
                String teste__ = adapters.getItemAtPosition( position ).toString();
                Log.i("data list", teste__);
                String[] testeSplit = teste__.split( "/" );
                String[] testeSplit2 = testeSplit[2].split( " " );

                final String dialog = testeSplit[0] + "\n" + testeSplit[1] + "\n" + testeSplit2[0] + "\n" + testeSplit2[1];

                Date data_jogo = null;
                Date agora_data = new Date();
                Date data;
                SimpleDateFormat hourFormat = new SimpleDateFormat( "dd-MM-yyy HH:mm" );
                try {
                    data_jogo = hourFormat.parse( testeSplit[2] );
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }

                data = new Date( data_jogo.getTime() - 21600000 );


                Log.i( "data jogo", data_jogo.toString() );
                Log.i( "data -12", data.toString() );
                Log.i( "data atual", agora_data.toString() );

                agora_data.getTime();
                if (agora_data.before( data )) {
                    exemplo_simples( position, dialog );
                } else {
                    exemplo2( position, dialog );
                }

            }
        } );
    }

//                            if (){
//                                exemplo_simples(dialog );
//                            }
//                                                  }
//                                              });}

            String response;

            //
            private void exemplo_simples(int arg1, String teste__ ) {
                //Cria o gerador do AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //define o titulo
                builder.setTitle("Requisição");
                //define a mensagem
                builder.setMessage(teste__);
                //define um botão como positivo
                //define um botão como negativo.
                builder.setPositiveButton("Voltar ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(Requested.this, "Negado", Toast.LENGTH_SHORT).show();
                        response = "Recusado";
                        final ParseQuery<ParseObject> object = new ParseQuery("Jogo");
                        object.whereEqualTo( "objectId", o );
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

    private void exemplo2(int arg1, String teste__ ) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Requisição");
        //define a mensagem
        builder.setMessage(teste__);
        //define um botão como positivo
        //define um botão como negativo.

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(Requested.this, MainActivity.class);
        startActivity(intent);
    }

}
