package com.rgic.calldesk.excel;

import com.savvion.sbm.bizlogic.client.BLClientUtil;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
//import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
//import com.savvion.ejb.bizlogic.manager.BizLogicManager;
//import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DataSlot;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstance;
import com.savvion.sbm.bizlogic.server.svo.ProcessInstanceList;
import com.savvion.sbm.bizlogic.server.svo.ProcessTemplate;
//import com.savvion.sbm.bizlogic.util.BLDocClient;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.dms.DSContext;
//import com.savvion.sbm.dms.svo.DocumentFolder;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
//import com.rgic.icm.common.dbutil.DBQueryManager;

import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import com.rgic.icm.common.dbutil.*;
import com.savvion.sbm.bizlogic.server.svo.WorkItemList; 
import com.savvion.sbm.bizlogic.server.svo.WorkItem;
/** 
 * Only javadoc-style comments will be preserved
 */
public class ReadExcel { 
	
	
	Connection conn = null;
	HSSFSheet sheet = null;
	ByteArrayInputStream arrayInputStream = null;
	public ReadExcel(){	
	}
	
		
	HSSFWorkbook workbook = null;
	DocumentDS docDS = null;
	private static String makemodelComment;
	private static String MMApproverStatus;

	

	public DocumentDS getDocDS() {
		return docDS;
	}

	public void setDocDS(DocumentDS docDS) {
		this.docDS = docDS;
	}
	
	public static String getMakeModelComment() {
		return makemodelComment;
	}

	public static void setMakeModelComment(String makemodelComment) {
		ReadExcel.makemodelComment = makemodelComment;
	}

	public static void main(  String[] args) throws SQLException, IOException{
	    new ReadExcel().process();
		int i = 13;
		System.out.println((char)i);
	   
	}
	
	public void process() throws SQLException, IOException{
		
	    
		//DBQueryManager queryManager = new DBQueryManager();

//		int[] update  = queryManager.updateCategorySubCategoryData(pid,proposal,catSubCatCommentList);
		
			
		
	    ArrayList record=new ArrayList();
	    String Comment;
	    ArrayList recordOfRecord=new ArrayList();
	    ResultSet rs = null;
	    ResultSet rss = null;
	    Statement stmt = null;
	    Statement pstmt = null;
	    long process_instance_id;
	    ProcessInstance pie;
	    WorkItemList wl;
	    java.util.Vector vt;
	    WorkItem wi;
	    String status;
	    HashMap dsValues = new HashMap();    	    
	    	    
	    try {
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	conn  = DriverManager.getConnection("jdbc:oracle:thin:@rgivsstfs.reliancecapital.com:1521:EBMS","rgicbr","rgicbr"); //to be retrieved from DBQuerymanager	//10.65.5.120    	
	    	//Connection conn = ConnectionManager.getDBConnection();
	    	stmt = conn.createStatement();   
	    	pstmt = conn.createStatement();	    	
	    	//GET BLSERVER
	    	BLServer blServer= BLClientUtil.getBizLogicServer();	    	
	    	
	    	Session blSession= (com.savvion.sbm.bizlogic.util.Session)blServer.connect("rgicl","rgicl"); //from properties file
	    		    	
	    	ProcessTemplate pt=blServer.getProcessTemplate(blSession, "CallDesk"); //replaced by CallDesk PT
	    	long ptID = pt.getID();
	    	
	    	ProcessInstanceList piList = blServer.getProcessInstanceList(blSession, ptID);
	    	//System.out.println("ProcessInstance:..."+piList.getList(blSession));
	    	
	    	ProcessInstance pi = (ProcessInstance)piList.next();    	
	    	
	    	long pid = pi.getID();
	    	DataSlot dsValue = blServer.getDataSlot(blSession, pid, "ApproverDocument");
	    		    	
	    	DocumentDS docDs = (DocumentDS)dsValue.getValue();    	
	    	
	    	com.savvion.sbm.dms.DSContext context =  new DSContext("rgicl","rgicl", new HashMap()); //from properties file
	    	List ls1 = docDs.getDocuments();
	    	com.savvion.sbm.dms.svo.Document doc = null;
	    	//String doc  = "";
	    	for(int i=0;i<ls1.size();i++){
	    		doc = (com.savvion.sbm.dms.svo.Document)ls1.get(i);	    		
	    		//doc.saveToFile(arg0, arg1)
	    		//doc.saveToLocation(context, "D:/Rajat/");
	    		doc.saveToFile(context, "D:/StoreDoc/"+doc.getName());//from system property : java.io.temp
	    	}
	    	 
	    	//Thread.sleep(10000);

  	      	//*************code to Read the excell sheet*****************
	    	String loc = "D:/StoreDoc/"+doc.getName();
	    	int BIG_BLOCK_SIZE = 512;
	    	//String loc = "D:/Rajat/"+"1aa.xls";	    	
	    	
	    	InputStream inputStream=new FileInputStream(loc);
	    	
	    	ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
	    	byte[] bytesOfFile = new byte[512];
						int fileCount = 0;
			while((fileCount = inputStream.read(bytesOfFile)) != -1)
			{				
				byteOS.write(bytesOfFile, 0, fileCount);
			}
			byteOS.close();
			byte[] allbytes = byteOS.toByteArray();
			
			ByteArrayInputStream iStream = new ByteArrayInputStream(allbytes);
			
			int skippable = allbytes.length % BIG_BLOCK_SIZE;			
			
			byte[] outputData = new byte[allbytes.length-skippable];
			int numRead = 0;
			numRead = iStream.read ( outputData);			
			iStream.skip(skippable);			

            arrayInputStream = new ByteArrayInputStream(outputData);

	    	
	    	/*int i = 0;
	    	while ((i =fileInputStream.available()) != -1) {
	    		System.out.print ((char)fileInputStream.read());
	    		
	    	}*/
	    	
	    	//System.out.println("File Input Stream***************"+fileInputStream.read());
	    	//POIFSFileSystem fs = new POIFSFileSystem(fileInputStream);
	    	//System.out.println("POI File System***************"+fs);
	    	
	    	//workbook=new HSSFWorkbook(fileInputStream);
	    	workbook=new HSSFWorkbook(arrayInputStream);
	    	
	    	sheet=workbook.getSheetAt(0);	    	
	    	
	    	short i1=0;
	    	HSSFCell cell=null;
	    	int count = 0;
	    	for (Iterator it=sheet.rowIterator(); it.hasNext(); ) {
	    		HSSFRow sheetRow=(HSSFRow)it.next();
	    		if(count == 0){
	    			sheetRow = (HSSFRow)it.next();
	    			sheetRow = (HSSFRow)it.next();
	    		}	    		
	    		for (i1=0; i1 < sheetRow.getLastCellNum(); i1++) {
	    			cell=sheetRow.getCell(i1);
	    			if(cell != null){	    				
		    			if (cell.getCellType() == 0) {
		    				double dataNum=cell.getNumericCellValue();
		    				record.add(dataNum);		    				
		    			}
		    			else if(cell.getCellType() == 1){
		    				String dataStr=cell.getStringCellValue();		    					    				
		    				record.add(dataStr);	    				    				
		    			}	    		
	    			}
	    		}    		
    			
    			String bool = (String)record.get(24);    			
    			StringBuffer sql = new StringBuffer();
    			String sqll = new String();
    	
    			//if(bool.equalsIgnoreCase("Yes")) {
    				
    				//FETCH PID on basis of ProposalId passed.        			
        			makemodelComment = (String)record.get(23);
        			MMApproverStatus = (String)record.get(24);
        			dsValues.put("MakeModelComment", makemodelComment);
        			dsValues.put("MMApproverStatus", MMApproverStatus);
        			String propID = (String)record.get(21);
        			
        			String qry = "select process_instance_id from ICMDiscrepantCases where ProposalId = '" + propID + "'";
        			
        			rs = stmt.executeQuery(qry);
        			System.out.println("ResultSet: "+rs);
        			while(rs.next()) {        				
        				process_instance_id = rs.getLong("process_instance_id");
        				
        			
        				
        				//check whether the status is activated for the retreived pid uding processinstance table, 
            			//if status is activated then only retreive its workitem and complete it.
        				String qury = "select status from PROCESSINSTANCE where process_instance_id = "+ process_instance_id ;
        			
        				rss = pstmt.executeQuery(qury);
        				while(rss.next()) {
        					status = rss.getString("status");
        			
        					if(status.equalsIgnoreCase("PI_ACTIVATED")){         					
        						pie = ProcessInstance.get(blSession, process_instance_id);
        			
                				wl = pie.getWorkItemList("Make Model");
                	
                				vt = wl.list;
                	
                				for(int i=0;i<vt.size();i++) {
                					wi = (WorkItem)vt.get(i);
                	
                					wi.complete();
                	
                					
                					pie.updateSlotValue(dsValues);
                	
                					pie.save();
                	
                					
                					
                					//updateComment(propID,process_instance_id,MMComment);
                				}
        					}
            				
        				}
        				        				
        			}    				
    				
    			//}
    			/*if(bool.equalsIgnoreCase("No")) {
    				
    				System.out.println("BOOL IS NO");
    				sql.append("insert into CALLDESK_NO values (");
    				
    				for(int i = 0; i < record.size(); i++) {   					
    					sql.append("'"+record.get(i)+"'");
    					sql.append(",");
    					sqll = sql.substring(0, (sql.length()-1));        				
    				}
    				
    				sqll = sqll.concat(")");
    				System.out.println("SQL******NO* " + sqll.toString());
    				stmt.executeQuery(sqll.toString());
    			}*/

    			recordOfRecord.add(record);
	    		record=new ArrayList();
	    		
	    		count++;
	    	}
	    	
	    	
	    	
	    }
	    catch (Exception e) {
	    	throw new RuntimeException("Error while getting generating xls ", e);
	    	//System.err.println(e);
	    }
	    finally{
	    	if(stmt!=null){
	    		try{
	    			stmt.close();
	    		}catch(SQLException sqe){}				
	    	}
	    	if(pstmt!=null){
	    		try{
	    			pstmt.close();
	    		}catch(SQLException sqe){}				
	    	}
	    	if(rs!=null){
	    		try{
	    			rs.close();
	    		}catch(SQLException sqe){}				
	    	}
	    	if(rss!=null){
	    		try{
	    			rss.close();
	    		}catch(SQLException sqe){}				
	    	}
	    	if(conn!=null){
	    		try{
	    			conn.close();
	    		}catch(SQLException sqe){}				
	    	}
	    	if(arrayInputStream!=null){
	    		try{
	    	        arrayInputStream.close();
	    		}catch(IOException e){}
	    	}
	    }
	}

	/**
	 * @return the mMApproverStatus
	 */
	public static String getMMApproverStatus() {
		return MMApproverStatus;
	}

	/**
	 * @param approverStatus the mMApproverStatus to set
	 */
	public static void setMMApproverStatus(String approverStatus) {
		MMApproverStatus = approverStatus;
	}  
	
	/*void updateComment(String propId,long process_instance_id,String MMComment) {
		
		int i;
		System.out.println("IN UPDATE COMMENT****");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
	    	Connection conn  = DriverManager.getConnection("jdbc:oracle:thin:@10.62.68.221:1521:orcl","ram","ram");
	    	Statement stmt = conn.createStatement();
			String sql = "Update categorydata set comments = '" + MMComment + "', commentid = 0 " 
						+ " where proposal = '" + propId 
						+ "' and pid = "	+ process_instance_id 
						+ " and subcategory = 55";
			System.out.println("SQL******** " + sql);
			i = stmt.executeUpdate(sql);
			System.out.println("UPDATE Result**** " + i);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}*/
	
}
