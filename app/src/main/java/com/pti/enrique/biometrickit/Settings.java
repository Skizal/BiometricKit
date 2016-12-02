package com.pti.enrique.biometrickit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    private Button apply;
    private EditText name, lastName, email, password, passwordNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //depend on bundle set one layout or another
        setContentView(R.layout.activity_settings);
        iOnCreate();
    }

    private void iOnCreate(){
        name = (EditText) findViewById( R.id.editName);
        lastName = (EditText) findViewById( R.id.editLastName);
        email = (EditText) findViewById( R.id.editEmail);
        password = (EditText) findViewById( R.id.editPassword);
        passwordNew = (EditText) findViewById( R.id.editPassword1);

        apply = (Button) findViewById(R.id.buttonApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyUser();
            }
        });
        setToolbar();
    }

    private void modifyUser(){
        NetworkManager nm = NetworkManager.getInstance( this );
        //nm.modifySettings( name, lastName, email, password, passwordNew);
    }

    private void setToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        myToolbar.setTitle("Settings");
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
