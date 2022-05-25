package app.icm;

import java.net.URL;
import java.util.Properties;
import org.apache.axis.client.Service;

public class UpdateProposalDetails {
   private long piid = 0L;
   private String endPointURL = "http://10.65.15.62:91/DiscrepancyCategory.svc?wsdl";
   private String operationName = "ResolveDiscrepancySavvion";

   public void execute() {
      try {
         String RH_STATUS = "Approved";
         String proposalId = new String("P12018100007");
         String approver = new String("parshu");
         String resolvedDt = new String("2009-05-30");
         System.out.println(resolvedDt);
         String subCat = "65";
         Integer subCat_val = new Integer(subCat);
         if (RH_STATUS.equalsIgnoreCase("APPROVED")) {
            Service service = new Service();
            BasicHttpBinding_IDiscrepancyCategoryStub bs = new BasicHttpBinding_IDiscrepancyCategoryStub(new URL(this.endPointURL), service);
            String ret = bs.resolveDiscrepancySavvion(proposalId, new String("Closed"), approver, resolvedDt, subCat_val);
            System.out.println("Return value is : " + ret.toString());
         }

      } catch (Exception var10) {
         throw new RuntimeException("Error while executing, execute method of app.icm.UpdateProposalDetails of ICM_Discrepancy \n", var10);
      }
   }

   public void PAKcallerID(String processInstanceName, String workstepName, Properties bizLogicHost) {
   }

   public static void main(String[] args) {
      UpdateProposalDetails upd = new UpdateProposalDetails();
      upd.execute();
   }
}
