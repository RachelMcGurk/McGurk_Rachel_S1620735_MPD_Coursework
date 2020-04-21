/* RACHEL MCGURK S1620735*/
package org.me.gcu.mpd_coursework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AllIncidentsClass extends AppCompatActivity implements View.OnClickListener {

    private TextView rawDataDisplay;
    private Button currentIncidentsButton;
    private Button roadWorksButton;
    private Button plannedWorksButton;

    private String noCI = "No current incidents";
    private String noRW = "No current roadworks";
    private String noPW = "No planned roadworks";

    private View activityView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents_main);

        activityView = (View)findViewById(R.id.activity_incidents_main);

        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        currentIncidentsButton = (Button)findViewById(R.id.currentIncidentsButton);
        currentIncidentsButton.setOnClickListener(this);

        roadWorksButton = (Button)findViewById(R.id.roadWorksButton);
        roadWorksButton.setOnClickListener(this);

        plannedWorksButton = (Button)findViewById(R.id.plannedWorksButton);
        plannedWorksButton.setOnClickListener(this);

        activityView.setBackgroundColor(getResources().getColor(R.color.RoyalBlue,null));

    }

    @Override
    public void onClick(View v)
    {
        DataParsingClass dpc = new DataParsingClass();

        if(v == currentIncidentsButton)
        {
            dpc.dataType = "ci";
            dpc.DataSetup();

            if (DataParsingClass.currentIncidentDisplay == "")
            {
                rawDataDisplay.setText(noCI);
            }
            else
            {
                rawDataDisplay.setText(DataParsingClass.currentIncidentDisplay);
            }
        }
        else if(v == roadWorksButton)
        {
            dpc.dataType = "rw";
            dpc.DataSetup();

            if (DataParsingClass.roadWorksDisplay == "")
            {
                rawDataDisplay.setText(noRW);
            }
            else
            {
                rawDataDisplay.setText(DataParsingClass.roadWorksDisplay);
            }
        }
        else if(v == plannedWorksButton)
        {
            dpc.dataType = "pw";
            dpc.DataSetup();

            if (DataParsingClass.plannedWorksDisplay == "")
            {
                rawDataDisplay.setText(noPW);
            }
            rawDataDisplay.setText(DataParsingClass.plannedWorksDisplay);
        }
    }
}
