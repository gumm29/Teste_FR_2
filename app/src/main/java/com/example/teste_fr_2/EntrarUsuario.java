package com.example.teste_fr_2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class EntrarUsuario extends AppCompatActivity {

    public EditText apelido, senha;
    private final String CHANNEL_ID = "notificação pessoal";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrar);

        ParseUser.logOut();

        apelido = findViewById(R.id.entrar_apelido);
        senha = findViewById(R.id.entrar_senha);

        Button entrar = findViewById(R.id.btn_entrar);
        entrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ParseUser.logInInBackground(apelido.getText().toString(), senha.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null){
                            Log.i("Ok", "certo");
                            Intent intentEntrar = new Intent(EntrarUsuario.this, MainActivity.class);
                            startActivity(intentEntrar);
                        }else{
                            e.printStackTrace();
                            Toast.makeText(EntrarUsuario.this, "Apelido o senha erradas! :(" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(EntrarUsuario.this, Inicio.class);
        startActivity(intent);
    }

    public void sendEmail(View view) {

        ParseUser.requestPasswordResetInBackground("gustavomm29@hotmail.com", new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("teste email", "ok");
                    // An email was successfully sent with reset instructions.
                } else {
                    Log.i("teste email", "erro "+e.toString());

                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });

    }

    public void sendNotification(View view) {

        //Get an instance of NotificationManager//
        createNotificationChannel();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_menu_send)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // Gets an instance of the NotificationManager service//

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from( this );
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name =  "Notificação Pessoal";
            String description = "Inclui todas as notificações atuais";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
            notificationManager.createNotificationChannel( notificationChannel );
        }
    }
}
