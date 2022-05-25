
package com.savvion.rcf;

/*
 * (C) Copyright 1998-2008 Savvion, Inc. All Rights Reserved. This program is an
 * unpublished copyrighted work which is proprietary to Savvion, Inc. and
 * contains confidential information that is not to be reproduced or disclosed
 * to any other person or entity without prior written consent from Savvion,
 * Inc. in each and every instance. History: Creation -- Savvion India Pvt. Ltd.
 */
import java.util.ArrayList;
import java.util.List;

import com.savvion.rcf.util.PageUtil;

public class PageManager
{
    private int startPage    = 1;

    private int startIndex   = 0;

    private int endPage      = -1;

    private int endIndex     = -1;

    private int rowsPerPage  = -1;

    private int totalRecords = -1;

    private int totalPageNum = -1;

    private int currentPage  = -1;

    public PageManager()
    {
        rowsPerPage = 15;
    }

    public PageManager( int rowsPerPage )
    {
        this.rowsPerPage = rowsPerPage;
    }

    public void setPaging( String currentPageStr, int noOfRecords,
            int rowsPerPage )
    {
        this.rowsPerPage = rowsPerPage;
        setPaging( currentPageStr, noOfRecords );
    }

    public void setPaging( String currentPageStr, int noOfRecords )
    {
        totalRecords = noOfRecords;
        totalPageNum = noOfRecords / rowsPerPage;
        if ( noOfRecords % rowsPerPage != 0 )
        {
            totalPageNum++;
        }
        if ( PageUtil.self().isNullorEmpty( currentPageStr ) )
        {
            currentPage = 1;
        }
        else
        {
            try
            {
                currentPage = Integer.parseInt( currentPageStr );
                while ( ( currentPage > 1 )
                        && ( totalRecords <= ( ( currentPage - 1 ) * rowsPerPage ) ) )
                {
                    --currentPage;
                }
                if ( currentPage < 1 )
                    currentPage = 1;
            }
            catch ( NumberFormatException nfe )
            {
                currentPage = 1;
            }
        }

        startIndex = ( currentPage - 1 ) * rowsPerPage;

        if ( totalPageNum == 0 )
        {
            endIndex = 0;
            endPage = 1;
        }
        else
        {
            endIndex = ( totalPageNum != currentPage || ( noOfRecords % rowsPerPage ) == 0 ) ? startIndex
                    + rowsPerPage - 1
                    : startIndex + ( noOfRecords % rowsPerPage ) - 1;
            endPage = totalPageNum;
        }

    }

    public int getStartPage()
    {
        return startPage;
    }

    public int getEndPage()
    {
        return endPage;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public int getTotalRecords()
    {
        return totalRecords;
    }

    public int getTotalPage()
    {
        return totalPageNum;
    }

    public int getStartIndex()
    {
        return startIndex;
    }

    public int getEndIndex()
    {
        return endIndex;
    }

    public boolean isFirstPage()
    {
        return ( currentPage == 1 );
    }

    public boolean isLastPage()
    {
        return ( currentPage == totalPageNum || totalPageNum == 0 );
    }

    public List< Object > getSubList( List< Object > list )
    {
        List< Object > subList = new ArrayList< Object >();
        if ( list != null )
        {
            int index = 0;
            if ( getEndIndex() > 0 )
            {
                index = ( getEndIndex() - ( getStartIndex() - 1 ) );
            }
            else
            {
                index = getEndIndex() - getStartIndex();
            }
            if ( index > 0 )
            {
                subList = list.subList( getStartIndex(), getEndIndex() + 1 );
            }
            else
            {
                subList = list;
            }
        }
        return subList;
    }
}
