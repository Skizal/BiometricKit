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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private  EditText user;
    private  EditText password;
    private  Button loginButton;
    private  Button signButton;
    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);


        loginButton();
        signButton = ( Button ) findViewById(R.id.bSignup );
        signButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick( View v ){
                        Intent intent = new Intent( "com.pti.enrique.biometrickit.SignUp" );
                        startActivityForResult( intent, 10 );
                    }
                }
        );
    }

    private void loginButton() {
        user = ( EditText )findViewById( R.id.userName );
        password = ( EditText )findViewById( R.id.userPass );
        loginButton = ( Button )findViewById( R.id.logButton );
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick( View v ){

                        token = user.getText().toString();
                        //login();
                        if( token.equals( "admin" ) &&  password.getText().toString().equals( "admin" ) ){
                            Toast.makeText( Login.this, "Username and password are correct", Toast.LENGTH_SHORT ).show();
                            Intent intent = new Intent( "com.pti.enrique.biometrickit.Main" );
                            startActivity( intent );
                        }
                        else {
                            Toast.makeText( Login.this, "Username and password are NOT correct", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10 && requestCode == 10 ) {
            if (data.hasExtra("Sign")) {
                String result = data.getExtras().getString( "Sign" );
                if (result != null && result.length() > 0 ) {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void toValidate(){
        Intent intent = new Intent( "com.pti.enrique.biometrickit.Validate" );
        startActivity( intent );
    }

    public void toMain(){
        Intent intent = new Intent( "com.pti.enrique.biometrickit.Main" );
        startActivity( intent );
    }

    public void login(){
        NetworkManager nm = NetworkManager.getInstance( this );
        nm.setUserJSON( user.getText().toString(), password.getText().toString() );
        nm.login( this );
    }

    @Override
    public void onResume(){
        user.setText("");
        password.setText("");
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
