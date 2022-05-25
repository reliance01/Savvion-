package com.rgicl.marcom.beans;

public class TicketInitialInfo {
	private String ticketNo;
	private String application;
	private String category;
	private String subCategory;
	private String uinNo;
	private long legalApprovalTime;
	private String product;
	private String businessUser;

	public String getBusinessUser() {
		return businessUser;
	}

	public void setBusinessUser(String businessUser) {
		this.businessUser = businessUser;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public long getLegalApprovalTime() {
		return legalApprovalTime;
	}

	public void setLegalApprovalTime(long legalApprovalTime) {
		this.legalApprovalTime = legalApprovalTime;
	}

	public String getUinNo() {
		return uinNo;
	}

	public void setUinNo(String uinNo) {
		this.uinNo = uinNo;
	}

	private long irdaDate;

	public long getIrdaDate() {
		return irdaDate;
	}

	public void setIrdaDate(long irdaDate) {
		this.irdaDate = irdaDate;
	}

	private long processTime;

	public long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(long processTime) {
		this.processTime = processTime;
	}

	private String status;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
