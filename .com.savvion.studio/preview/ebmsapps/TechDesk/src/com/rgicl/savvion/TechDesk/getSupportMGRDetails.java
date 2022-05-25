package com.rgicl.savvion.TechDesk;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.Session;

import java.util.*;
import com.savvion.rcf.*;

/**
 * Only javadoc-style comments will be preserved
 */
public class getSupportMGRDetails { 

	private String TeamName;
	private String supportMgrID;
	private String supportMgrMail;
	private String supportMgrName;
	String sqlString = "";
	long piID = 0;
	private String supportMgr2;
	public String getSupportMgr2() {
		return supportMgr2;
	}
	            
	public void setSupportMgr2(String supportMgr2) {
		this.supportMgr2 = supportMgr2;
	}

	public String getSupportMgr3() {
		return supportMgr3;
	}

	public void setSupportMgr3(String supportMgr3) {
		this.supportMgr3 = supportMgr3;
	}

	private String supportMgr3;

	public void getDetails() {
		try {
			ArrayList supportMgrDtlList = new ArrayList();
			supportMgrDtlList = CallDeskReportDAO.getInstance()
					.getSupportEscMgrDetails(TeamName);

			if (supportMgrDtlList.size() > 0) {
				supportMgrID = (String) supportMgrDtlList.get(1);
				supportMgrName = (String) supportMgrDtlList.get(2);
				supportMgrMail = (String) supportMgrDtlList.get(3);
				supportMgr2 = (String)supportMgrDtlList.get(4);
				supportMgr3 = (String)supportMgrDtlList.get(5);

				 if(supportMgr2==null || supportMgr2.equals("") || supportMgr2.equals("<NULL>"))
				 {
					 supportMgr2 = "70008042";
				 }

				 if(supportMgr3==null || supportMgr3.equals("") || supportMgr3.equals("<NULL>"))
				 {
					 supportMgr3 = "70008042";
				 }
			}
			BLServer blserver = BLClientUtil.getBizLogicServer();
			Session blsession = blserver.connect("rgicl", "rgicl");
			ProcessInstance pi = blserver.getProcessInstance(blsession, piID);
			HashMap hm = new HashMap();
			hm.put("supportMgr2", supportMgr2);
			hm.put("supportMgr3", supportMgr3);

			pi.updateSlotValue(hm);
			pi.save();
			blserver.disConnect(blsession);

		} catch (Exception ex) {
			throw new RuntimeException(
					"Error while getting Support Manager Details", ex);
		}
	}

	public void setProcessContextData(Hashtable processCtx) {
		piID = ((Long) processCtx.get("ProcessInstanceID")).longValue();
	}

	public String getSupportMgrID() {
		return supportMgrID;
	}

	public void setSupportMgrID(String supportMgrID) {
		this.supportMgrID = supportMgrID;
	}

	public String getSupportMgrMail() {
		return supportMgrMail;
	}

	public void setSupportMgrMail(String supportMgrMail) {
		this.supportMgrMail = supportMgrMail;
	}

	public String getSupportMgrName() {
		return supportMgrName;
	}

	public void setSupportMgrName(String supportMgrName) {
		this.supportMgrName = supportMgrName;
	}

	public String getTeamName() {
		return TeamName;
	}

	public void setTeamName(String teamName) {
		TeamName = teamName;
	}

}
