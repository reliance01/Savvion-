package com.rel.dao;

import com.savvion.common.databases.DatabaseManager;
import com.savvion.mom.Service1Soap_BindingStub;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.axis.client.Service;

public class OperationsAuditDataUtil_New {
   private String wsdlUrl = "";
   private static final String SBM_DB_JNDI = "jdbc/SBMCommonDB";
   final String SBM_HOME = System.getProperty("sbm.home");
   final String WebService_Prop;

   public OperationsAuditDataUtil_New() {
      this.WebService_Prop = this.SBM_HOME + "/conf/AuditWebService.properties";

      try {
         String ip = InetAddress.getLocalHost().getHostAddress();
         System.out.println("IP Address is $ " + ip);
         Properties properties = new Properties();
         properties.load(new FileInputStream(this.WebService_Prop));
         if (ip.contains("10.65.15.")) {
            this.wsdlUrl = properties.getProperty("UAT_SERVICE_ENDPOINT");
         } else {
            this.wsdlUrl = properties.getProperty("PROD_SERVICE_ENDPOINT");
         }

      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
      try {
         if (rs != null) {
            rs.close();
            rs = null;
         }

         if (pstmt != null) {
            pstmt.close();
            pstmt = null;
         }

         if (conn != null) {
            conn.close();
            conn = null;
         }

      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public Connection getConnection(String jndi) {
      Connection conn = null;

      try {
         if (jndi != null) {
            conn = DatabaseManager.getConnection(jndi);
         }

         return conn;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public String[] getALLRegions() {
      try {
         Service1Soap_BindingStub sb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
         String[] arr = sb.getRegions();
         return arr;
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public List<String> getBranches(String region, String branchType) {
      try {
         List<String> branches = null;
         if (region != null && region.length() > 1) {
            Service1Soap_BindingStub sb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
            String[] arr = sb.getRegionBranchs(region, branchType);
            branches = new ArrayList(Arrays.asList(arr));
         }

         return branches;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public String getZonalHead(String region) {
      try {
         String zh = "";
         if (region != null && region.length() > 1) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
               conn = this.getConnection("jdbc/SBMCommonDB");
               pstmt = conn.prepareStatement("select employee_name from zom_panel where region_name=?");
               pstmt.setString(1, region);

               for(rs = pstmt.executeQuery(); rs.next(); zh = rs.getString("employee_name")) {
               }
            } catch (SQLException var11) {
            } finally {
               this.closeResources(rs, pstmt, conn);
            }
         }

         return zh;
      } catch (Exception var13) {
         throw new RuntimeException(var13);
      }
   }

   public String getEmailId(String empCode) {
      try {
         String email = "";
         if (empCode != null && empCode.length() > 1) {
            Service1Soap_BindingStub sb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
            email = sb.getEmployeeEmailId(empCode);
         }

         return email;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public String getContactNo(String empCode) {
      try {
         String contactNo = "";
         if (empCode != null && empCode.length() > 1) {
            Service1Soap_BindingStub sb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
            contactNo = sb.getEmployeeContactNo(empCode);
         }

         return contactNo;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public List<String> getAuditorList(String regionName) {
      try {
         List<String> adList = new ArrayList();
         if (regionName != null && regionName.length() > 1) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
               conn = this.getConnection("jdbc/SBMCommonDB");
               pstmt = conn.prepareStatement("select employee_name from auditor_panel where region_name = ?");
               pstmt.setString(1, regionName);
               rs = pstmt.executeQuery();

               while(rs.next()) {
                  adList.add(rs.getString("employee_name"));
               }
            } catch (SQLException var11) {
            } finally {
               this.closeResources(rs, pstmt, conn);
            }
         }

         return adList;
      } catch (Exception var13) {
         throw new RuntimeException(var13);
      }
   }

   public Map getBranchHead(String branch) {
      try {
         Map hm = new HashMap();
         List<String> bm = new ArrayList();
         if (branch != null && branch.length() > 1) {
            Service1Soap_BindingStub sb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
            String region = sb.getRegionFromBranch(branch);
            System.out.println("$ region name is $" + region);
            if (region != null && region != "") {
               Connection conn = null;
               PreparedStatement pstmt = null;
               ResultSet rs = null;

               try {
                  conn = this.getConnection("jdbc/SBMCommonDB");
                  pstmt = conn.prepareStatement("select employee_name from rom_panel where region_name=?");
                  pstmt.setString(1, region);
                  rs = pstmt.executeQuery();

                  while(rs.next()) {
                     bm.add(rs.getString("employee_name"));
                  }
               } catch (SQLException var14) {
               } finally {
                  this.closeResources(rs, pstmt, conn);
               }
            }
         }

         if (bm != null) {
            hm.put("BM", bm);
         }

         List bsm = null;
         if (branch != null && branch.length() > 1) {
            Service1Soap_BindingStub sb = new Service1Soap_BindingStub(new URL(this.wsdlUrl), new Service());
            String[] arr = sb.getBSMDetails(branch);
            bsm = new ArrayList(Arrays.asList(arr));
         }

         if (bsm != null) {
            hm.put("BSM", bsm);
         }

         return hm;
      } catch (Exception var16) {
         throw new RuntimeException(var16);
      }
   }
}
