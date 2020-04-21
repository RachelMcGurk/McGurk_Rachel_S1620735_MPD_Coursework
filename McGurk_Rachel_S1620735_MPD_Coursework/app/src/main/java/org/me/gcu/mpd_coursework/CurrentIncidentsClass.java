/* RACHEL MCGURK S1620735*/

package org.me.gcu.mpd_coursework;

import java.util.Date;

public class CurrentIncidentsClass {

    private String title;
    private String description;
    private String link;
    private String geoLocation;
    private double latitude;
    private double longitude;
    private String author;
    private String comments;
    //private Date pubDate;
    private String pubDate;

    public CurrentIncidentsClass()
    {
        title = "";
        description = "";
        link = "";
        geoLocation = "";
    }

    public CurrentIncidentsClass(String atitle,String adescription)
    {
        title = atitle;
        description = adescription;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String atitle)
    {
        title = atitle;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String adescription)
    {
        description = adescription;
    }

    public String toString()
    {
        String incident;

        incident = title + ": " + description;

        return incident;
    }


    public void setLink(String alink)
    {
        link = alink;
    }

    public String getGeoLocation()
    {
        return geoLocation;
    }


    public void setGeoLocationV2(String ageoLocation)
    {
        geoLocation = ageoLocation;
    }

    public void setGeoLocation(double alatitude, double alongitude)
    {
        latitude = alatitude;
        longitude = alongitude;
    }

    public void setPubDateV2(String apubDate)
    {
        pubDate = apubDate;
    }

    /*
    public void setAuthor(String author)
    {
        szAuthor = author;
    }

    public void setComment(String comment)
    {
        szComments = comment;
    }

    public void setPubDate(Date pubDate)
    {
        pPubDate = pubDate;
    }

    public void setPubDateV2(String pubDate)
    {
        szPubDate = pubDate;
    }

*/
}
