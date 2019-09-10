package com.example.teste_fr_2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class CadastrarUsuario extends AppCompatActivity {
    //  ### montar page objects
    public EditText nome, apelido, email, senha ;

//  ###

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.cadastrar );

        nome = findViewById( R.id.formulario_nome );
        apelido = findViewById( R.id.formulario_apelido );
        email = findViewById( R.id.formulario_email );
        senha = findViewById( R.id.formulario_senha );


        Button cadastro = findViewById( R.id.formulario_cadastrar );
        cadastro.setOnClickListener( new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (apelido.getText().toString().contains( " " ) || nome.getText().toString().equals( "" ) || nome.getText().toString() == null || apelido.getText().toString().equals( "" ) || apelido.getText().toString() == null || email.getText().toString().equals( "" ) || email.getText().toString() == null || (!email.getText().toString().contains( "@" ) && email.getText().toString().contains( ".com" )) || senha.getText().toString().length() <=3 ) {
                    Log.i("errado", "nome vazio");
                    Toast.makeText( CadastrarUsuario.this, "Preencha novamente, alguma informação esta errada :( " + apelido.getText().toString(), Toast.LENGTH_SHORT ).show();
                }else{
                    ParseObject object = new ParseObject( "Cadastro_FR" );
                    object.put( "Nome", nome.getText().toString() );
                    object.put( "Apelido", apelido.getText().toString() );
                    object.put( "Email", email.getText().toString() );
                    object.put( "Pontos", "10");
                    object.saveInBackground( new SaveCallback() {
                        @Override
                        public void done(ParseException ex) {
                            if (ex == null) {
                                Log.i( "Parse Result", "Successful!" );
                            } else {
                                Log.i( "Parse Result", "Failed" + ex.toString() );
                            }
                        }
                    } );
                    ParseUser user = new ParseUser();
                    user.setUsername( apelido.getText().toString() );
                    user.setPassword( senha.getText().toString() );
                    user.setEmail( email.getText().toString() );
                    user.signUpInBackground( new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i( "sign up ok", "Deu certo, entrou" );
                            } else {
                                e.printStackTrace();
                            }
                        }
                    } );

                    Toast.makeText( CadastrarUsuario.this, "Cadastro realizado com sucesso! Obrigado  " + apelido.getText().toString(), Toast.LENGTH_SHORT ).show();
                    finish();
                }
            }

        } );
    }
}
