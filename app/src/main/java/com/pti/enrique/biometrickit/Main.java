package com.pti.enrique.biometrickit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;


public class Main extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<String> data;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iOnCreate();
    }

    private void iOnCreate(){
        NetworkManager nm = NetworkManager.getInstance( this );
        setToolbar();
        setRecyclerView();

        ArrayList<String> data = new ArrayList<>();
        data.add( "Juan ");
        data.add( "Marta");
        data.add( "Herminia");
        updateRecyclerView( );
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
                Toast.makeText(Main.this, "Information Updated", Toast.LENGTH_LONG ).show();
                NetworkManager nm = NetworkManager.getInstance();
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






















































