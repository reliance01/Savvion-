package com.commission;

import java.net.InetAddress;
import java.net.URL;
import com.rpas.*;

import org.apache.axis.client.Service;

public class UpdateDetails {
	
	//private String commWS_URL = "http://10.65.15.119:8081/Webservice/Policywise.asmx?wsdl";
	//private String rpasWS_URL="http://10.65.15.60/Savvion_RPAS_WS/RpasMakeLivePolicyService.asmx?wsdl";
	
	private String commWS_URL = "http://10.65.5.64:8081/Webservice/Policywise.asmx?wsdl";
	private String rpasWS_URL="http://10.65.5.143/Savvion_RPAS_WS/RpasMakeLivePolicyService.asmx?WSDL";
	
	public UpdateDetails()
	{
		try{
		String ip = InetAddress.getLocalHost().getHostAddress();
		if(ip.contains("10.65.15"))
		{
			commWS_URL = "http://10.65.15.119:8081/Webservice/Policywise.asmx?wsdl";
			rpasWS_URL = "http://10.65.15.60/Savvion_RPAS_WS/RpasMakeLivePolicyService.asmx?wsdl";			
		}
		}catch(Exception ex){}
	}
		
	public void invokeWS(String policyNo, String irda, String approver)
	{
		try{			
			PolicywiseSoap_BindingStub sb = new PolicywiseSoap_BindingStub(new URL(commWS_URL), new Service());
			sb.insertPolicywiseDeviation(policyNo, irda, approver);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public String[] invokeWSRPAS(String proposalNo, String ticketNo, String FinalStatus, String FinalRemark, String AddDeductiveStr)
	{
		try{
		RpasMakeLivePolicyServiceSoap12Stub _sb = new RpasMakeLivePolicyServiceSoap12Stub(new URL(rpasWS_URL), new Service());
		String arr[] = _sb.makePolicyLive(proposalNo, ticketNo, FinalStatus, FinalRemark, AddDeductiveStr);
		return arr;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
