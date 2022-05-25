package app.icm;

import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis.client.Service;

/**
 * Auto-generated
 */
public class UpdateProposalDetails {
	String endPointURL = "";

	public UpdateProposalDetails() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			if (ip.contains("10.62.182.42")) {
			//if (ip.contains("10.65.15.")) {
				/*
				 * New Service URL defined(for UAT) as given by Harshal Dhumane.
				 * By :- Sunil Jangir and Surabhi Kadam. Feb 06, 2020
				 */
				// endPointURL =
				// "http://rgiuicmdd.reliancegeneral.co.in:91/DiscrepancyCategory.svc?wsdl";
				endPointURL = "http://rgiuicmapp.reliancecapital.com:91/DiscrepancyCategory.svc?wsdl";
			} else {
				endPointURL = "http://icm.reliancegeneral.co.in:8081/DiscrepancyCategory.svc?wsdl";
			}

		} catch (Exception e) {
		}
	}

	public void updateDataInICM(String proposalId, String subCat, String approver, String RH_STATUS, String rh_Remarks) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String resolvedDt = sdf.format(now);

			String remark = RH_STATUS + "~" + rh_Remarks;

			int subCat_val = 0;
			if (subCat != null) {
				subCat_val = Integer.parseInt(subCat);
			}

			Service service = new Service();
			BasicHttpBinding_IDiscrepancyCategoryStub bs = new BasicHttpBinding_IDiscrepancyCategoryStub(new URL(endPointURL), service);
			// adding code to update raised remark in ICM
			// bs.raisedRemarkService(proposalId, approver);
			// code to update resolved remark in ICM
			bs.resolveDiscrepancySavvion(proposalId, remark, approver, resolvedDt, subCat_val);

		} catch (Exception ex) {
			throw new RuntimeException("Error while executing, execute method of app.icm.UpdateProposalDetails of " + "ICM_Discrepancy \n", ex);
		}
	}

	public void execute() {
		// do nothing
	}

	public void PAKcallerID(String processInstanceName, String workstepName, java.util.Properties bizLogicHost) {
	}

}