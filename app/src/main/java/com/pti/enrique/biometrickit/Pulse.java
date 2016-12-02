package com.pti.enrique.biometrickit;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

public class Pulse extends AppCompatActivity {
    private final Handler mHandler = new Handler();
    private Runnable mTimer2;
    private LineGraphSeries<DataPoint> seriesMonth;
    private LineGraphSeries<DataPoint> seriesDay;
    private GraphView graph;
    private Button butt;
    private double graph2LastXValue = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        myToolbar.setTitle("Heart Rate");
        setSupportActionBar(myToolbar);

        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(200);

        butt = (Button) findViewById(R.id.Switch);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( butt.getText().equals("Month") ){
                    setMonthGraph();
                    butt.setText( "Day" );
                }
                else{
                    setDayGraph();
                    butt.setText( "Month" );
                }
            }
        });


        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Live");
        spec.setContent(R.id.RealTime);
        spec.setIndicator("Live");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Historical");
        spec.setContent(R.id.Historical);
        spec.setIndicator("Historical");
        host.addTab(spec);

        seriesMonth = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 83),
                new DataPoint(1, 90),
                new DataPoint(2, 86),
                new DataPoint(3, 88),
                new DataPoint(4, 76),
                new DataPoint(5, 70),
                new DataPoint(6, 72),
                new DataPoint(7, 83),
                new DataPoint(8, 83),
                new DataPoint(9, 90),
                new DataPoint(10, 86),
                new DataPoint(11, 88),
                new DataPoint(12, 76),
                new DataPoint(13, 70),
                new DataPoint(14, 72),
                new DataPoint(15, 83),
        });
        seriesMonth.appendData(new DataPoint(16, 120), true, 16 );

        seriesMonth.setColor( Color.RED );

        seriesDay = new LineGraphSeries<>();

        setMonthGraph();
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

    private void setMonthGraph(){
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(30);

        graph.addSeries( seriesMonth );
    }

    private void setDayGraph(){
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);

        graph.addSeries( seriesDay );
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

    double mLastRandom = 2;
    Random mRand = new Random();

    private double getRandom() {
        return mLastRandom += mRand.nextDouble() * 0.5 - 0.25;
    }
}

