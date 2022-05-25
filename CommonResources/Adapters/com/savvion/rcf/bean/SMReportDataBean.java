package com.savvion.rcf.bean;

import java.sql.Date;

public class SMReportDataBean {
	private String inwardDate;
	private String region;
	private String branch;
	private String proposalid;
	private String premium;
	private String AgentName;
	private String AgentMobileNo;
	private String RMRName;
	private String SMName;
	private String CSOname;
	private String vertical;
	private String disCrepantDate;
	private String aging;
	private String pendingWith;
	private String status;
	//private String startDate;
	public String getAgentMobileNo() {
		return AgentMobileNo;
	}
	public void setAgentMobileNo(String agentMobileNo) {
		AgentMobileNo = agentMobileNo;
	}
	public String getAgentName() {
		return AgentName;
	}
	public void setAgentName(String agentName) {
		AgentName = agentName;
	}
	public String getAging() {
		return aging;
	}
	public void setAging(String aging) {
		this.aging = aging;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCSOname() {
		return CSOname;
	}
	
	
	public void setCSOname(String oname) {
		CSOname = oname;
	}
	public String getDisCrepantDate() {
		return disCrepantDate;
	}
	public void setDisCrepantDate(String disCrepantDate) {
		this.disCrepantDate = disCrepantDate;
	}
	public String getInwardDate() {
		return inwardDate;
	}
	public void setInwardDate(String inwardDate) {
		this.inwardDate = inwardDate;
	}
	public String getPendingWith() {
		return pendingWith;
	}
	public void setPendingWith(String pendingWith) {
		this.pendingWith = pendingWith;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getProposalid() {
		return proposalid;
	}
	public void setProposalid(String proposalid) {
		this.proposalid = proposalid;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRMRName() {
		return RMRName;
	}
	public void setRMRName(String name) {
		RMRName = name;
	}
	public String getSMName() {
		return SMName;
	}

	public void setSMName(String name) {
		SMName = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	
}
