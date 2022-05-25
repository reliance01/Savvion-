
package com.savvion.rcf.bean;

import java.sql.Date;

public class CourierMISBean
{
    private String packetNo;

    private Date   receivedDate;

    private String awbNo;

    private String location;

    private String packetStatus;

    public String getAwbNo()
    {
        return awbNo;
    }

    public void setAwbNo( String awbNo )
    {
        this.awbNo = awbNo;
    }

    public String getPacketNo()
    {
        return packetNo;
    }

    public void setPacketNo( String packetNo )
    {
        this.packetNo = packetNo;
    }

    public String getPacketStatus()
    {
        return packetStatus;
    }

    public void setPacketStatus( String packetStatus )
    {
        this.packetStatus = packetStatus;
    }

    public Date getReceivedDate()
    {
        return receivedDate;
    }

    public void setReceivedDate( Date receivedDate )
    {
        this.receivedDate = receivedDate;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }
}
