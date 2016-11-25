package com.pti.enrique.biometrickit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Validate extends AppCompatActivity {
    private EditText eCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        eCode = ( EditText ) findViewById(R.id.code );
        Button bValidate = ( Button ) findViewById( R.id.vButton );
        bValidate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick( View v ){
                        valida();
                    }
                }
        );
    }

    private void valida(){
        String sCode =  eCode.getText().toString();
        NetworkManager.getInstance().validate(this, sCode);
    }

    public void toMain(){
        Intent i = new Intent( "com.pti.enrique.biometrickit.Main" );
        startActivity( i );
    }
}
