package com.rgicl.savvion.TechDesk;

import java.util.ArrayList;

import com.savvion.rcf.CallDeskReportDAO;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;

/**
 * Only javadoc-style comments will be preserved
 */
public class GetApprover2MGRDetails1 {
	private String ApproverUserID2;
	private String app2MgrID;
	private String app2MgrMail;
	private String app2MgrName; 

	String sqlString = "";

	public void getMgrDetails() {
		try {
			ArrayList supportMgrDtlList = new ArrayList();
			supportMgrDtlList = CallDeskReportDAO.getInstance()
					.getEscMgrDetails(ApproverUserID2);

			if (supportMgrDtlList.size() > 0) {
				app2MgrID = (String) supportMgrDtlList.get(0);
				app2MgrName = (String) supportMgrDtlList.get(1);
				app2MgrMail = (String) supportMgrDtlList.get(2);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Error while getting Manager Details",
					ex);
		}
	}

	public String getApp2MgrID() {
		return app2MgrID;
	}

	public void setApp2MgrID(String app2MgrID) {
		this.app2MgrID = app2MgrID;
	}

	public String getApp2MgrMail() {
		return app2MgrMail;
	}

	public void setApp2MgrMail(String app2MgrMail) {
		this.app2MgrMail = app2MgrMail;
	}

	public String getApp2MgrName() {
		return app2MgrName;
	}

	public void setApp2MgrName(String app2MgrName) {
		this.app2MgrName = app2MgrName;
	}

	public String getApproverUserID2() {
		return ApproverUserID2;
	}

	public void setApproverUserID2(String approverUserID2) {
		ApproverUserID2 = approverUserID2;
	}
}
