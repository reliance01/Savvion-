
package com.savvion.rcf.bean;

import java.sql.Date;

public class DailyCOPSBean
{
    private String location;

    private String loanNumber;

    private Date   updateRejectDate;

    private String rejectReason;

    private String type;

    private String trackerNumber;

    private Date   inwardLops;

    private Date   inwardSyntel;

    public Date getInwardLops()
    {
        return inwardLops;
    }

    public void setInwardLops( Date inwardLops )
    {
        this.inwardLops = inwardLops;
    }

    public Date getInwardSyntel()
    {
        return inwardSyntel;
    }

    public void setInwardSyntel( Date inwardSyntel )
    {
        this.inwardSyntel = inwardSyntel;
    }

    public String getLoanNumber()
    {
        return loanNumber;
    }

    public void setLoanNumber( String loanNumber )
    {
        this.loanNumber = loanNumber;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

    public String getRejectReason()
    {
        return rejectReason;
    }

    public void setRejectReason( String rejectReason )
    {
        this.rejectReason = rejectReason;
    }

    public String getTrackerNumber()
    {
        return trackerNumber;
    }

    public void setTrackerNumber( String trackerNumber )
    {
        this.trackerNumber = trackerNumber;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public Date getUpdateRejectDate()
    {
        return updateRejectDate;
    }

    public void setUpdateRejectDate( Date updateRejectDate )
    {
        this.updateRejectDate = updateRejectDate;
    }
}
