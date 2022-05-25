package bitly.services;

import java.net.InetAddress;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

import org.datacontract.schemas._2004._07.BitlyConversion.ClsRequest;
import org.tempuri.IService1Proxy;



import sms.services.BasicHttpBinding_ISendMessageStub;
import sms.services.SingleMessage;

public class SendBitly_Test {
	
	public static void main(String []args) {
		try {
			SendBitly_Test sb = new SendBitly_Test();
			sb.sendBitlySMS("T102421100047", "Approver");
			System.out.println(new IService1Proxy().shortURL(new ClsRequest("Calldesk Ticket", "CallDesk", "TEST", "6", "http://google.com")).getShort_url());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String sendBitlySMS(String TicketNo, String WorkStepName)
			throws Exception {
		System.out.println("SendBitly :: Start of sendBitlySMS() ...........");
		String strShortURL = null;
		String ID = null;
		String strmobileNo = null;
		HashMap<String, String> hm = null;
		String ApproverID = null;
		try {
			//URL url = new URL("http://rgiclservices.reliancegeneral.co.in/BitlyURL/Service.svc?wsdl");
			URL url_sms = new URL("http://relgeninsure3/services/SendMessage.svc?wsdl");
			
			hm = (HashMap<String, String>) getDetails(TicketNo, WorkStepName);
			System.out.println("SendBitly :: sendBitlySMS() :: hm : "+hm.toString());
			if (hm != null && !hm.isEmpty()) {
				ID = (String) hm.get("ID");
				strmobileNo = (String) hm.get("MOBILE");
				ApproverID = (String) hm.get("APPROVER");
				if (strmobileNo != null && strmobileNo.length() >= 10) {
					if (ID != null && ID.length() > 0) {
						String strLongURL = "https://calldesk.reliancegeneral.co.in/TicketApproval/TicketApproval.aspx?ID=" + ID;
						//BasicHttpBinding_IBitlyURLStub stub = new BasicHttpBinding_IBitlyURLStub(url, new org.apache.axis.client.Service());

						strShortURL = (String) hm.get("SHORT_URL");
						//System.out.println("strShortURL " + strShortURL);
						if (strShortURL == null) {
							try {
								strShortURL = new IService1Proxy().shortURL(new ClsRequest("Calldesk Ticket", "CallDesk", TicketNo, "6", strLongURL)).getShort_url(); 
								
							} catch (RemoteException e) {
								e.printStackTrace();
							}
							//strShortURL = stub.getShortURLGoogle(strLongURL,"Calldesk Ticket", TicketNo, strmobileNo);
						}
						System.out.println("strShortURL:: "+strShortURL);
						if (strShortURL != null && strShortURL.toLowerCase().contains("http")) {
							
							BasicHttpBinding_ISendMessageStub sms = new BasicHttpBinding_ISendMessageStub(url_sms, new org.apache.axis.client.Service());
							SingleMessage smsMsg = new SingleMessage();
							smsMsg.setApp_Process("Approver SMS");
							smsMsg.setDepartment("1");
							smsMsg.setSMS_Event("Approver SMS");
							smsMsg.setRef_Name("Ticket no");
							smsMsg.setRef_Value(TicketNo);
							smsMsg.setUserName("intranet");
							smsMsg.setPassword("rgiclintra07#");
							smsMsg.setMobileNumber(strmobileNo);
							
							UUID idOne = UUID.randomUUID();
							String uRequestId = String.valueOf(idOne);
							System.out.println("uRequestId:: "+uRequestId);
							if(uRequestId!=null && uRequestId.length()>0){
								if(uRequestId.length()>20){
									uRequestId = uRequestId.substring(0,20);
								}
								smsMsg.setSource_RequestID(uRequestId);
							}
							System.out.println("SendBitly :: sendBitlySMS() :: setting Message ...........");
							smsMsg.setMessage("Dear User, Ticket " + TicketNo + " is pending for action, click  " + strShortURL + " to taken necessary action.");
							System.out.println("SendBitly :: sendBitlySMS() :: setting Message ...........");
							sms.send(smsMsg);
							System.out.println("SendBitly :: sendBitlySMS() :: Message Sent ...........");
							System.out.println("Done1");
							if (strShortURL != null && strShortURL.length() > 5) {
								updateDetails(TicketNo, strShortURL, strLongURL, ApproverID, strmobileNo);
							}
						}

					}
				}
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		System.out.println("SendBitly :: End of sendBitlySMS() ...........");
		return strShortURL;
	}

	private Connection getConnectionToSQLDB() throws Exception {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String ip = InetAddress.getLocalHost().getHostAddress();
			String dbip = "";
			String dbuser = "";
			String dbpass = "";
			if (ip.contains("10.65.5.")) {
				//Changed by Sunil Jangir on 1-July-2021
				//dbip = "10.65.5.127";
				dbip = "RGIRMSCDDB";
				dbuser = "calldesk";
				dbpass = "calldesk";
			} else {
				//dbip = "10.65.15.98";
				dbip = "RGIRMSCDDB";
				dbuser = "calldesk";
				dbpass = "calldesk";
			}
			con = DriverManager.getConnection("jdbc:sqlserver://" + dbip
					+ ":7359;databaseName=CalldeskManagement", dbuser, dbpass);
			System.out.println("Connection in - getConnectionToSQLDB():::: "+con);
		} catch (Exception e) {
			System.out.println("Exception in getConnectionToSQLDB() "+e.getMessage());
			throw new Exception("Error in getting a DB connection"
					+ e.getMessage());
		}
		return con;
	}

	private void updateDetails(String TicketNo, String ShortURL,
			String LongURL, String ApproverID, String MobileNo)
			throws Exception {
		Connection con = null;
		CallableStatement proc_stmt = null;
		try {
			if (TicketNo != null && TicketNo.length() > 0) {
				con = getConnectionToSQLDB();
				proc_stmt = con
						.prepareCall("{ call usp_Bitly_UpdateLink(?,?,?,?,?) }");
				proc_stmt.setString(1, TicketNo);
				proc_stmt.setString(2, ShortURL);
				proc_stmt.setString(3, LongURL);
				proc_stmt.setString(4, ApproverID);
				proc_stmt.setString(5, MobileNo);
				proc_stmt.executeUpdate();
				proc_stmt.close();
				con.close();
				System.out.println("Done2");
			}
		} catch (Exception e) {
			throw new Exception("SBM Web services error :" + e.getMessage());
		} finally {
			try {
				if (proc_stmt != null) {
					try {
						proc_stmt.close();
					} catch (Exception ex) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception ex) {
					}
				}
			} catch (Exception ex) {
			}
		}
	}

	@SuppressWarnings("resource")
	private HashMap<String, String> getDetails(String TicketNo,
			String WorkstepName) throws Exception {
		System.out.println("SendBitly :: Start of getDetails() ..........."+TicketNo);
		Connection con = null;
		CallableStatement proc_stmt = null;
		ResultSet rs = null;
		String guid = null;
		HashMap<String, String> hm = new HashMap<String, String>();
		String mobileno = null;
		String Approver = null;
		String short_url = null;
		try {
			if (TicketNo != null && TicketNo.length() > 0) {				
				con = getConnectionToSQLDB();
				System.out.println("con in getDetails: "+con);
				proc_stmt = con.prepareCall("{ call usp_Bitly_GetTicketNumberDetails(?) }");
				proc_stmt.setString(1, TicketNo);
				rs = proc_stmt.executeQuery();
				System.out.println("Values in rs: "+rs.toString());				
				System.out.println("ID through getXX(Index):: "+proc_stmt.getString("ID"));
				guid = proc_stmt.getString("ID");
				short_url = proc_stmt.getString("SHORT_URL");
				mobileno = proc_stmt.getString("MOBILENO1");
				Approver = proc_stmt.getString("APPROVER");
				/*if (rs != null) {
					while (rs.next()) {
						guid = rs.getString("ID");
										
							short_url = rs.getString("SHORT_URL");
							if (WorkstepName != null
									&& WorkstepName.equalsIgnoreCase("approver")) {
								
								mobileno = rs.getString("MOBILENO1");
								Approver = rs.getString("APPROVER");
								System.out.println("approver from rs: "+Approver);
							} else {
								mobileno = rs.getString("MOBILENO2");
								Approver = rs.getString("SAPPROVER");
							}
						}				
					
				}*/
				System.out.println("ID:: "+guid   +" APPROVER "+Approver);
				hm.put("ID", guid);
				hm.put("MOBILE", mobileno);
				hm.put("APPROVER", Approver);
				hm.put("SHORT_URL", short_url);
				proc_stmt.close();
				rs.close();
				con.close();
				System.out.println("Done");
			}
		} catch (Exception e) {
			throw new Exception("SBM Web services error :" + e.getMessage());
		} finally {
			try {
				if (proc_stmt != null) {
					try {
						proc_stmt.close();
					} catch (Exception ex) {
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception ex) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception ex) {
					}
				}
			} catch (Exception ex) {
			}
		}
		System.out.println("SendBitly :: End of getDetails() ..........."+hm.toString());
		
		return hm;
	}

}
