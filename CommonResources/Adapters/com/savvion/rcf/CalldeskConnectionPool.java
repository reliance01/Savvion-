package com.savvion.rcf;

import java.sql.Connection;

import com.savvion.TreeReport.*;
import com.savvion.sbm.util.ServiceLocator;
import com.savvion.sbm.util.ConnectionPool;

public class CalldeskConnectionPool {

	private ConnectionPool           cp                   = null;

    private int                      minConn              = 5;

    private int                      maxConn              = 30;

    private long                     connectionReqTimeout = 25;  // seconds

    private static CalldeskConnectionPool self;

    private CalldeskConnectionPool()
    {
    }

    public synchronized static CalldeskConnectionPool self()
    {
        if ( self != null )
        {
            return self;
        }
        self = new CalldeskConnectionPool();
        return self;
    }

    public ConnectionPool getConnectionPool() throws Exception
    {
        try
        {
            if ( cp == null )
            {
                String dbname = ServiceLocator.self().getDatabaseName();
                String driverName = ServiceLocator.self().getDBProperty(
                        dbname, "driver" );
                String url = ServiceLocator.self()
                        .getDBProperty( dbname, "url" );
                String user = ServiceLocator.self().getDBUser();
                String passwd = ServiceLocator.self().getDBPassword();

                cp = new ConnectionPool( dbname, driverName, url, user, passwd,
                        "DUAL", null );
                cp.setDebug( true );
                cp.setLimits( minConn, maxConn, minConn );
                cp.setConnectionReqTimeout( connectionReqTimeout );
                Connection conn = cp.getConnection();
                cp.close( conn );
            }
        }
        catch ( Exception ex )
        {
            closeCP();
            throw ex;
        }
        return cp;
    }

    public void close( Connection conn )
    {
        if ( cp != null && conn != null )
        {
            cp.close( conn );
        }
    }

    private void closeCP()
    {
        try
        {
            if ( cp != null )
            {
                cp.close();
            }
        }
        catch ( Exception ignore )
        {
            ignore.printStackTrace();
        }
    }
}
