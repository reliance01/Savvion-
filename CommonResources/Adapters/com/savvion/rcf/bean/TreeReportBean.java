package com.savvion.rcf.bean;

import java.util.List;

public class TreeReportBean {

	private String SM = "";
	private String BM = "";
	private String zoneName = "";
	private String regionName = "";
	private String branchName = "";
	
	public String getBM() {
		return BM;
	}
	public void setBM(String bm) {
		BM = bm;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getSM() {
		return SM;
	}
	public void setSM(String sm) {
		SM = sm;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	
	
}
