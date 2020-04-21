/* RACHEL MCGURK S1620735*/

package org.me.gcu.mpd_coursework;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class PlannedRouteClass extends AppCompatActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener {

    private GoogleMap mMap;

    SearchView searchBar;

    LinkedList<CurrentIncidentsClass> cList;
    LinkedList<PlannedWorksClass> pList;
    LinkedList<RoadWorksClass> rList;

    private View activityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_route);

        activityView = (View)findViewById(R.id.activity_planned_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setQueryHint("e.g. M8");
        searchBar.setOnQueryTextListener(this);

        cList = DataParsingClass.ciList;
        pList = DataParsingClass.pwList;
        rList = DataParsingClass.rwList;

        String location = "";

        activityView.setBackgroundColor(getResources().getColor(R.color.RoyalBlue,null));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        String searchQuery = "";
        String temp = "";

        searchQuery = searchBar.getQuery().toString();

        for (Integer i = 0; i < cList.size(); i++)
        {
            if (cList.get(i).getTitle().contains(searchQuery))
            {
                temp += cList.get(i).getTitle() + "\n" + cList.get(i).getDescription() + "\n" + cList.get(i).getGeoLocation() + "\n";

                String [] latLong = cList.get(i).getGeoLocation().split(" ");

                double latitude = Double.parseDouble(latLong[0]);
                double longitude = Double.parseDouble(latLong[1]);

                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title(cList.get(i).getDescription() + "\n"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
            }
        }
        for (Integer i = 0; i < rList.size(); i++)
        {
            if (rList.get(i).getTitle().contains(searchQuery))
            {
                temp += rList.get(i).getTitle() + "\n" + rList.get(i).getDescription() + "\n" + rList.get(i).getGeoLocation() + "\n";

                String [] latLong = rList.get(i).getGeoLocation().split(" ");

                double latitude = Double.parseDouble(latLong[0]);
                double longitude = Double.parseDouble(latLong[1]);

                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title(rList.get(i).getTitle() + "\n"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
            }
        }

        Log.d("MyTag", "search " + temp);

        if (temp == "")
        {
            temp += "No incidents found on this route";
        }

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng glasgow = new LatLng(55.8642, -4.251);
        //mMap.addMarker(new MarkerOptions().position(glasgow).title("Marker in Glasgow"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 10));
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
