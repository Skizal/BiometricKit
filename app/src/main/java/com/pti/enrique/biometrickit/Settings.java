package com.pti.enrique.biometrickit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
    private EditText name, lastName, email, passwordNew;
    private String pass;

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
        passwordNew = (EditText) findViewById( R.id.editPassword1);
        apply = (Button) findViewById(R.id.buttonApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyUser();
            }
        });
        setToolbar();

        NetworkManager nm = NetworkManager.getInstance( this );
        nm.getUser( this );
    }

    public void setEdits(String sName, String sLastName, String sEmail, String sPassword ){
        name.setText( sName );
        lastName.setText( sLastName );
        email.setText( sEmail );
        passwordNew.setText( sPassword );
        pass = sPassword;
    }

    private void modifyUser(){
        if( passwordNew.getText().toString().equals( pass ) ) {
            mod();
        }
        else {
            AlertDialog diaBox = AskOption(  );
            diaBox.show();
        }
    }

    private void mod(){
        NetworkManager nm = NetworkManager.getInstance( this );
        nm.updateUser(this, passwordNew.getText().toString(), name.getText().toString(), lastName.getText().toString(), email.getText().toString());
    }

    private AlertDialog AskOption( ) {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder( this )
                .setTitle("Modifying password!")
                .setMessage("Are you sure?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mod();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        passwordNew.setText( pass );
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
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
