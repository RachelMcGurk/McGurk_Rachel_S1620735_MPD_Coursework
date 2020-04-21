/* RACHEL MCGURK S1620735*/

package org.me.gcu.mpd_coursework;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class SpecificSearchClass extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchBar;
    CalendarView calendar;
    private TextView rawDataDisplayCI;
    private TextView rawDataDisplayRW;
    private TextView rawDataDisplayPW;

    private TextView headingCI;
    private TextView headingRW;
    private TextView headingPW;

    LinkedList<CurrentIncidentsClass> cList;
    LinkedList<PlannedWorksClass> pList;
    LinkedList<RoadWorksClass> rList;

    private View activityView;

    int queryDate;
    int queryMonth;

    int startMonth;
    int endMonth;

    int startDate;
    int endDate;

    String startDateString = "";
    String endDateString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activityView = (View)findViewById(R.id.activity_search);

        rawDataDisplayCI = (TextView)findViewById(R.id.rawDataDisplayCI);
        rawDataDisplayCI.setTypeface(Typeface.SANS_SERIF);
        rawDataDisplayRW = (TextView)findViewById(R.id.rawDataDisplayRW);
        rawDataDisplayPW = (TextView)findViewById(R.id.rawDataDisplayPW);

        headingCI = (TextView)findViewById(R.id.CIheading);
        headingCI.setTypeface(Typeface.DEFAULT_BOLD);
        headingRW = (TextView)findViewById(R.id.RWheading);
        headingRW.setTypeface(Typeface.DEFAULT_BOLD);
        headingPW = (TextView)findViewById(R.id.PWheading);
        headingPW.setTypeface(Typeface.DEFAULT_BOLD);

        searchBar = (SearchView) findViewById(R.id.searchBar);

        searchBar.setQueryHint("e.g. M8");
        searchBar.setOnQueryTextListener(this);

        cList = DataParsingClass.ciList;
        pList = DataParsingClass.pwList;
        rList = DataParsingClass.rwList;

        activityView.setBackgroundColor(getResources().getColor(R.color.RoyalBlue,null));

        calendar = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        calendar.setDate(1587300371306L); // set selected date 22 May 2016 in milliseconds

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                queryDate = dayOfMonth;
                queryMonth = month;
                CheckDate();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        String searchQuery = "";
        String tempCI = "";
        String tempRW = "";
        String tempPW = "";

        searchQuery = searchBar.getQuery().toString();

        for (Integer i = 0; i < cList.size(); i++)
        {
            if (cList.get(i).getTitle().contains(searchQuery))
            {
                tempCI += cList.get(i).getTitle() + "\n" + cList.get(i).getDescription() + "\n"  + "\n";
            }
        }
        for (Integer i = 0; i < pList.size(); i++)
        {
            if (pList.get(i).getTitle().contains(searchQuery))
            {
                tempPW += pList.get(i).getTitle() + "\n" + pList.get(i).getDescription() + "\n"  + "\n";
            }
        }
        for (Integer i = 0; i < rList.size(); i++)
        {
            if (rList.get(i).getTitle().contains(searchQuery))
            {
                tempRW += rList.get(i).getTitle() + "\n" + rList.get(i).getDescription() + "\n"  + "\n";
            }
        }

        //Log.d("MyTag", "search " + searchQuery);

        if (tempCI == "")
        {
            tempCI += "No current incidents found on this route";
        }
        if (tempRW == "")
        {
            tempRW += "No current roadworks found on this route";
        }
        if (tempPW == "")
        {
            tempPW += "No planned roadworks found on this route";
        }

        rawDataDisplayCI.setText(tempCI);
        rawDataDisplayRW.setText(tempRW);
        rawDataDisplayPW.setText(tempPW);

        return false;
    }

    void CheckDate()
    {
        String temp = "";
        queryMonth++;
        //Log.d("MyTag", "querymonth " + queryMonth);

        for (Integer i = 0; i < pList.size(); i++)
        {
            String date = pList.get(i).getDescription();
            startDateString = date.substring(date.indexOf(", "), date.indexOf(" - "));
            endDateString = date.substring(date.indexOf("End"));
            ConvertStartMonth();
            ConvertEndMonth();

            String tempStartString = startDateString.substring(startDateString.indexOf(" ") + 1, 4);
            String tempEndString = endDateString.substring(endDateString.indexOf("day, ") + 5, endDateString.indexOf("day, ") + 7);

            startDate = Integer.parseInt(tempStartString);
            endDate = Integer.parseInt(tempEndString);

            Log.d("MyTag", "querymonth " + queryMonth);
            Log.d("MyTag", "startmonth " + startMonth);

            //Log.d("MyTag", "enddate " + endDate);
            //String dateConvert = Character.toString((char) (date.charAt(0) + date.charAt(1) + date.charAt(2)));
            //Log.d("MyTag", "startmonth " + startMonth);

            if (queryMonth >= startMonth)
            {
                if (queryMonth <= endMonth)
                {
                    if (queryDate >= startDate)
                    {
                        if (queryDate <= endDate)
                        {
                            temp += pList.get(i).getTitle() + "\n" + pList.get(i).getDescription() + "\n" + "\n";
                        }
                    }
                }
            }
            //Log.d("MyTag", "date " + endDateString);
        }

        if (temp == "")
        {
            temp+= "No planned roadworks found on this date";
        }

        String tempCI = "N/A";
        String tempRW = "N/A";

        rawDataDisplayPW.setText(temp);
        rawDataDisplayCI.setText(tempCI);
        rawDataDisplayRW.setText(tempRW);
    }

    void ConvertStartMonth()
    {
        if (startDateString.contains("January"))
        {
            startMonth = 1;
        }
        else if (startDateString.contains("February"))
        {
            startMonth = 2;
        }
        else if (startDateString.contains("March"))
        {
            startMonth = 3;
        }
        else if (startDateString.contains("April"))
        {
            startMonth = 4;
        }
        else if (startDateString.contains("May"))
        {
            startMonth = 5;
        }
        else if (startDateString.contains("June"))
        {
            startMonth = 6;
        }
        else if (startDateString.contains("July"))
        {
            startMonth = 7;
        }
        else if (startDateString.contains("August"))
        {
            startMonth = 8;
        }
        else if (startDateString.contains("September"))
        {
            startMonth = 9;
        }
        else if (startDateString.contains("October"))
        {
            startMonth = 10;
        }
        else if (startDateString.contains("November"))
        {
            startMonth = 11;
        }
        else if (startDateString.contains("December"))
        {
            startMonth = 12;
        }
    }

    void ConvertEndMonth()
    {
        if (endDateString.contains("January"))
        {
            endMonth = 1;
        }
        else if (endDateString.contains("February"))
        {
            endMonth = 2;
        }
        else if (endDateString.contains("March"))
        {
            endMonth = 3;
        }
        else if (endDateString.contains("April"))
        {
            endMonth = 4;
        }
        else if (endDateString.contains("May"))
        {
            endMonth = 5;
        }
        else if (endDateString.contains("June"))
        {
            endMonth = 6;
        }
        else if (endDateString.contains("July"))
        {
            endMonth = 7;
        }
        else if (endDateString.contains("August"))
        {
            endMonth = 8;
        }
        else if (endDateString.contains("September"))
        {
            endMonth = 9;
        }
        else if (endDateString.contains("October"))
        {
            endMonth = 10;
        }
        else if (endDateString.contains("November"))
        {
            endMonth = 11;
        }
        else if (endDateString.contains("December"))
        {
            endMonth = 12;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
