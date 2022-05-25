package com.rgicl.savvion.motor.uw;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/*import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfLoginInfo;*/
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.util.BLDocClient;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.dms.svo.DocumentFolder;
import com.savvion.sbm.dms.DSContext;


/**
 * Only javadoc-style comments will be preserved
 */

public class CallDCTM 
{
	private String PrevPolicyNumber;
    //private IDfSession session;
    private Hashtable dsValues;
    private long pid;

    public CallDCTM()
    {
    }

    public void setProcessContextData(Hashtable context)
    {
        pid = ((Long)context.get("ProcessInstanceID")).longValue();
    }

    public void setPrevPolicyNumber(String PrevPolicyNumber)
    {
        this.PrevPolicyNumber = PrevPolicyNumber;
    }

    public String getPrevPolicyNumber()
    {
        return PrevPolicyNumber;
    }

    public void getDocument() 
    {
/*        IDfSessionManager sMgr;
        sMgr = null;
        session = null;*/
        try
        {
            /*
        	if(!PrevPolicyNumber.equals("") && PrevPolicyNumber != null && !PrevPolicyNumber.equals("<NULL>"))
            {
            	Properties prop = System.getProperties();
            	System.out.println("Parshuram :" + prop.getProperty("java.library.path"));
            	PropertyReader propertyReader = PropertyReader.getInstance();
                BLServer blServer = BLClientUtil.getBizLogicServer();
                Session blSession = blServer.connect(propertyReader.getPropertyByKey("bizlogic_admin_user"), propertyReader.getPropertyByKey("bizlogic_admin_password"));
                ProcessInstance pi = blServer.getProcessInstance(blSession, pid);
                
                File file = null;
                com.savvion.sbm.dms.svo.DocumentFolder piFolder = BLDocClient.createInstanceFolder(pi.getProcessTemplateID());
                HashMap hm = new HashMap();
                DSContext ctx = new DSContext(hm);
                DocumentDS docDS = BLDocClient.createInstanceDS(ctx, piFolder, "PolicyDocuments");
                sMgr = createSessionManager(propertyReader.getPropertyByKey("rgi_documentum_repository_name"), propertyReader.getPropertyByKey("rgi_documentum_user"), propertyReader.getPropertyByKey("rgi_documentum_password"));
                session = sMgr.getSession(propertyReader.getPropertyByKey("rgi_documentum_repository_name"));
                
                IDfQuery dfQuery = new DfQuery();
                IDfSysObject sysObj = null;
                dfQuery.setDQL((new StringBuilder("select r_object_id from rgi_policy where policy_id = '")).append(PrevPolicyNumber).append("'").append(" and r_content_size > 0").toString());

		        List<String> rowsData = new ArrayList<String>();
		        IDfCollection col = dfQuery.execute(session, IDfQuery.DF_READ_QUERY);
		        
		        while (col.next()) 
		        {
			         rowsData.add(col.getString("r_object_id"));
		        }
		        System.out.println("hello :"); 
                int existCount = 0;
                for(Iterator iterator = rowsData.iterator(); iterator.hasNext();)
                {
                    String sObjectId = (String)iterator.next();
                    IDfSysObject objCopy = null;
                    sysObj = (IDfSysObject)session.getObject(new DfId(sObjectId));
                    if(sysObj != null)
                    {
                        objCopy = sysObj;
                    }
                    IDfFormat myFormat = objCopy.getFormat();
                    String ext = myFormat.getDOSExtension();
                    String filePath = (new StringBuilder(String.valueOf(propertyReader.getPropertyByKey("rgi_policy_doc_temp_path")))).append(objCopy.getObjectName()).append(".").append(ext).toString();
                    objCopy.getFileEx2(filePath, objCopy.getContentType(), 0, "", false);
                    file = new File(filePath);
                    if(docDS.isDocumentExist((new StringBuilder(String.valueOf(objCopy.getObjectName()))).append(".").append(ext).toString()))
                    {
                        existCount++;
                        docDS.create(blSession.getUser(), (new StringBuilder(String.valueOf(objCopy.getObjectName()))).append(existCount).append(".").append(ext).toString(), file,null);
                    } else
                    {
                        docDS.create(blSession.getUser(), (new StringBuilder(String.valueOf(objCopy.getObjectName()))).append(".").append(ext).toString(), file,null);
                    }
                }

                HashMap dsValues = new HashMap();
                dsValues.put("PolicyDocuments", docDS);
                pi.updateSlotValue(dsValues);
                pi.save();
                blServer.disConnect(blSession);
            }*/
            this.dsValues.put("getPrevPolicyNumber", this.dsValues.get("setPrevPolicyNumber"));
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error in CallDCTM MotorUW", e);
        }
	    finally 
	    {
			/*if (session != null) 
			{
				sMgr.release(session);
			}*/
		}
    }

 /*   IDfSessionManager createSessionManager(String docbase, String user, String pass) throws Exception 
    {
        IDfClientX clientx = new DfClientX();
        IDfClient client = clientx.getLocalClient();
        IDfSessionManager sMgr = client.newSessionManager();
        IDfLoginInfo loginInfoObj = clientx.getLoginInfo();
        loginInfoObj.setUser(user);
        loginInfoObj.setPassword(pass);
        sMgr.setIdentity(docbase, loginInfoObj);
        return sMgr;
    }

*/    public void setAllInputDataslots(Hashtable dataSlot)
    {
        dsValues = dataSlot;
        PrevPolicyNumber = (String)dsValues.get("setPrevPolicyNumber");
    }

    public Hashtable getAllOutputDataslots()
    {
        return dsValues;
    }
}