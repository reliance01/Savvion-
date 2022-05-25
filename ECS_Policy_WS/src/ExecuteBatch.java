import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.util.SBMHomeFactory;
import com.savvion.sbm.util.ServiceLocator;
import java.io.IOException;
import java.io.InputStream;
import org.apache.axis.AxisFault;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class ExecuteBatch {
   private static BizLogicManager pak = null;
   private static Byte[] bytearray = new Byte[0];

   private static BizLogicManager getBizLogicManager() throws AxisFault {
      if (pak == null) {
         synchronized(bytearray) {
            if (pak == null) {
               try {
                  String appserver = ServiceLocator.self().getAppServerID();
                  BizLogicManagerHome blmhome = (BizLogicManagerHome)SBMHomeFactory.self().lookupHome(appserver, "BizLogicManager", BizLogicManagerHome.class);
                  pak = blmhome.create();
               } catch (Throwable var3) {
                  System.out.println("ExecuteBatch.getBizLogicManager() Throwable " + var3);
                  throw new AxisFault("SBM Web services error :" + var3.getMessage());
               }
            }
         }
      }

      return pak;
   }

   private boolean checkConnection() {
      boolean b = false;

      try {
         getBizLogicManager().checkConnection();
         b = true;
      } catch (AxisFault var3) {
         System.out.println("ExecuteBatch.checkConnection() AxisFault " + var3.getMessage());
         b = false;
      } catch (Exception var4) {
         System.out.println("ExecuteBatch.checkConnection() Exception " + var4.getMessage());
         b = false;
      }

      return b;
   }

   private int checkSavvionConnection() {
      int i = 0;
      HttpClient httpclient = new HttpClient();
      GetMethod httpget = new GetMethod("http://10.65.15.246:18793/sbm/services/RGICL_CMS_WSCI?wsdl");

      try {
         try {
            i = httpclient.executeMethod(httpget);
            System.out.println("value of status code " + i);
         } catch (Exception var8) {
            System.out.println("checkSavvionConnection Exception " + var8);
            i = 0;
         }

         System.out.println("Status Code " + httpget.getStatusCode());
         System.out.println("Status Text " + httpget.getStatusText());
         System.out.println("Status Line " + httpget.getStatusLine());
      } finally {
         httpget.releaseConnection();
      }

      return i;
   }

   public void stopSavvionServices() {
      Runtime runtime = Runtime.getRuntime();

      try {
         Process p1 = runtime.exec("cmd.exe /c start C:\\Users\\Administrator\\Desktop\\StopSavvionServicesDEV.bat");
         InputStream is = p1.getInputStream();
         boolean var4 = false;

         int i;
         while((i = is.read()) != -1) {
            System.out.print((char)i);
         }
      } catch (IOException var5) {
         System.out.println("ExecuteBatch.stopSavvionServices() IOException " + var5);
      }

   }

   public void startSavvionServices() {
      Runtime runtime = Runtime.getRuntime();

      try {
         Process p1 = runtime.exec("cmd.exe /c start C:\\Users\\Administrator\\Desktop\\StartSavvionServicesDEV.bat");
         InputStream is = p1.getInputStream();
         boolean var4 = false;

         int i;
         while((i = is.read()) != -1) {
            System.out.print((char)i);
         }
      } catch (IOException var5) {
         System.out.println("ExecuteBatch.startSavvionServices() IOException " + var5);
      }

   }

   public void excecuteScheduler() {
      int b = this.checkSavvionConnection();
      System.out.println("ExecuteBatch.excecuteScheduler() checkConnection is " + b);

      try {
         if (b == 200) {
            System.out.println("Savvion is Up");
         } else {
            while(b != 200) {
               System.out.println("In While loop");
               System.out.println("Savvion is Down");
               this.stopSavvionServices();
               Thread.sleep(600000L);
               this.startSavvionServices();
               Thread.sleep(120000L);
               b = 200;
            }
         }
      } catch (Exception var3) {
         System.out.println("ExecuteBatch.excecuteScheduler() Exception " + var3);
      }

   }

   public static void main(String[] args) {
      System.out.println("Welcome in Main");
      ExecuteBatch batch = new ExecuteBatch();
      batch.excecuteScheduler();
   }
}
