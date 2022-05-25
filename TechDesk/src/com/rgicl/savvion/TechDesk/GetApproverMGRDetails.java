package com.rgicl.savvion.TechDesk;

import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import java.util.*;
import com.savvion.rcf.*;

/**
 * Only javadoc-style comments will be preserved
 */
public class GetApproverMGRDetails {
	private String ApproverID;
	private String app1MgrID;
	private String app1MgrMail;
	private String app1MgrName; 
	String sqlString = "";

	public void getMgrDetails() {
		try {
			ArrayList supportMgrDtlList = new ArrayList();
			supportMgrDtlList = CallDeskReportDAO.getInstance()
					.getEscMgrDetails(ApproverID);
			if (supportMgrDtlList.size() > 0) {
				app1MgrID = (String) supportMgrDtlList.get(0);
				app1MgrName = (String) supportMgrDtlList.get(1);
				app1MgrMail = (String) supportMgrDtlList.get(2);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Error while getting Manager Details",
					ex);
		}
	}

	public String getApp1MgrID() {
		return app1MgrID;
	}

	public void setApp1MgrID(String app1MgrID) {
		this.app1MgrID = app1MgrID;
	}

	public String getApp1MgrMail() {
		return app1MgrMail;
	}

	public void setApp1MgrMail(String app1MgrMail) {
		this.app1MgrMail = app1MgrMail;
	}

	public String getApp1MgrName() {
		return app1MgrName;
	}

	public void setApp1MgrName(String app1MgrName) {
		this.app1MgrName = app1MgrName;
	}

	public String getApproverID() {
		return ApproverID;
	}

	public void setApproverID(String approverID) {
		ApproverID = approverID;
	}

}
