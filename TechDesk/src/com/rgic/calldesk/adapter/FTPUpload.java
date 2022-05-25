package com.rgic.calldesk.adapter;

/* 
 * Modified by Parshuram Juwekar Feb 08, 2011
 * 
 * FTPUpload API uploads document dataslot files to the ftp site.
 * This API is used to upload files to ftp site whose details are read from 
 * a properties file. Files are required to be uploaded as CallDesk .Net application
 * needs those files to be displyed to end user.
 * 
 * CallDesk .Net application searches files with file name in the FTP locations 
 * and opens them.
 * 
 * FTPUpload API uses apache commons FTPClient API to upload the File to the 
 * Ftp site  
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.rgicl.savvion.TechDesk.util.AppConstants;
import com.rgicl.savvion.TechDesk.util.PropertyReader;
import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.dms.DSContext;
import com.savvion.sbm.dms.svo.Document;
import com.savvion.util.NLog;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;

/**
 * Auto-generated
 */
public class FTPUpload {

	private DocumentDS appSupportDocument;
	private DocumentDS approverDocument;
	private String WorkStep_Name1;
	private long processInstanceID;
	private String Approver1DocLocation;
	private String Approver2DocLocation;
	private String AppSupportDocLocation;
	private BLServer blServer;
	private Session blSession;
	private File file;
	private FileInputStream fs;
	private DataSlot dsValue;
	private DocumentDS docDs;
	private Document doc;
//	private static String ftpServer="10.65.15.97";//10.65.8.30
	private static String ftpServer = "10.65.8.30";

	private static String ftpUser = "70006390";
	private static String ftpPassWord = "pass@123";
	
//	private static String ftpUser = "sa_ftpuser";
//	private static String ftpPassWord = "Pass@123456789";
	

	private static String bl_admin_user = "rgicl";
	private static String bl_admin_password = "rgicl";
	private FTPClient client;

	/*
	 * The static block loads ftp server details, bizlogic admin user details
	 * from techdesk.properties file. techdesk.properties is located under
	 * SBM_HOME/conf folder. By using static by, this properties file will be
	 * loaded only once at ejb server startup. Any new modifications/properties
	 * will need server reboot for changes to reflect.
	 * 
	 */

	static {
		try {
			// PropertyReader propertyReader = PropertyReader.getInstance();
			// ftpUser =
			// propertyReader.getPropertyByKey(AppConstants.RGI_FTP_USER);
			// ftpPassWord =
			// propertyReader.getPropertyByKey(AppConstants.RGI_FTP_PASSWORD);
			// ftpServer =
			// propertyReader.getPropertyByKey(AppConstants.RGI_FTP_SERVER);
			// bl_admin_user =
			// propertyReader.getPropertyByKey(AppConstants.BIZLOGIC_ADMIN_USER);
			// bl_admin_password =
			// propertyReader.getPropertyByKey(AppConstants.BIZLOGIC_ADMIN_PASSWORD);
		} catch (Exception e) {
			// do nothing
		}
	}

	/*
	 * getDocument() method is the main method which will get executed on
	 * ApproverFTPUpload adapter workstep. In this method document dataslot
	 * ApproverDocument/AppSupportDocument is read using BizLogic Server APIs
	 * and then files attached to document dataslot are uploaded to ftp site.
	 */

	public void getDocument() throws Exception {
		try {
			blServer = BLClientUtil.getBizLogicServer();
			blSession = blServer.connect(bl_admin_user, bl_admin_password);

			if (WorkStep_Name1.equalsIgnoreCase("ApproverFTPUpload")) {
				dsValue = blServer.getDataSlot(blSession, processInstanceID, "ApproverDocument");
			} else {
				dsValue = blServer.getDataSlot(blSession, processInstanceID, "AppSupportDocument");
			}

			docDs = (DocumentDS) dsValue.getValue();
			DSContext context = new DSContext("rgicl", "rgicl", new HashMap());
			List ls1 = docDs.getDocuments();

			for (int i = 0; i < ls1.size(); i++) {
				doc = (com.savvion.sbm.dms.svo.Document) ls1.get(i);
				doc.saveToFile(context, "/data/CallDesk_FTPFile_TempLoc/" + doc.getName());
				file = new File("/data/CallDesk_FTPFile_TempLoc/" + doc.getName());
				// Upload documnet to upload to ftp server
				upload(ftpServer, ftpUser, ftpPassWord, doc.getName(), file);
			}

			blServer.disConnect(blSession);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * upload() method uses apache commons FTPClient API and uploads files to
	 * the FTP Site
	 * 
	 */

	public void upload(String ftpServer, String user, String password, String fileName, File file) throws Exception {
		try {
			client = new FTPClient();
			int reply;
			client.connect(ftpServer);
			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = client.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				try {
					client.disconnect();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			client.login(ftpUser, ftpPassWord);
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.enterLocalPassiveMode();
			fs = new FileInputStream(file);
			client.storeFile(fileName, fs);
			client.logout();
			StringBuffer sb = new StringBuffer();
			sb.append("ftp://");
			sb.append(ftpServer);
			sb.append("/");
			sb.append(fileName);
	
			if (WorkStep_Name1.equalsIgnoreCase("ApproverFTPUpload")) {
				setApprover1DocLocation(sb.toString());
			} else {
				setAppSupportDocLocation(sb.toString());
			}
			NLog.ws.info("File Uploaded Successfully..."+fileName);
			File tempfile = new File("/data/CallDesk_FTPFile_TempLoc/" + fileName);
	
			if (tempfile.exists()) {
				tempfile.delete();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					// do nothing
					throw new RuntimeException(e);
				}
			}
			if (client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException e) {
					// do nothing
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void setAppSupportDocument(DocumentDS appSupportDocument) {
		this.appSupportDocument = appSupportDocument;
	}

	public DocumentDS getAppSupportDocument() {
		return this.appSupportDocument;
	}

	public String getWorkStep_Name1() {
		return WorkStep_Name1;
	}

	public void setApproverDocument(DocumentDS approverDocument) {
		this.approverDocument = approverDocument;
	}

	public DocumentDS getApproverDocument() {
		return this.approverDocument;
	}

	public void setProcessInstanceID(long processInstanceID) {
		this.processInstanceID = processInstanceID;
	}

	public long getProcessInstanceID() {
		return this.processInstanceID;
	}

	public String getApprover1DocLocation() {
		return Approver1DocLocation;
	}

	public void setApprover1DocLocation(String approver1DocLocation) {
		Approver1DocLocation = approver1DocLocation;
	}

	public String getApprover2DocLocation() {
		return Approver2DocLocation;
	}

	public void setApprover2DocLocation(String approver2DocLocation) {
		this.Approver2DocLocation = approver2DocLocation;
	}

	public String getAppSupportDocLocation() {
		return AppSupportDocLocation;
	}

	public void setAppSupportDocLocation(String appSupportDocLocation) {
		AppSupportDocLocation = appSupportDocLocation;
	}

	public void PAKcallerID(String processInstanceName, String workstepName, java.util.Properties bizLogicHost) {
	}

	public void setWorkStep_Name1(String workStep_Name1) {
		this.WorkStep_Name1 = workStep_Name1;
	}

	private String appSupportDocLocation;
	private String approver1DocLocation;
	private String approver2DocLocation;
	private String workStep_Name1;
}
