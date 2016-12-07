package com.pti.enrique.biometrickit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;



public class Main extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> data;
    private EditText textDev;
    private Button addDev;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iOnCreate();
    }

    private void iOnCreate(){
        setToolbar();
        setRecyclerView();

        textDev = (EditText) findViewById(R.id.deviceText);
        addDev = (Button) findViewById(R.id.addButton);
        addDev.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addDevice();
                    }
                }
        );

        data = new ArrayList<>();
        /*
        data.add( "Juan ");
        data.add( "Marta");
        data.add( "Herminia");
        updateRecyclerView( );
        */
        NetworkManager nm = NetworkManager.getInstance( this );
        nm.getDevices( this );
    }

    private void addDevice(){
        NetworkManager nm = NetworkManager.getInstance(this);
        nm.addDevice( this, textDev.getText().toString() );
    }

    private void setRecyclerView(){
        mRecyclerView = ( RecyclerView ) findViewById( R.id.my_recycler_view );
        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );
    }

    public void updateDevices( String dev ){
        data.add( dev );
    }

    public void resetDevices(){
        data.clear();
    }

    public void deleteDevice( String id ){
        data.remove( id );
    }

    public void updateRecyclerView(){
        mAdapter = new MyAdapter( data, this );
        mRecyclerView.setAdapter( mAdapter );
    }

    private void setToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        myToolbar.setTitle("Devices");
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent( "com.pti.enrique.biometrickit.Settings" );
                startActivity( intent );
                return true;

            case R.id.action_refresh:
                NetworkManager nm = NetworkManager.getInstance( this );
                nm.getDevices( this );
                return true;

            case R.id.action_logout:
                super.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public void onBackPressed(){
        moveTaskToBack( true );
    }

}






















































