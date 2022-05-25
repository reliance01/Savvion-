package com.rgi.savvion.techdesk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.savvion.BizSolo.beans.GenericAction;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstanceList;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.BizSolo.beans.DocLocator;
import com.savvion.BizSolo.beans.XMLObject;

/**
 * Auto-generated
 */
public class GetAllSuspendedItems extends GenericAction {
	private long count;
	private long processTemplateID;
	private long suspendedItemsCount;
	private String wSName;
	private Map dataMap;

	private static BLServer blserver = null;
	private static BLClientUtil blutil = null;
	private static Session blsession = null;

	public int commit() {
		this.processTemplateID = getPropLong("processTemplateID");
		this.suspendedItems = getPropMap("suspendedItems");
		this.suspendedItemsCount = getPropLong("suspendedItemsCount");
		this.count = getPropLong("Count");
		this.wSName = getPropString("WSName");

		/**
		 * Logic to get Count and the Suspended Items
		 */

		suspendedItemsCount = getSuspendedItemsCount(processTemplateID);
		System.out.println("Suspended Count: " + suspendedItemsCount);
		suspendedItems = getWorkStepNames(processTemplateID);

		setPropLong("processTemplateID", processTemplateID);
		setPropMap("suspendedItems", suspendedItems);
		setPropLong("suspendedItemsCount", suspendedItemsCount);
		setPropLong("Count", count);
		setPropString("WSName", wSName);
		return 0;
	}

	private long getSuspendedItemsCount(long ptid) {
		long count = 0;

		try {
			if (blserver == null) {
				blserver = blutil.getBizLogicServer();
				blsession = blserver.connect("rgicl", "rgicl");
				WorkStepInstanceList wslist = blserver.getSuspendedWorkStepInstanceList(blsession, ptid);
				if (wslist != null) {
					List ls = wslist.getList();
					if (ls != null && !ls.isEmpty()) {

						for (int i = 0; i < ls.size(); i++) {
							WorkStepInstance ws = (WorkStepInstance) ls.get(i);

							if (ws.getProcessInstanceID() >= 22419228) {
								// 20029629
								// 22419228
								count++;
							}
						}
					}
					System.out.println("No of Suspended Items: " + count);
				}
			}
		} catch (Exception ex) {
			System.out.println("Error while getting suspended workstep count " + ex.getMessage());
		}

		return count;
	}

	private Map getWorkStepNames(long processTemplateID) {
		System.out.println("Inside getWorkStepNames method");
		Map<String, String> wsDataMap = new HashMap<String, String>();
		try {
			// if(blserver == null)
			// {
			System.out.println("Inside getWorkStepNames method : got blserver running");
			// blserver = blutil.getBizLogicServer();
			// blsession = blserver.connect("rgicl", "rgicl");
			System.out.println("Inside getWorkStepNames method : connected to blserver and got session");
			WorkStepInstanceList wslist = blserver.getSuspendedWorkStepInstanceList(blsession, processTemplateID);
			System.out.println("wslist:::: " + wslist.toString());
			if (wslist != null) {
				List ls = wslist.getList();
				System.out.println("wslist size :::::::::: " + ls.size());
				if (ls != null && !ls.isEmpty()) {
					int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0, counter6 = 0, counter7 = 0;
					for (int i = 0; i < ls.size(); i++) {
						WorkStepInstance ws = (WorkStepInstance) ls.get(i);

						if (ws.getProcessInstanceID() > 22980676) {
							System.out.println("ws.getName()::::: " + ws.getName());
							if (ws.getName().equalsIgnoreCase("ExpectedClosureDateWS")) {
								// wsNamesList.add("ExpectedClosureDateWS");
								wsDataMap.put("ExpectedClosureDateWS", "ExpectedClosureDateWS");
								System.out.println("ExpectedClosueDateWs If Block");
							}
							if (ws.getName().equalsIgnoreCase("UpdateApprover2Status")) {
								wsDataMap.put("UpdateApprover2Status", "UpdateApprover2Status");
								System.out.println("ExpectedClosueDateWs If Block");
							}
							if (ws.getName().equalsIgnoreCase("UpdateCallDeskData")) {
								wsDataMap.put("UpdateCallDeskData", "UpdateCallDeskData");
								System.out.println("UpdateCallDeskData If Block");
							}
							if (ws.getName().equalsIgnoreCase("AppSupportFTPUpload")) {
								wsDataMap.put("AppSupportFTPUpload", "AppSupportFTPUpload");
								System.out.println("AppSupportFTPUpload If Block");
							}
							if (ws.getName().equalsIgnoreCase("UpdateApproverStatus")) {
								wsDataMap.put("UpdateApproverStatus", "UpdateApproverStatus");
								System.out.println("UpdateApproverStatus If Block");
							}							
							if (ws.getName().equalsIgnoreCase("AppSupport")) {
								wsDataMap.put("AppSupport", "AppSupport");
							}
							if (ws.getName().equalsIgnoreCase("Approver")) {
								wsDataMap.put("Approver", "Approver");
							}

						}

					}
					System.out.println("wsDataMap:::: " + wsDataMap.toString());
				}
			}

			// }
		} catch (Exception ex) {
			System.out.println("Error while getting suspended workstep Names " + ex.getMessage());
		}

		// setPropString("WSName", wSName);
		// setPropMap("WSMap", wsDataMap);
		return wsDataMap;
	}

	private java.util.Map suspendedItems;
}