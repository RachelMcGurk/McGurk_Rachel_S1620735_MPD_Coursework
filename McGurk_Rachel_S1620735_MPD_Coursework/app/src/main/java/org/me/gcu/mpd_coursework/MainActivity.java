/* RACHEL MCGURK S1620735*/

package org.me.gcu.mpd_coursework;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.fonts.Font;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView rawDataDisplay;
    private Button allIncidentsButton;
    private Button searchRoadButton;
    private Button planRouteButton;

    Intent sceneManager;

    DataParsingClass dpc = new DataParsingClass();

    private View activityView;

    boolean internetConnection;


    //LinkedList<CurrentIncidentsClass> cList;
    //LinkedList<PlannedWorksClass> pList;
    //LinkedList<RoadWorksClass> rList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityView = (View)findViewById(R.id.activity_main);

        dpc.DataSetup();

        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        //rawDataDisplay.setFont

        allIncidentsButton = (Button)findViewById(R.id.allIncidentsButton);
        allIncidentsButton.setOnClickListener(this);

        searchRoadButton = (Button)findViewById(R.id.searchRoadButton);
        searchRoadButton.setOnClickListener(this);

        planRouteButton = (Button)findViewById(R.id.planRouteButton);
        planRouteButton.setOnClickListener(this);

        activityView.setBackgroundColor(getResources().getColor(R.color.RoyalBlue,null));

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            //we are connected to a network
            internetConnection = true;
        }
        else {
            internetConnection = false;
        }

        Log.d("MyTag", "internet" + internetConnection);

        //cList = DataParsingClass.ciList;
        //pList = DataParsingClass.pwList;
        //rList = DataParsingClass.rwList;
    }

    public void onClick(View v)
    {
        if (v == allIncidentsButton)
        {
            if (internetConnection)
            {
                sceneManager = new Intent(this, AllIncidentsClass.class);
                startActivity(sceneManager);
            }
            else
            {
                showtbDialog();
            }
        }

        if (v == searchRoadButton)
        {
            if (internetConnection) {
                sceneManager = new Intent(this, SpecificSearchClass.class);
                startActivity(sceneManager);
            }
            else
            {
                showtbDialog();
            }
        }
        if (v == planRouteButton)
        {
            if (internetConnection) {
                sceneManager = new Intent(this, PlannedRouteClass.class);
                startActivity(sceneManager);
            }
            else
            {
                showtbDialog();
            }
        }
    }

    private void showtbDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cannot connect to the internet");
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }
} // End of MainActivity
