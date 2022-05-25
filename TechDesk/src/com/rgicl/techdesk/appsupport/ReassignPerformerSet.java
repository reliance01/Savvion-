package com.rgicl.techdesk.appsupport;


import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.rcf.ReassignTaskAppSupport;
import com.savvion.sbm.bizlogic.server.svo.*;
import com.savvion.sbm.bizlogic.util.*;
import com.savvion.sbm.util.*;
import com.tdiinc.userManager.Realm;
import com.tdiinc.userManager.User;
import com.tdiinc.userManager.UserManager;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;

/**
 * Auto-generated
 */
public class ReassignPerformerSet {
	private long processInstanceID;
	private String workStep_Name;
	private String l2SystemName;
	private String appSupportGroupName;
	String jndiName = "jdbc/SBMCommonDB";
	private boolean isReassigned;	

	public void setReassignPerformer() {
		
		System.out.println("processInstanceID in ReassignPerformerSet: "+processInstanceID);
		try{
			if(isIndividualUser){
				System.out.println("setReassignPerformer : individual user"+l2SystemName);
				appSupportGroupName = l2SystemName;
				appSupportGroupName = l2SystemName.replaceAll("\\s", "");
				setPreviousCallDetails();
			}else{
				System.out.println("Getting GroupName from l2SystemName: "+l2SystemName);
				appSupportGroupName = getPerformer(l2SystemName);
				System.out.println("Getting GroupNmae:"+appSupportGroupName);
				
				setPreviousCallDetails();
			}
			
			isReassigned = false;
			l2SystemName = "";
			isIndividualUser = false;
		}
		catch(Exception e)
		{
			System.out.println("Error in getting Performer: "+e.getMessage());
		}
	}
	
	public void PAKcallerID(String processInstanceName, String workstepName, java.util.Properties bizLogicHost) {
	}

	

	public String getL2SystemName() {
		return this.l2SystemName;
	}

	public void setL2SystemName(String l2SystemName) {
		this.l2SystemName = l2SystemName;
	}
	
	
	private Connection getDBConnection() 
	{
		Connection connection = null;
		try{
		    connection = ((javax.sql.DataSource) new javax.naming.InitialContext().lookup(jndiName)).getConnection();
		}
		catch (Exception ex){
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
	
	private void setPreviousCallDetails(){
		System.out.println("START of setPreviousCallDetails");
		String systemName = l2SystemName; 
		String piID = String.valueOf(processInstanceID);
		String appSupportRmk = appSupportRemark; 
		String changeStatus = AppsupportFinalStatus;
		String quotationAmount = quotation; 
		String currentUser=appSupportPerformer;
		String ipAddress = "";
		
		System.out.println("systemName: "+systemName);
		System.out.println("piID: "+piID);
		System.out.println("appSupportRmk: "+appSupportRmk);
		System.out.println("changeStatus: "+changeStatus);
		System.out.println("quotationAmount: "+quotationAmount);
		System.out.println("currentUser: "+currentUser);
		System.out.println("ipAddress: "+ipAddress);
		
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		try{
			String qry = "call TECHDESK_ADDL2L3_DETAIL(?,?,?,?,?,?,?,?,?,?)";
			connection = getDBConnection();
			cstmt = connection.prepareCall(qry);
			cstmt.setLong(1, Long.parseLong(piID));
			cstmt.setString(2, ticketNo);
			cstmt.setString(3, currentUser);
			//cstmt.setString(4, list.trim());
			cstmt.setString(4, systemName);
			cstmt.setString(5, changeStatus.trim());
			cstmt.setString(6, appSupportRmk.trim());
			cstmt.setString(7, ipAddress);
			cstmt.registerOutParameter(8, 12);
			cstmt.registerOutParameter(9, 12);
			cstmt.registerOutParameter(10, -10);
			cstmt.executeQuery();
		}catch(Exception ex){
			System.out.println("Error in setting Previous Calldetails: "+ex.getMessage());
		}
		System.out.println("END of setPreviousCallDetails");
	}

	private String getPerformer(String l2SystemName)
	{
	    Connection connection = null;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String currentperformer = null;
	    try
	    {
	    	if(l2SystemName != null && l2SystemName.trim().length() > 0){		        
		        connection = getDBConnection();
		        String sql = "SELECT SYSTEM_GROUPNAME FROM TECHDESK_L2GROUP WHERE UPPER(SYSTEM_NAME) = UPPER(?)";
		        pstmt = connection.prepareStatement(sql);
		        pstmt.setString(1, l2SystemName.trim());
		        
		        rset = pstmt.executeQuery();
		        if (rset != null) {
		        	while (rset.next()) {
		        		currentperformer = rset.getString(1);
					}
		        }
	    	}
	    }
	    catch(Exception e){
	    	throw new RuntimeException(e);
	    }
	    finally{
	    	releaseResouce(rset, pstmt, connection);
	    }
	    System.out.println("currentperformer in reassign adapter: "+currentperformer);
	    return currentperformer;
	}

		

		public long getProcessInstanceID() {
			return this.processInstanceID;
		}

		public String getWorkStep_Name() {
			return this.workStep_Name;
		}

		public void setProcessInstanceID(long processInstanceID) {
			this.processInstanceID = processInstanceID;
		}

		public void setWorkStep_Name(String workStep_Name) {
			this.workStep_Name = workStep_Name;
		}
		public String getAppSupportGroupName() {
			return this.appSupportGroupName;
		}

		public void setAppSupportGroupName(String appSupportGroupName) {
			this.appSupportGroupName = appSupportGroupName;
		}



		public boolean getIsReassigned() {
			return this.isReassigned;
		}

		public void setIsReassigned(boolean isReassigned) {
			this.isReassigned = isReassigned;
		}

		private boolean isIndividualUser;

		public boolean getIsIndividualUser() {
			return this.isIndividualUser;
		}

		public void setIsIndividualUser(boolean isIndividualUser) {
			this.isIndividualUser = isIndividualUser;
		}

		private String appSupportRemark;

		public String getAppSupportRemark() {
			return this.appSupportRemark;
		}

		public void setAppSupportRemark(String appSupportRemark) {
			this.appSupportRemark = appSupportRemark;
		}
		private String AppsupportFinalStatus;
		
		public String getAppsupportFinalStatus() {
			return AppsupportFinalStatus;
		}

		public void setAppsupportFinalStatus(String appsupportFinalStatus) {
			AppsupportFinalStatus = appsupportFinalStatus;
		}
		
		private String callStatus;

		public String getCallStatus() {
			return this.callStatus;
		}

		public void setCallStatus(String callStatus) {
			this.callStatus = callStatus;
		}

		private String quotation;

		public String getQuotation() {
			return this.quotation;
		}

		public void setQuotation(String quotation) {
			this.quotation = quotation;
		}

		private String appSupportPerformer;

		public String getAppSupportPerformer() {
			return this.appSupportPerformer;
		}

		public void setAppSupportPerformer(String appSupportPerformer) {
			this.appSupportPerformer = appSupportPerformer;
		}

		private String ticketNo;

		public String getTicketNo() {
			return this.ticketNo;
		}

		public void setTicketNo(String ticketNo) {
			this.ticketNo = ticketNo;
		}

}