<%@ page import="com.savvion.sbm.bizlogic.server.ejb.BLServer" %>
<%@ page import="com.savvion.sbm.bizlogic.util.Session"  %>
<%@ page import="com.savvion.sbm.bizlogic.client.BLClientUtil" %>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.ProcessInstance" %>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.WorkStepInstance" %>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.WorkItemList" %>
<%@ page import="com.savvion.sbm.bizlogic.server.svo.WorkItem" %>

<%@ page import="com.tdiinc.userManager.UserManager" %>
<%@ page import="com.tdiinc.userManager.Realm" %>
<%@ page import="com.tdiinc.userManager.User" %>

<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.sql.DataSource"%>

<%@ page import="com.savvion.rcf.ReassignTaskAppSupport"%>

<%@ page import="java.util.*"%>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>

<%

     String systemName = request.getParameter("system");
     String piID = request.getParameter("piID");
     BLServer blServer = null;
     Session blSession = null;
     try
     {
	 blServer = BLClientUtil.getBizLogicServer();
	 blSession = blServer.connect("rgicl", "rgicl");
	 if(systemName != null && systemName.trim().length() > 0)
	 {
	     ProcessInstance pi = blServer.getProcessInstance(blSession, Long.parseLong(piID));
	     WorkStepInstance wsi = pi.getWorkStepInstance("AppSupport");
	     WorkItemList wli = wsi.getWorkItemList();
	     Vector wiList = (Vector)wli.getList();
	     //Vector pfmrs = getPerformers(systemName);
	     Vector pfmrs = new ReassignTaskAppSupport().getPerformers(systemName);
	     if(pfmrs != null && !pfmrs.isEmpty() && wiList != null && !wiList.isEmpty())
	     {
		 if(pfmrs.size() > 1)
		 {
		     for(int j = 0; j < wiList.size(); j++)
		     {
			 WorkItem wi = (WorkItem)wiList.get(j);
			 wi.makeAvailable(pfmrs);
			 wi.save();
		     }
		 } 
		 else
		 {
		     assignTasktoUser(blSession, wiList, (String)pfmrs.get(0));
		 }
		 out.println(pfmrs);
             }
        }
     }
     catch(Exception e)
     {
         throw new RuntimeException(e);
     }
     finally
     {
         if(blServer != null)
	 {
	     blServer.disConnect(blSession);
         }
     }

%>


<%!

    String jndiName = "jdbc/SBMCommonDB";
    private Connection getDBConnection() 
    {
        Connection connection = null;
	try 
	{
	    connection = ((javax.sql.DataSource) new javax.naming.InitialContext().lookup(jndiName)).getConnection();
	}
	catch (Exception ex) 
	{
		System.out.println("Error Getting DBConnection : "+ ex.getMessage());
	}
        return connection;
    }
    
    private void releaseResouce(ResultSet rset,PreparedStatement pstmt,Connection conn) 
    {
	 try 
	 {
	     if (rset != null) 
	     {
		 rset.close();
	     }
	     if (pstmt != null) 
	     {
		pstmt.close();
	     }
	     if (conn != null) 
	     {
		conn.close();
	     }
	 }
	 catch (Exception ex) 
	 {
		System.out.println("Error while ReleaseResouce : "+ ex.getMessage());
         }
    }
    
    private void assignTasktoUser(Session blSession,Vector wiList,String pfmr) throws Exception
    {
        List wiids = new ArrayList();
        boolean isAlreadyAssigned = false;
        for(int j = 0; j < wiList.size(); j++)
        {
            WorkItem wi = (WorkItem)wiList.get(j);
            isAlreadyAssigned = wi.isAssigned();
            wiids.add(Long.valueOf(wi.getID()));
        }
        if(isAlreadyAssigned)
        {
            WorkItem.reAssign(blSession, wiids, pfmr);
        }
        else
        {
            WorkItem.assign(blSession, wiids, pfmr, false);
        }
    }
    


    private Vector getPerformers(String systemName)
    {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        Vector pfmrs = null;
        try
        {
	    if(systemName != null && systemName.trim().length() > 0)
	    {
	        Realm rlm = UserManager.getDefaultRealm();
	        String systems[] = systemName.split(",");
	        connection = getDBConnection();
	        boolean isEnterdUserID = false;
	        pfmrs = new Vector();
	        for(int i = 0; i < systems.length; i++)
	        {
	    	    User u = rlm.getUser(systems[i].trim());
		    if(u != null)
		    {
		        isEnterdUserID = true;
		        pfmrs.add(systems[i].trim());
		    }
	        }  

	        if(!isEnterdUserID)
	        {
	    	    String sql = "SELECT SYSTEM_GROUPNAME FROM TECHDESK_L2GROUP WHERE UPPER(SYSTEM_NAME) = UPPER(?)";
		    pstmt = connection.prepareStatement(sql);
		    for(int i = 0; i < systems.length; i++)
		    {
		        if(systems[i] != null && systems[i].trim().length() > 0)
		        {
		    	    String grpName = null;
			    pstmt.setString(1, systems[i].trim());
			    for(rset = pstmt.executeQuery(); rset.next();)
			    {
			        grpName = rset.getString(1);
			    }

			    if(grpName != null && grpName.trim().length() > 0 && rlm.getGroup(grpName.trim()) != null)
			    {
			        String grpMembers[] = rlm.getGroup(grpName.trim()).getMemberNames();
			        for(int ij = 0; ij < grpMembers.length; ij++)
			        {
			   	    User u = rlm.getUser(grpMembers[ij]);
				    if(u != null)
				    {
				        pfmrs.add(grpMembers[ij]);
				    }
			        }  
			    }
		        }
		    }
	        }
	   }
    }
    catch(Exception e)
    {
	throw new RuntimeException(e);
    }
    finally
    {
        releaseResouce(rset, pstmt, connection);
    }
    return pfmrs;
}


%>