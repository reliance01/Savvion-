package com.savvion.rcf.bean;

public class DetailedReportDataBean {

	private String IS_L1TEAMDATA;
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
	private String WI_PREV_PERFORMER;

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
	private String CALLSTATUS;

	public String getCALLSTATUS() {
		return CALLSTATUS;
	}

	public void setCALLSTATUS(String cALLSTATUS) {
		CALLSTATUS = cALLSTATUS;
	}

	public String getIS_L1TEAMDATA() {
		return IS_L1TEAMDATA;
	}

	public void setIS_L1TEAMDATA(String iSL1TEAMDATA) {
		IS_L1TEAMDATA = iSL1TEAMDATA;
	}

	/**
	 * @return the fINALCALLTICKETSTATUS
	 */
	public String getFINALCALLTICKETSTATUS() {
		return FINALCALLTICKETSTATUS;
	}

	/**
	 * @param finalcallticketstatus
	 *            the fINALCALLTICKETSTATUS to set
	 */
	public void setFINALCALLTICKETSTATUS(String finalcallticketstatus) {
		this.FINALCALLTICKETSTATUS = finalcallticketstatus;
	}

	/**
	 * @return the aPPSUPPORTPERFORMER
	 */
	public String getAPPSUPPORTPERFORMER() {
		return APPSUPPORTPERFORMER;
	}

	/**
	 * @param appsupportperformer
	 *            the aPPSUPPORTPERFORMER to set
	 */
	public void setAPPSUPPORTPERFORMER(String appsupportperformer) {
		this.APPSUPPORTPERFORMER = appsupportperformer;
	}

	/**
	 * @return the tEAMNAME
	 */
	public String getTEAMNAME() {
		return TEAMNAME;
	}

	/**
	 * @param teamname
	 *            the tEAMNAME to set
	 */
	public void setTEAMNAME(String teamname) {
		TEAMNAME = teamname;
	}

	public String getAPPLICATIONTYPE() {
		return APPLICATIONTYPE;
	}

	public void setAPPLICATIONTYPE(String applicationtype) {
		this.APPLICATIONTYPE = applicationtype;
	}

	public String getAPPROVEDDATESTRING() {
		return APPROVEDDATESTRING;
	}

	public void setAPPROVEDDATESTRING(String approveddatestring) {
		this.APPROVEDDATESTRING = approveddatestring;
	}

	public String getAPPROVERNAME() {
		return APPROVERNAME;
	}

	public void setAPPROVERNAME(String approvername) {
		this.APPROVERNAME = approvername;
	}

	public String getAPPROVERSTATUS() {
		return APPROVERSTATUS;
	}

	public void setAPPROVERSTATUS(String approverstatus) {
		this.APPROVERSTATUS = approverstatus;
	}

	public String getAPPSUPPORTCLOSEDTSTRING() {
		return APPSUPPORTCLOSEDTSTRING;
	}

	public void setAPPSUPPORTCLOSEDTSTRING(String appsupportclosedtstring) {
		this.APPSUPPORTCLOSEDTSTRING = appsupportclosedtstring;
	}

	public String getAPPSUPPORTSTATUS() {
		return APPSUPPORTSTATUS;
	}

	public void setAPPSUPPORTSTATUS(String appsupportstatus) {
		this.APPSUPPORTSTATUS = appsupportstatus;
	}

	public String getBRANCH() {
		return BRANCH;
	}

	public void setBRANCH(String branch) {
		this.BRANCH = branch;
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

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String start_time) {
		this.START_TIME = start_time;
	}

	public String getTICKETNO() {
		return TICKETNO;
	}

	public void setTICKETNO(String ticketno) {
		this.TICKETNO = ticketno;
	}

	public String getELAPSED_TIME() {
		return ELAPSED_TIME;
	}

	public void setELAPSED_TIME(String elapsed_time) {
		ELAPSED_TIME = elapsed_time;
	}

	public String getTAT_TIME() {
		return TAT_TIME;
	}

	public void setTAT_TIME(String tat_time) {
		TAT_TIME = tat_time;
	}

	public String getEND_TIME() {
		return END_TIME;
	}

	public void setEND_TIME(String end_time) {
		END_TIME = end_time;
	}

	public String getUSERREMARK() {
		return USERREMARK;
	}

	public void setUSERREMARK(String userremark) {
		USERREMARK = userremark;
	}

	public String getAPPSUPPORTREMARK() {
		return APPSUPPORTREMARK;
	}

	public void setAPPSUPPORTREMARK(String appsupportremark) {
		APPSUPPORTREMARK = appsupportremark;
	}

	public String getPI_STATUS() {
		return PI_STATUS;
	}

	public void setPI_STATUS(String pi_status) {
		PI_STATUS = pi_status;
	}

	public String getWI_PERFORMER() {
		return WI_PERFORMER;
	}

	public void setWI_PERFORMER(String wi_performer) {
		WI_PERFORMER = wi_performer;
	}

	public String getAPPROVERDESIGNATION() {
		return APPROVERDESIGNATION;
	}

	public void setAPPROVERDESIGNATION(String approverdesignation) {
		APPROVERDESIGNATION = approverdesignation;
	}

	public String getAPPROVERREMARK() {
		return APPROVERREMARK;
	}

	public void setAPPROVERREMARK(String approverremark) {
		APPROVERREMARK = approverremark;
	}

	public String getAPPROVERUSERID() {
		return APPROVERUSERID;
	}

	public void setAPPROVERUSERID(String approveruserid) {
		APPROVERUSERID = approveruserid;
	}

	public String getESCALATIONSTATUS() {
		return ESCALATIONSTATUS;
	}

	public void setESCALATIONSTATUS(String escalationstatus) {
		ESCALATIONSTATUS = escalationstatus;
	}

	public String getISSUEREQUESTID() {
		return ISSUEREQUESTID;
	}

	public void setISSUEREQUESTID(String issuerequestid) {
		ISSUEREQUESTID = issuerequestid;
	}

	public String getREOPENID() {
		return REOPENID;
	}

	public void setREOPENID(String reopenid) {
		REOPENID = reopenid;
	}

	/**
	 * @return the uPLOADSCREEN
	 */
	public String getUPLOADSCREEN() {
		return UPLOADSCREEN;
	}

	/**
	 * @param uploadscreen
	 *            the uPLOADSCREEN to set
	 */
	public void setUPLOADSCREEN(String uploadscreen) {
		UPLOADSCREEN = uploadscreen;
	}

	/**
	 * @return the aPPROVER1DOCLOCATION
	 */
	public String getAPPROVER1DOCLOCATION() {
		return APPROVER1DOCLOCATION;
	}

	/**
	 * @param approver1doclocation
	 *            the aPPROVER1DOCLOCATION to set
	 */
	public void setAPPROVER1DOCLOCATION(String approver1doclocation) {
		APPROVER1DOCLOCATION = approver1doclocation;
	}

	/**
	 * @return the aPPROVER2DOCLOCATION
	 */
	public String getAPPROVER2DOCLOCATION() {
		return APPROVER2DOCLOCATION;
	}

	/**
	 * @param approver2doclocation
	 *            the aPPROVER2DOCLOCATION to set
	 */
	public void setAPPROVER2DOCLOCATION(String approver2doclocation) {
		APPROVER2DOCLOCATION = approver2doclocation;
	}

	/**
	 * @return the aPPROVER2NAME
	 */
	public String getAPPROVER2NAME() {
		return APPROVER2NAME;
	}

	/**
	 * @param approver2name
	 *            the aPPROVER2NAME to set
	 */
	public void setAPPROVER2NAME(String approver2name) {
		APPROVER2NAME = approver2name;
	}

	/**
	 * @return the aPPROVERFINALREMARK
	 */
	public String getAPPROVERFINALREMARK() {
		return APPROVERFINALREMARK;
	}

	/**
	 * @param approverfinalremark
	 *            the aPPROVERFINALREMARK to set
	 */
	public void setAPPROVERFINALREMARK(String approverfinalremark) {
		APPROVERFINALREMARK = approverfinalremark;
	}

	/**
	 * @return the fINALAPPROVALDATE
	 */
	public String getFINALAPPROVALDATE() {
		return FINALAPPROVALDATE;
	}

	/**
	 * @param finalapprovaldate
	 *            the fINALAPPROVALDATE to set
	 */
	public void setFINALAPPROVALDATE(String finalapprovaldate) {
		FINALAPPROVALDATE = finalapprovaldate;
	}

	/**
	 * @return the aPPSUPPORTDOCLOCATION
	 */
	public String getAPPSUPPORTDOCLOCATION() {
		return APPSUPPORTDOCLOCATION;
	}

	/**
	 * @param appsupportdoclocation
	 *            the aPPSUPPORTDOCLOCATION to set
	 */
	public void setAPPSUPPORTDOCLOCATION(String appsupportdoclocation) {
		APPSUPPORTDOCLOCATION = appsupportdoclocation;
	}

	/**
	 * @return the vERIFYAPPROVALREMARK
	 */
	public String getVERIFYAPPROVALREMARK() {
		return VERIFYAPPROVALREMARK;
	}

	/**
	 * @param verifyapprovalremark
	 *            the vERIFYAPPROVALREMARK to set
	 */
	public void setVERIFYAPPROVALREMARK(String verifyapprovalremark) {
		VERIFYAPPROVALREMARK = verifyapprovalremark;
	}

	/**
	 * @return the iSSUEREQUESTSUBID
	 */
	public String getISSUEREQUESTSUBID() {
		return ISSUEREQUESTSUBID;
	}

	/**
	 * @param issuerequestsubid
	 *            the iSSUEREQUESTSUBID to set
	 */
	public void setISSUEREQUESTSUBID(String issuerequestsubid) {
		ISSUEREQUESTSUBID = issuerequestsubid;
	}

	/**
	 * @return the aPPROVALFINALSTATUS
	 */
	public String getAPPROVALFINALSTATUS() {
		return APPROVALFINALSTATUS;
	}

	/**
	 * @param approvalfinalstatus
	 *            the aPPROVALFINALSTATUS to set
	 */
	public void setAPPROVALFINALSTATUS(String approvalfinalstatus) {
		APPROVALFINALSTATUS = approvalfinalstatus;
	}

	/**
	 * @return the aPPROVERFINALREMARK1
	 */
	public String getAPPROVERFINALREMARK1() {
		return APPROVERFINALREMARK1;
	}

	/**
	 * @param approverfinalremark1
	 *            the aPPROVERFINALREMARK1 to set
	 */
	public void setAPPROVERFINALREMARK1(String approverfinalremark1) {
		APPROVERFINALREMARK1 = approverfinalremark1;
	}

	/**
	 * @return the aPPSUPPORTFINALSTATUS
	 */
	public String getAPPSUPPORTFINALSTATUS() {
		return APPSUPPORTFINALSTATUS;
	}

	/**
	 * @param appsupportfinalstatus
	 *            the aPPSUPPORTFINALSTATUS to set
	 */
	public void setAPPSUPPORTFINALSTATUS(String appsupportfinalstatus) {
		APPSUPPORTFINALSTATUS = appsupportfinalstatus;
	}

	public String getWI_PREV_PERFORMER() {
		return WI_PREV_PERFORMER;
	}

	public void setWI_PREV_PERFORMER(String wi_prev_performer) {
		WI_PREV_PERFORMER = wi_prev_performer;
	}

}
