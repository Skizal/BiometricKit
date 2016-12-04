package com.pti.enrique.biometrickit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Pulse extends AppCompatActivity {
    private final Handler handlerReal = new Handler();
    private Runnable runReal;
    private LineGraphSeries<DataPoint> seriesMonth;
    private LineGraphSeries<DataPoint> seriesDay;
    private LineGraphSeries<DataPoint> seriesReal;
    private GraphView graph;
    private GraphView graphH;
    private NetworkManager nm;
    private Button bReal;
    private Button bDay;
    private Button bMonth;
    private Spinner sDay;
    private Spinner sMonth;
    private Spinner sYear;
    private String id;
    private int realIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse);

        Intent intent = getIntent();
        id = intent.getStringExtra( "id" );

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        myToolbar.setTitle("Heart Rate");
        setSupportActionBar(myToolbar);
        setSpinners();
        setButtons();

        realIndex = 0;

        nm = NetworkManager.getInstance( this );

        seriesMonth = new LineGraphSeries<>();

        seriesDay = new LineGraphSeries<>();

        seriesReal = new LineGraphSeries<>();

        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(140);
        setRealGraph();

        graphH = (GraphView) findViewById(R.id.graph2);
        graphH.getViewport().setYAxisBoundsManual(true);
        graphH.getViewport().setMinY(0);
        graphH.getViewport().setMaxY(140);
        setMonthGraph();


        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Live");
        spec.setContent(R.id.realTime);
        spec.setIndicator("Live");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Historical");
        spec.setContent(R.id.historical);
        spec.setIndicator("Historical");
        host.addTab(spec);

    }

    private void setSpinners(){
        Integer[] days = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        Integer[] months = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        Integer[] years = new Integer[]{2016, 2017};

        sDay = (Spinner) findViewById(R.id.spinDay);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, days);
        sDay.setAdapter(adapterDay);

        sMonth = (Spinner) findViewById(R.id.spinMonth);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, months);
        sMonth.setAdapter(adapterMonth);

        sYear = (Spinner) findViewById(R.id.spinYear);
        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, years);
        sYear.setAdapter(adapterYear);
    }

    private void setButtons(){
        bDay = (Button) findViewById(R.id.buttonDay);
        bDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day, month, year;
                day = sDay.getSelectedItem().toString();
                month = sMonth.getSelectedItem().toString();
                year = sYear.getSelectedItem().toString();
                nm.getDay(getBaseContext(), day, month, year, id);
            }
        });
        bMonth = (Button) findViewById(R.id.buttonMonth);
        bMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month, year;
                month = sMonth.getSelectedItem().toString();
                year = sYear.getSelectedItem().toString();
                nm.getMonth(getBaseContext(), month, year, id);
            }
        });
        bReal = (Button) findViewById(R.id.getReal);
        bReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( bReal.getText().toString().contains( "Start" ) ) {
                    getReal();
                    bReal.setText( "Stop Live");
                }
                else {
                    handlerReal.removeCallbacks(runReal);
                    bReal.setText( "Start Live");
                    realIndex = 0;
                    seriesReal = new LineGraphSeries<DataPoint>();
                    graph.removeAllSeries();
                    graph.addSeries( seriesReal );
                }

            }
        });
    }

    private void getReal(){
        runReal = new Runnable() {
            @Override
            public void run() {
                nm.getReal( getBaseContext(), id);
                handlerReal.postDelayed(this, 200);
                /*
                seriesReal.appendData( new DataPoint( (double)realIndex , 100 ), true, 100 );
                ++realIndex;
                */
            }
        };
        handlerReal.postDelayed(runReal, 200);
    }

    public void updateReal( ArrayList<Double> data ){
        for( int i = 0; i < data.size(); ++i ){
            seriesReal.appendData( new DataPoint( (double)realIndex , data.get( i ) ), true, 100 );
            ++realIndex;
        }
    }

    public void updateSeriesDay( ArrayList<Double> data ){
        seriesDay = new LineGraphSeries<>();
        for( int i = 0; i < 24; ++i ){
            seriesDay.appendData( new DataPoint( (double)i , data.get( i ) ), true, 24 );
        }
        setDayGraph();
    }

    public void updateSeriesMonth( ArrayList<Double> data ){
        seriesDay = new LineGraphSeries<>();
        for( int i = 0; i < 30; ++i ){
            seriesDay.appendData( new DataPoint( (double)i , data.get( i ) ), true, 30 );
        }
        setMonthGraph();
    }

    private void setRealGraph(){
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(100);

        graph.addSeries( seriesReal );
    }

    private void setMonthGraph(){
        graphH.getViewport().setXAxisBoundsManual(true);
        graphH.getViewport().setMinX(0);
        graphH.getViewport().setMaxX(30);

        graphH.removeAllSeries();
        graphH.addSeries( seriesMonth );
    }

    private void setDayGraph(){
        graphH.getViewport().setXAxisBoundsManual(true);
        graphH.getViewport().setMinX(0);
        graphH.getViewport().setMaxX(24);

        graphH.removeAllSeries();
        graphH.addSeries( seriesDay );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_pulse, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent("com.pti.enrique.biometrickit.Settings");
                startActivity(intent);
                return true;

            case R.id.action_export:
                //Json export here
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

