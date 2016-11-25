package com.pti.enrique.biometrickit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText userName;
    private EditText password;
    private String sName, sLastName, sEmail, sUserName, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        myToolbar.setTitle( "Join BiometricKit");
        setSupportActionBar(myToolbar);


        name = (EditText)findViewById( R.id.name );
        lastName = ( EditText )findViewById( R.id.lastName);
        email = ( EditText )findViewById( R.id.eMail);
        userName = ( EditText )findViewById( R.id.userName );
        password = ( EditText )findViewById( R.id.password );

        Button create = ( Button ) findViewById( R.id.button );
        create.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick( View v ){
                        String sName, sLastName, sEmail, sUserName, sPassword;
                        sName = name.getText().toString();
                        sLastName = lastName.getText().toString();
                        sEmail = email.getText().toString();
                        sUserName = userName.getText().toString();
                        sPassword = password.getText().toString();
                        if( sName.isEmpty() || sLastName.isEmpty() || sEmail.isEmpty() || sUserName.isEmpty() || sPassword.isEmpty() ){
                            Toast.makeText( SignUp.this, "Some fields are missing", Toast.LENGTH_SHORT ).show();
                        }
                        else{
                            NetworkManager.getInstance(SignUp.this).createUser( SignUp.this, sUserName, sPassword, sName, sLastName, sEmail );
                        }
                    }
        });

    }


    public void finishOK(){
        Intent log = new Intent( );
        log.putExtra( "Sign", "Registered correctly" );
        setResult( 10, log );
        super.finish();
    }

    @Override
    public void onBackPressed(){
        Intent log = new Intent( );
        setResult( 10, log );
        super.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
