package ProductOrder;

import com.savvion.sbm.bpmportal.util.IHTML;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class Orders implements IHTML, Serializable {
   ArrayList arrays = null;
   private static final String NEW_LINE = "\n";

   public Orders() {
      this.init();
   }

   private void init() {
      Object[][] var1 = new Object[][]{{"1", "1", "204", "12-DEC-04"}, {"2", "1", "203", "11-DEC-04"}};
      this.arrays = new ArrayList();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         Object[] var10000 = var1[var2];
         ArrayList var4 = new ArrayList();

         for(int var5 = 0; var5 < var1[var2].length; ++var5) {
            var4.add(var1[var2][var5]);
         }

         this.arrays.add(var4);
      }

   }

   public String getPresentation(boolean var1) {
      StringBuffer var2 = new StringBuffer();
      StringBuffer var3 = new StringBuffer();
      new StringBuffer();
      if (this.arrays == null && this.arrays.size() == 0) {
         var2.append("</tr>\n").append("<td><br>There is no result to view!");
      } else {
         var3.append("<style type=\"text/css\">").append("\n");
         var3.append(" .ColHdr { ").append("\n");
         var3.append(" font-family: Verdana;font-size: 8pt;text-align: center;").append("\n");
         var3.append(" color: #000000;background-color: #F5F8F9;").append("\n");
         var3.append(" border-style: solid;").append("\n");
         var3.append(" border-top-width: 1px;border-top-color: #0061ad;").append("\n");
         var3.append(" border-bottom-width: 1px;border-bottom-color: #0061ad;").append("\n");
         var3.append(" border-left-width: 1px;border-left-color: #0061ad;").append("\n");
         var3.append(" border-right-width: 1px;border-right-color: #0061ad;").append("\n");
         var3.append(" } ").append("\n");
         var3.append(" .ValTlb { ").append("\n");
         var3.append(" font-family: Verdana;font-size: 8pt;text-align: left;").append("\n");
         var3.append(" background-color: #ffffff;").append("\n");
         var3.append(" border-style: solid;").append("\n");
         var3.append(" border-top-width: 0px;border-top-color: #0061ad;").append("\n");
         var3.append(" border-bottom-width: 1px;border-bottom-color: #0061ad;").append("\n");
         var3.append(" border-left-width: 1px;border-left-color: #0061ad;").append("\n");
         var3.append(" border-right-width: 1px;border-right-color: #0061ad;").append("\n");
         var3.append(" } ").append("\n");
         var3.append(" .TxtInpt { ").append("\n");
         var3.append(" font-family: Verdana, Arial, Helvetica, sans-serif; ").append("\n");
         var3.append(" font-size: 8pt;").append("\n");
         var3.append(" border-color: #BCC7CF #45617E #243242 #96A7B4;").append("\n");
         var3.append(" border-style: solid; ").append("\n");
         var3.append(" border-top-width: 1px;").append("\n");
         var3.append(" border-right-width: 1px;").append("\n");
         var3.append(" border-bottom-width: 1px;").append("\n");
         var3.append(" border-left-width: 1px;").append("\n");
         var3.append(" background-color: #FFFFFF").append("\n");
         var3.append(" } ").append("\n");
         var3.append(" .TdTlb { ").append("\n");
         var3.append(" background-color: #EAE6DB; border: 1px solid #A29379;").append("\n");
         var3.append(" padding-top: 4px; padding-right: 4px; padding-bottom: 4px;").append("\n");
         var3.append(" padding-left: 4px; color: #FFFFFF\"").append("\n");
         var3.append(" } ").append("\n");
         var3.append("</style>").append("\n");

         try {
            var2.append(var3.toString());
            var2.append("  <table width=\"100%\">").append("\n");
            var2.append("  <tr>").append("\n");
            var2.append("    <td height=\"10\"></td>").append("\n");
            var2.append("  </tr>").append("\n");
            var2.append("  <tr>").append("\n");
            var2.append("    <td class=\"TdTlb\"> ").append("\n");
            var2.append("      <table width=\"100%\" cellpadding=\"2\" align=\"center\" cellspacing=\"0\" border=\"0\" bgcolor=\"#F7F8F9\">").append("\n");
            var2.append("        <tr>").append("\n");
            var2.append("          <td>").append("\n");
            var2.append("              <div style=\"height: 100%; width: 100%; overflow: auto; background-color: #cccccc\">\n").append("\n");
            var2.append("              <table border=\"0\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">").append("\n");
            var2.append("                <tr>").append("\n");
            var2.append("                  <td class=\"ColHdr\">").append("ORDER_ID").append("</td>\n").append("\n");
            var2.append("                  <td class=\"ColHdr\">").append("CUSTOMER_ID").append("</td>\n").append("\n");
            var2.append("                  <td class=\"ColHdr\">").append("SERVICE_ID").append("</td>\n").append("\n");
            var2.append("                  <td class=\"ColHdr\">").append("ORDERDATE").append("</td>\n").append("\n");
            var2.append("                </tr>").append("\n");

            for(int var5 = 0; var5 < this.arrays.size(); ++var5) {
               ArrayList var6 = (ArrayList)this.arrays.get(var5);
               var2.append("                    <tr>").append("\n");

               for(int var7 = 0; var7 < var6.size(); ++var7) {
                  try {
                     Object var8 = var6.get(var7);
                     var2.append("                  <td class=\"ValTlb\">").append("\n");
                     if (var1) {
                        var2.append("<input type=\"text\" name=\"" + this.getFieldName(var5, var7) + "\" value=\"" + (var8 == null ? "" : var8.toString()) + "\" class=\"TxtInpt\" >");
                     } else {
                        var2.append(var8 != null ? var8.toString() : "&nbsp;");
                     }

                     var2.append("</td>\n");
                  } catch (Exception var9) {
                     var9.printStackTrace();
                  }
               }

               var2.append("                    </tr>").append("\n");
            }

            var2.append("                    </table>").append("\n");
            var2.append("                </div>").append("\n");
            var2.append("              </td>").append("\n");
            var2.append("              </tr>").append("\n");
            var2.append("         </table>").append("\n");
            var2.append("       </td>").append("\n");
            var2.append("      </tr>").append("\n");
            var2.append("  <tr id=\"doReqTable\">").append("\n");
            var2.append("    <td height=\"10\"></td>").append("\n");
            var2.append("  </tr>").append("\n");
            var2.append("  </table>").append("\n");
         } catch (Exception var10) {
            var10.printStackTrace();
            return "<tr><td>Error: " + var10.getMessage() + "</td></tr>";
         }
      }

      return var2.toString();
   }

   private String getFieldName(int var1, int var2) {
      return "orders_row_" + var1 + "_" + var2;
   }

   public Object doReqProcessing(Object var1) throws Exception {
      HttpServletRequest var2 = (HttpServletRequest)var1;
      Enumeration var3 = var2.getParameterNames();

      while(var3.hasMoreElements()) {
         String var4 = (String)var3.nextElement();
         if (var4.startsWith("orders_row_")) {
            try {
               String[] var5 = var4.split("_");
               String var6 = var2.getParameter(var4);
               int var7 = Integer.parseInt(var5[2]);
               int var8 = Integer.parseInt(var5[3]);
               this.setField(var7, var8, var6);
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      }

      return this;
   }

   private void setField(int var1, int var2, Object var3) {
      ArrayList var4 = (ArrayList)this.arrays.get(var1);
      var4.set(var2, var3);
      this.arrays.set(var1, var4);
   }

   public void addOrder(Order var1) {
      ArrayList var2 = new ArrayList();
      var2.add(var1.getOrderID() == null ? "" : var1.getOrderID());
      var2.add(var1.getCustomerID() == null ? "" : var1.getCustomerID());
      var2.add(var1.getServiceID() == null ? "" : var1.getServiceID());
      var2.add(var1.getOrderDate() == null ? "" : var1.getOrderDate());
      this.arrays.add(var2);
   }
}
