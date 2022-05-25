package com.savvion.rcf.bean;

import java.sql.Date;

public class TATReportDataBean {
	private String tatDuration;
	//private String ESCALATIONSTATUS;
	//private String TICKETNO;
	private String STATUS;
	private String APPSUPPORTGROUPNAME;
	//private String ISSUEREQUESTID;
	//private String APPLICATIONTYPE;
	//private String CALLCREATEDBY;
	//private String CALLTYPE;
	//private String CALLLOGDATE;
	private String WORKSTEPNAME;
	private int pid;
	
	private String TICKETNO;
	private String CALLCREATEDBY;
	private String BRANCH;
	private String APPLICATIONTYPE;
	private String CALLTYPE;
	private String CALLLOGDATE;
	private String APPROVERNAME;
	private String APPROVERSTATUS;
	private String APPROVEDDATESTRING;
	private String APPSUPPORTSTATUS;
	private String APPSUPPORTCLOSEDTSTRING;
	private String START_TIME;
	private String END_TIME;
	private String ELAPSED_TIME;
	private String TAT_TIME;
	private String USERREMARK;
	private String APPSUPPORTREMARK;
	private String PI_STATUS;
	private String WI_PERFORMER;
		
	private String ISSUEREQUESTID;
	private String ISSUEREQUESTSUBID;
	private String REOPENID;
	private String APPROVERUSERID;
	private String APPROVERDESIGNATION;
	private String APPROVERREMARK;
	private String ESCALATIONSTATUS;
	private String TEAMNAME;
	private String APPSUPPORTPERFORMER;
	private String UPLOADSCREEN;
	private String FINALCALLTICKETSTATUS;
	
	private String APPROVER2NAME;
	private String APPROVER1DOCLOCATION;
	private String APPROVER2DOCLOCATION;
	private String APPSUPPORTDOCLOCATION;
	private String FINALAPPROVALDATE;
	private String APPROVERFINALREMARK;
	private String APPROVERFINALREMARK1;
	private String APPROVALFINALSTATUS;
	private String APPSUPPORTFINALSTATUS;
	private String VERIFYAPPROVALREMARK;

	public String getAPPLICATIONTYPE() {
		return APPLICATIONTYPE;
	}

	public void setAPPLICATIONTYPE(String applicationtype) {
		APPLICATIONTYPE = applicationtype;
	}

	public String getTatDuration() {
		return tatDuration;
	}

	public void setTatDuration(String tatDuration) {
		this.tatDuration = tatDuration;
	}

	public String getAPPSUPPORTGROUPNAME() {
		return APPSUPPORTGROUPNAME;
	}

	public void setAPPSUPPORTGROUPNAME(String appsupportgroupname) {
		this.APPSUPPORTGROUPNAME = appsupportgroupname;
	}

	public String getESCALATIONSTATUS() {
		return ESCALATIONSTATUS;
	}

	public void setESCALATIONSTATUS(String escalationstatus) {
		this.ESCALATIONSTATUS = escalationstatus;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String status) {
		this.STATUS = status;
	}

	public String getTICKETNO() {
		return TICKETNO;
	}

	public void setTICKETNO(String ticketno) {
		this.TICKETNO = ticketno;
	}

	public String getISSUEREQUESTID() {
		return ISSUEREQUESTID;
	}

	public void setISSUEREQUESTID(String issuerequestid) {
		this.ISSUEREQUESTID = issuerequestid;
	}

	public String getCALLCREATEDBY() {
		return CALLCREATEDBY;
	}

	public void setCALLCREATEDBY(String callcreatedby) {
		this.CALLCREATEDBY = callcreatedby;
	}

	public String getCALLLOGDATE() {
		return CALLLOGDATE;
	}

	public void setCALLLOGDATE(String calllogdate) {
		this.CALLLOGDATE = calllogdate;
	}

	public String getCALLTYPE() {
		return CALLTYPE;
	}

	public void setCALLTYPE(String calltype) {
		this.CALLTYPE = calltype;
	}

	public String getWORKSTEPNAME() {
		return WORKSTEPNAME;
	}

	public void setWORKSTEPNAME(String workstepname) {
		WORKSTEPNAME = workstepname;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getAPPROVALFINALSTATUS() {
		return APPROVALFINALSTATUS;
	}

	public void setAPPROVALFINALSTATUS(String approvalfinalstatus) {
		APPROVALFINALSTATUS = approvalfinalstatus;
	}

	public String getAPPROVEDDATESTRING() {
		return APPROVEDDATESTRING;
	}

	public void setAPPROVEDDATESTRING(String approveddatestring) {
		APPROVEDDATESTRING = approveddatestring;
	}

	public String getAPPROVER1DOCLOCATION() {
		return APPROVER1DOCLOCATION;
	}

	public void setAPPROVER1DOCLOCATION(String approver1doclocation) {
		APPROVER1DOCLOCATION = approver1doclocation;
	}

	public String getAPPROVER2DOCLOCATION() {
		return APPROVER2DOCLOCATION;
	}

	public void setAPPROVER2DOCLOCATION(String approver2doclocation) {
		APPROVER2DOCLOCATION = approver2doclocation;
	}

	public String getAPPROVER2NAME() {
		return APPROVER2NAME;
	}

	public void setAPPROVER2NAME(String approver2name) {
		APPROVER2NAME = approver2name;
	}

	public String getAPPROVERDESIGNATION() {
		return APPROVERDESIGNATION;
	}

	public void setAPPROVERDESIGNATION(String approverdesignation) {
		APPROVERDESIGNATION = approverdesignation;
	}

	public String getAPPROVERFINALREMARK() {
		return APPROVERFINALREMARK;
	}

	public void setAPPROVERFINALREMARK(String approverfinalremark) {
		APPROVERFINALREMARK = approverfinalremark;
	}

	public String getAPPROVERFINALREMARK1() {
		return APPROVERFINALREMARK1;
	}

	public void setAPPROVERFINALREMARK1(String approverfinalremark1) {
		APPROVERFINALREMARK1 = approverfinalremark1;
	}

	public String getAPPROVERNAME() {
		return APPROVERNAME;
	}

	public void setAPPROVERNAME(String approvername) {
		APPROVERNAME = approvername;
	}

	public String getAPPROVERREMARK() {
		return APPROVERREMARK;
	}

	public void setAPPROVERREMARK(String approverremark) {
		APPROVERREMARK = approverremark;
	}

	public String getAPPROVERSTATUS() {
		return APPROVERSTATUS;
	}

	public void setAPPROVERSTATUS(String approverstatus) {
		APPROVERSTATUS = approverstatus;
	}

	public String getAPPROVERUSERID() {
		return APPROVERUSERID;
	}

	public void setAPPROVERUSERID(String approveruserid) {
		APPROVERUSERID = approveruserid;
	}

	public String getAPPSUPPORTCLOSEDTSTRING() {
		return APPSUPPORTCLOSEDTSTRING;
	}

	public void setAPPSUPPORTCLOSEDTSTRING(String appsupportclosedtstring) {
		APPSUPPORTCLOSEDTSTRING = appsupportclosedtstring;
	}

	public String getAPPSUPPORTDOCLOCATION() {
		return APPSUPPORTDOCLOCATION;
	}

	public void setAPPSUPPORTDOCLOCATION(String appsupportdoclocation) {
		APPSUPPORTDOCLOCATION = appsupportdoclocation;
	}

	public String getAPPSUPPORTFINALSTATUS() {
		return APPSUPPORTFINALSTATUS;
	}

	public void setAPPSUPPORTFINALSTATUS(String appsupportfinalstatus) {
		APPSUPPORTFINALSTATUS = appsupportfinalstatus;
	}

	public String getAPPSUPPORTPERFORMER() {
		return APPSUPPORTPERFORMER;
	}

	public void setAPPSUPPORTPERFORMER(String appsupportperformer) {
		APPSUPPORTPERFORMER = appsupportperformer;
	}

	public String getAPPSUPPORTREMARK() {
		return APPSUPPORTREMARK;
	}

	public void setAPPSUPPORTREMARK(String appsupportremark) {
		APPSUPPORTREMARK = appsupportremark;
	}

	public String getAPPSUPPORTSTATUS() {
		return APPSUPPORTSTATUS;
	}

	public void setAPPSUPPORTSTATUS(String appsupportstatus) {
		APPSUPPORTSTATUS = appsupportstatus;
	}

	public String getBRANCH() {
		return BRANCH;
	}

	public void setBRANCH(String branch) {
		BRANCH = branch;
	}

	public String getELAPSED_TIME() {
		return ELAPSED_TIME;
	}

	public void setELAPSED_TIME(String elapsed_time) {
		ELAPSED_TIME = elapsed_time;
	}

	public String getEND_TIME() {
		return END_TIME;
	}

	public void setEND_TIME(String end_time) {
		END_TIME = end_time;
	}

	public String getFINALAPPROVALDATE() {
		return FINALAPPROVALDATE;
	}

	public void setFINALAPPROVALDATE(String finalapprovaldate) {
		FINALAPPROVALDATE = finalapprovaldate;
	}

	public String getFINALCALLTICKETSTATUS() {
		return FINALCALLTICKETSTATUS;
	}

	public void setFINALCALLTICKETSTATUS(String finalcallticketstatus) {
		FINALCALLTICKETSTATUS = finalcallticketstatus;
	}

	public String getISSUEREQUESTSUBID() {
		return ISSUEREQUESTSUBID;
	}

	public void setISSUEREQUESTSUBID(String issuerequestsubid) {
		ISSUEREQUESTSUBID = issuerequestsubid;
	}

	public String getPI_STATUS() {
		return PI_STATUS;
	}

	public void setPI_STATUS(String pi_status) {
		PI_STATUS = pi_status;
	}

	public String getREOPENID() {
		return REOPENID;
	}

	public void setREOPENID(String reopenid) {
		REOPENID = reopenid;
	}

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String start_time) {
		START_TIME = start_time;
	}

	public String getTAT_TIME() {
		return TAT_TIME;
	}

	public void setTAT_TIME(String tat_time) {
		TAT_TIME = tat_time;
	}

	public String getTEAMNAME() {
		return TEAMNAME;
	}

	public void setTEAMNAME(String teamname) {
		TEAMNAME = teamname;
	}

	public String getUPLOADSCREEN() {
		return UPLOADSCREEN;
	}

	public void setUPLOADSCREEN(String uploadscreen) {
		UPLOADSCREEN = uploadscreen;
	}

	public String getUSERREMARK() {
		return USERREMARK;
	}

	public void setUSERREMARK(String userremark) {
		USERREMARK = userremark;
	}

	public String getVERIFYAPPROVALREMARK() {
		return VERIFYAPPROVALREMARK;
	}

	public void setVERIFYAPPROVALREMARK(String verifyapprovalremark) {
		VERIFYAPPROVALREMARK = verifyapprovalremark;
	}

	public String getWI_PERFORMER() {
		return WI_PERFORMER;
	}

	public void setWI_PERFORMER(String wi_performer) {
		WI_PERFORMER = wi_performer;
	}

		
	
}
