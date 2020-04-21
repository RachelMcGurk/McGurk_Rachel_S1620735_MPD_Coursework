/* RACHEL MCGURK S1620735*/

package org.me.gcu.mpd_coursework;

import java.util.Date;

public class RoadWorksClass
{
    private String szTitle;
    private String szDescription;
    private String szLink;
    private String geoLocation;
    private double latitude;
    private double longitude;
    private String szAuthor;
    private String szComments;
    private Date pPubDate;
    private String szPubDate;


    public RoadWorksClass()
    {
        szTitle = "";
        szDescription = "";
        szLink = "";
    }

    public RoadWorksClass(String abolt,String awasher,String anut)
    {
        szTitle = abolt;
        szDescription = awasher;
        szLink = anut;
    }

    public void setTitle(String title)
    {
        szTitle = title;
    }

    public void setDescription(String description)
    {
        szDescription = description;
    }

    public void setLink(String link)
    {
        szLink = link;
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




    public String getTitle()
    {
        return szTitle;
    }

    public String getDescription()
    {
        return szDescription;
    }

    public String getLink()
    {
        return szLink;
    }

    /*public double getGeoLocationLat()
    {
        return dLatitude;
    }

    public double getGeoLocationLong()
    {
        return dLongitude;
    }*/


    public String getAuthor()
    {
        return szAuthor;
    }

    public String getComment()
    {
        return szComments;
    }

    public Date getPubDate()
    {
        return pPubDate;
    }
}
