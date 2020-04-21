/* RACHEL MCGURK S1620735*/

package org.me.gcu.mpd_coursework;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class DataParsingClass extends AppCompatActivity {

    private String resultMain;
    private String resultTask;

    public static String currentIncidentDisplay = "";
    public static String plannedWorksDisplay = "";
    public static String roadWorksDisplay = "";

    public static LinkedList<CurrentIncidentsClass> ciList;
    public static LinkedList<PlannedWorksClass> pwList;
    public static LinkedList<RoadWorksClass> rwList;

    private String urlSource;

    public String dataType;

    private String currentIncidentUrlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String plannedWorksUrlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String roadWorksUrlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";

    private int lineCount = 0;

    private String iTypeUrl;

    public void DataSetup()
    {
        if (dataType == "ci")
        {
            urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
        }
        if (dataType == "pw")
        {
            urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
        }
        if (dataType == "rw")
        {
            urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
        }

        /*if (ciList == null)
        {
            startProgress(currentIncidentUrlSource, "ci");
        }
        if (pwList == null)
        {
            startProgress(plannedWorksUrlSource, "pw");
        }
        if (ciList == null)
        {
            startProgress(roadWorksUrlSource, "rw");
        }*/

        startProgress();
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        resultTask = "";
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            //

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {

                    if(dataType == "ci")
                    {
                        if(lineCount > 1)
                        {
                            resultTask = resultTask + inputLine;

//                          Log.e("MyTag",inputLine);
                        }
                        else
                        {
                            lineCount++;
                        }
                    }
                    else if(dataType == "pw")
                    {
                        if(lineCount > 0)
                        {
                            resultTask = resultTask + inputLine;
//                          Log.e("MyTag",inputLine);
                        }
                        else
                        {
                            lineCount++;
                        }
                    }
                    else if(dataType == "rw")
                    {
                        if(lineCount > 0)
                        {
                            resultTask = resultTask + inputLine;
//                          Log.e("MyTag",inputLine);
                        }
                        else
                        {
                            lineCount++;
                        }
                    }


                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            DataParsingClass.this.runOnUiThread(new Runnable()
            {
                public void run() {

                    Log.d("UI thread", "I am the UI thread");

                    if(dataType == "ci")
                    {
                        resultMain = resultTask;

                        ciList = parseCurrentIncidentsData(resultMain);

                        for (Integer i = 0; i < ciList.size(); i++)
                        {
                            currentIncidentDisplay += ciList.get(i).getTitle() + "\n" + ciList.get(i).getDescription() + "\n" + "\n";
                        }
                    }
                    else if(dataType == "pw")
                    {
                        resultMain = resultTask;

                        pwList = parsePlannedWorksData(resultMain);

                        for (Integer i = 0; i < pwList.size(); i++)
                        {
                            plannedWorksDisplay += pwList.get(i).getTitle() + "\n" + pwList.get(i).getDescription() + "\n" + "\n";
                        }
                    }
                    else if(dataType == "rw")
                    {
                        resultMain = resultTask;

                        rwList = parseRoadWorksData(resultMain);

                        for (Integer i = 0; i < rwList.size(); i++)
                        {
                            roadWorksDisplay += rwList.get(i).getTitle() + "\n" + rwList.get(i).getDescription() + "\n" + "\n";
                        }
                    }

                }
            });
        }
    }
    private LinkedList<CurrentIncidentsClass> parseCurrentIncidentsData(String dataToParse)
    {
        CurrentIncidentsClass incidentWidget = null;
        LinkedList <CurrentIncidentsClass> inciList = null;
        boolean bStart = false;
        //inciList  = new LinkedList<IncidentsClass>();

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                switch(eventType)
                {
                    case XmlPullParser.START_TAG:

                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            inciList  = new LinkedList<CurrentIncidentsClass>();
                        }
                        else if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            incidentWidget = new CurrentIncidentsClass();
                            bStart = true;
                        }
                        else if(bStart)
                        {
                            //Log.e("MyTag","TagName:" + xpp.getName());
                            if (xpp.getName().equalsIgnoreCase("title"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","title " + temp);
                                incidentWidget.setTitle(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","description " + temp);
                                incidentWidget.setDescription(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("link"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","link " + temp);
                                incidentWidget.setLink(temp);

                            }
                            else if (xpp.getName().equalsIgnoreCase("georss:point"))
                            //else if (xpp.getName().equalsIgnoreCase("georss:point"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();

                                incidentWidget.setGeoLocationV2(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("pubDate"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","pubDate " + temp);
                                incidentWidget.setPubDateV2(temp);
                            }
                        }

                        break;

                    case XmlPullParser.END_TAG:

                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            //Log.e("MyTag","incidentWidget is " + incidentWidget.toString());
                            inciList.add(incidentWidget);
                        }
                        else
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            int size;
                            size = inciList.size();
                            //Log.e("MyTag","incidentCollection size is " + size);
                        }

                        break;
                }
                // Get the next event
                eventType = xpp.next();
            }
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");

        return inciList;
    }

    private LinkedList<RoadWorksClass> parseRoadWorksData(String dataToParse)
    {
        RoadWorksClass roadWorkWidget = null;
        LinkedList <RoadWorksClass> roadWorksList = null;
        boolean bStart = false;
        //inciList  = new LinkedList<IncidentsClass>();

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                switch(eventType)
                {
                    case XmlPullParser.START_TAG:

                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            roadWorksList  = new LinkedList<RoadWorksClass>();
                        }
                        else if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            //Log.e("MyTag","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            roadWorkWidget = new RoadWorksClass();
                            bStart = true;
                        }
                        else if(bStart)
                        {
                            //Log.e("MyTag","TagName:" + xpp.getName());
                            if (xpp.getName().equalsIgnoreCase("title"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","title " + temp);
                                roadWorkWidget.setTitle(temp);
                                Log.d("MyTag", "yes");
                            }
                            else if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","description " + temp);
                                roadWorkWidget.setDescription(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("link"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","link " + temp);
                                roadWorkWidget.setLink(temp);

                                //eventType = xpp.next();
                                //Log.e("MyTag","ccccccccccccccccccccccccccccccccccc" + xpp.getName());
                            }
                            else if (xpp.getName().equalsIgnoreCase("georss:point"))
                            //else if (xpp.getName().equalsIgnoreCase("georss:point"))
                            {
                                //Log.e("MyTag","fffffffffffffffffffffffffff");
                                //String tp = xpp.getName().replace(":","");
                                //if (tp.equals("georsspoint"))

                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","ccccccccccccccccccccccccccccccccccc" + temp);



                                //String[] arrOfStr = temp.split(" ", 2);

                                roadWorkWidget.setGeoLocationV2(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("pubDate"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","pubDate " + temp);

                                //String[] arrOfStr = temp.split(" ", 2);

                                roadWorkWidget.setPubDateV2(temp);
                            }
                        }




                        break;

                    case XmlPullParser.END_TAG:

                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            //Log.e("MyTag","incidentWidget is " + roadWorkWidget.toString());
                            roadWorksList.add(roadWorkWidget);
                        }
                        else
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            int size;
                            size = roadWorksList.size();
                            //Log.e("MyTag","incidentCollection size is " + size);
                        }

                        break;


                }

                // Get the next event
                eventType = xpp.next();
            }
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");

        return roadWorksList;
    }


    private LinkedList<PlannedWorksClass> parsePlannedWorksData(String dataToParse)
    {
        PlannedWorksClass plannedWorksWidget = null;
        LinkedList <PlannedWorksClass> plannedWorksList = null;
        boolean bStart = false;
        //inciList  = new LinkedList<IncidentsClass>();

        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {

                switch(eventType)
                {
                    case XmlPullParser.START_TAG:

                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            plannedWorksList  = new LinkedList<PlannedWorksClass>();
                        }
                        else if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            //Log.e("MyTag","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            plannedWorksWidget = new PlannedWorksClass();
                            bStart = true;
                        }
                        else if(bStart)
                        {
                            //Log.e("MyTag","TagName:" + xpp.getName());
                            if (xpp.getName().equalsIgnoreCase("title"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","title " + temp);
                                plannedWorksWidget.setTitle(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","description " + temp);
                                plannedWorksWidget.setDescription(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("link"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","link " + temp);
                                plannedWorksWidget.setLink(temp);

                                //eventType = xpp.next();
                                //Log.e("MyTag","ccccccccccccccccccccccccccccccccccc" + xpp.getName());
                            }
                            else if (xpp.getName().equalsIgnoreCase("georss:point"))
                            //else if (xpp.getName().equalsIgnoreCase("georss:point"))
                            {
                                //Log.e("MyTag","fffffffffffffffffffffffffff");
                                //String tp = xpp.getName().replace(":","");
                                //if (tp.equals("georsspoint"))

                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","ccccccccccccccccccccccccccccccccccc" + temp);



                                //String[] arrOfStr = temp.split(" ", 2);

                                plannedWorksWidget.setGeoLocationV2(temp);
                            }
                            else if (xpp.getName().equalsIgnoreCase("pubDate"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                //Log.e("MyTag","pubDate " + temp);

                                //String[] arrOfStr = temp.split(" ", 2);

                                plannedWorksWidget.setPubDateV2(temp);
                            }
                        }




                        break;

                    case XmlPullParser.END_TAG:

                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            //Log.e("MyTag","incidentWidget is " + plannedWorksWidget.toString());
                            plannedWorksList.add(plannedWorksWidget);
                        }
                        else
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {
                            int size;
                            size = plannedWorksList.size();
                            //Log.e("MyTag","incidentCollection size is " + size);
                        }

                        break;


                }

                // Get the next event
                eventType = xpp.next();
            }
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");

        return plannedWorksList;
    }
}
