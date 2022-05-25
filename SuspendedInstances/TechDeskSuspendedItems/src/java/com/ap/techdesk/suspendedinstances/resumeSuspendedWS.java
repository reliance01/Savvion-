package com.ap.techdesk.suspendedinstances;


import java.util.List;

import com.savvion.BizSolo.beans.DocLocator;
import com.savvion.BizSolo.beans.GenericAction;
import com.savvion.BizSolo.beans.XMLObject;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstance;
import com.savvion.sbm.bizlogic.server.svo.WorkStepInstanceList;
import com.savvion.sbm.bizlogic.util.Session;

/**
 * Auto-generated
 */
public class resumeSuspendedWS extends GenericAction {
	
	private long processTemplateID;
	private static BLServer blserver = null;
	private static BLClientUtil blutil = null;
	private static Session blsession = null;
	
	public int commit() {
		this.processTemplateID = getPropLong("processTemplateID");
		System.out.println("Inside commit() of ResumeAllSuspendedItems::: "+processTemplateID);
		try{
			if(blserver == null)
			{
				System.out.println("bl server is null");
				blserver = blutil.getBizLogicServer();
				blsession = blserver.connect("rgicl", "rgicl");
				WorkStepInstanceList wslist = blserver.getSuspendedWorkStepInstanceList(blsession,processTemplateID);
				if(wslist!= null){
					List ls = wslist.getList();
					if(ls!=null && !ls.isEmpty())
					{
						for(int i=0; i<ls.size(); i++)
						{
							WorkStepInstance ws = (WorkStepInstance)ls.get(i);
							if(ws.isSuspended() && ws.getProcessTemplateID()==processTemplateID && ws.getProcessInstanceID()>=22419228)
								//22419228
								//20029629
							{
								
								try{									
										ws.resume();
										System.out.println("WorkStep resumed sucessfully " + ws.getName() + " Process instance " + ws.getProcessInstanceName());									
								
								}catch(Exception ex)
								{
									System.out.println("Error while resuming suspended workstep " + ex.getMessage());
								}

							}
						}	
					}
				}
				
			}
		}catch(Exception ex){
			System.out.println("Error while getting suspended workstep count " + ex.getMessage());
		}
		setPropLong("processTemplateID", processTemplateID);
		return 0;
	}
}