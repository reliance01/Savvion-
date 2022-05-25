package com.rgicl.aureaprocess.techdesk.smsservice;


import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.rgicl.savvion.TechDesk.ApproverUtil;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.*;
import com.savvion.sbm.bizlogic.util.*;
import com.savvion.sbm.util.*;

import bitly.services.SendBitly;

import com.savvion.sbm.bizlogic.server.svo.DocumentDS;

/**
 * Auto-generated
 */
public class SendApprovalSMS {
	
	ApproverUtil appUtil = new ApproverUtil();
	SendBitly smsBitly = new SendBitly();
	
	public void sendApprovalSMS() {
		System.out.println("SendApprovalSMS::sendApprovalSMS()::Ticket No: "+ticketNo);
		try{
			smsBitly.sendBitlySMS(ticketNo, "Approver");
		}catch(Exception ex){
			System.out.println("Exception in SendApprovalSMS:::: "+ex.getMessage());
		}
	}


	private String ticketNo;

	public String getTicketNo() {
		return this.ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public void PAKcallerID(String processInstanceName, String workstepName, java.util.Properties bizLogicHost) {
	}
}