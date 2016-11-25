package com.pti.enrique.biometrickit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //depend on bundle set one layout or another
        setContentView(R.layout.activity_settings);
        iOnCreate();
    }

    private void iOnCreate(){
        setToolbar();
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
