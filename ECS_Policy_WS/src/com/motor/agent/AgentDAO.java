package com.motor.agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AgentDAO {
   private Connection conn = null;
   private PreparedStatement pstmt = null;
   private Statement stmt = null;
   private ResultSet rs = null;
   private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";
   private DataSource ds = null;

   public String getRegionName(String _branchName) {
      String qry = "select distinct region_code from IMD_IN_SAVVION where branch = ?";
      String regionName = new String();

      try {
         if (_branchName != null) {
            this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
            this.conn = this.ds.getConnection();
            this.pstmt = this.conn.prepareStatement(qry);
            this.pstmt.setString(1, _branchName);

            for(this.rs = this.pstmt.executeQuery(); this.rs.next(); regionName = this.rs.getString("region_code")) {
            }
         }
      } catch (Exception var18) {
         throw new RuntimeException(var18);
      } finally {
         if (this.pstmt != null) {
            try {
               this.pstmt.close();
            } catch (SQLException var17) {
            }
         }

         if (this.rs != null) {
            try {
               this.rs.close();
            } catch (SQLException var16) {
            }
         }

         if (this.conn != null) {
            try {
               this.conn.close();
            } catch (SQLException var15) {
            }
         }

      }

      return regionName;
   }

   public HashMap getAgentRegionData(HashMap agentDetails) {
      HashMap j = new HashMap();
      if (agentDetails != null && !agentDetails.isEmpty()) {
         try {
            if (agentDetails.get("AGENT_CODE").toString() != null && agentDetails.get("BAS_CODE").toString() != null && agentDetails.get("BRANCH_CODE").toString() != null && agentDetails.get("REGION_CODE").toString() != null) {
               this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
               this.conn = this.ds.getConnection();
               String qry = null;
               if (agentDetails.get("BAS_CODE").toString().contains("DIRECT")) {
                  qry = "select round(SUM(GWP),2) as GWP, round(((sum(claims_incurred)/sum(earned_prem_amt))*100),2) as LossRatio, round((-100)*(sum(OD_DTDASUM)/sum(BASICODRATE)),2) as AVG_OD_DISCOUNT from IMD_IN_SAVVION where agent_code like '%" + agentDetails.get("AGENT_CODE").toString() + "%' and branch ='" + agentDetails.get("BRANCH_CODE").toString() + "' and region_code = '" + agentDetails.get("REGION_CODE").toString() + "'";
               } else {
                  qry = "select round(SUM(GWP),2) as GWP, round(((sum(claims_incurred)/sum(earned_prem_amt))*100),2) as LossRatio, round((-100)*(sum(OD_DTDASUM)/sum(BASICODRATE)),2) as AVG_OD_DISCOUNT from IMD_IN_SAVVION where bas_code like '%" + agentDetails.get("BAS_CODE").toString() + "%' and branch ='" + agentDetails.get("BRANCH_CODE").toString() + "' and region_code = '" + agentDetails.get("REGION_CODE").toString() + "'";
               }

               this.stmt = this.conn.createStatement();

               String avg_od_discount;
               for(this.rs = this.stmt.executeQuery(qry); this.rs.next(); j.put("AVG_OD_DISCOUNT", avg_od_discount)) {
                  String gwp = this.rs.getString("GWP");
                  if (gwp == null) {
                     gwp = "N.A";
                  }

                  j.put("GWP", gwp);
                  String loss_ratio = this.rs.getString("LossRatio");
                  if (loss_ratio == null) {
                     loss_ratio = "N.A";
                  }

                  j.put("LOSS_RATIO", loss_ratio);
                  avg_od_discount = this.rs.getString("AVG_OD_DISCOUNT");
                  if (avg_od_discount == null) {
                     avg_od_discount = "N.A";
                  }
               }
            }
         } catch (Exception var20) {
            throw new RuntimeException(var20);
         } finally {
            if (this.stmt != null) {
               try {
                  this.stmt.close();
               } catch (SQLException var19) {
               }
            }

            if (this.rs != null) {
               try {
                  this.rs.close();
               } catch (SQLException var18) {
               }
            }

            if (this.conn != null) {
               try {
                  this.conn.close();
               } catch (SQLException var17) {
               }
            }

         }
      }

      return j;
   }

   public HashMap getAgentBranchData(HashMap agentDetails) {
      HashMap j = new HashMap();
      if (agentDetails != null && !agentDetails.isEmpty()) {
         try {
            if (agentDetails.get("AGENT_CODE").toString() != null && agentDetails.get("BAS_CODE").toString() != null && agentDetails.get("BRANCH_CODE").toString() != null) {
               this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
               this.conn = this.ds.getConnection();
               String qry = null;
               if (agentDetails.get("BAS_CODE").toString().contains("DIRECT")) {
                  qry = "select round(SUM(GWP),2) as GWP, round(((sum(claims_incurred)/sum(earned_prem_amt))*100),2) as LossRatio, round((-100)*(sum(OD_DTDASUM)/sum(BASICODRATE)),2) as AVG_OD_DISCOUNT from IMD_IN_SAVVION where agent_code like '%" + agentDetails.get("AGENT_CODE").toString() + "%' and branch ='" + agentDetails.get("BRANCH_CODE").toString() + "'";
               } else {
                  qry = "select round(SUM(GWP),2) as GWP, round(((sum(claims_incurred)/sum(earned_prem_amt))*100),2) as LossRatio, round((-100)*(sum(OD_DTDASUM)/sum(BASICODRATE)),2) as AVG_OD_DISCOUNT from IMD_IN_SAVVION where bas_code like '%" + agentDetails.get("BAS_CODE").toString() + "%' and branch ='" + agentDetails.get("BRANCH_CODE").toString() + "'";
               }

               this.stmt = this.conn.createStatement();

               String avg_od_discount;
               for(this.rs = this.stmt.executeQuery(qry); this.rs.next(); j.put("AVG_OD_DISCOUNT", avg_od_discount)) {
                  String gwp = this.rs.getString("GWP");
                  if (gwp == null) {
                     gwp = "N.A";
                  }

                  j.put("GWP", gwp);
                  String loss_ratio = this.rs.getString("LossRatio");
                  if (loss_ratio == null) {
                     loss_ratio = "N.A";
                  }

                  j.put("LOSS_RATIO", loss_ratio);
                  avg_od_discount = this.rs.getString("AVG_OD_DISCOUNT");
                  if (avg_od_discount == null) {
                     avg_od_discount = "N.A";
                  }
               }
            }
         } catch (Exception var20) {
            throw new RuntimeException(var20);
         } finally {
            if (this.stmt != null) {
               try {
                  this.stmt.close();
               } catch (SQLException var19) {
               }
            }

            if (this.rs != null) {
               try {
                  this.rs.close();
               } catch (SQLException var18) {
               }
            }

            if (this.conn != null) {
               try {
                  this.conn.close();
               } catch (SQLException var17) {
               }
            }

         }
      }

      return j;
   }

   public HashMap getAgentAvgData(HashMap agentDetails) {
      HashMap j = new HashMap();
      if (agentDetails != null && !agentDetails.isEmpty()) {
         try {
            if (agentDetails.get("AGENT_CODE").toString() != null && agentDetails.get("BAS_CODE").toString() != null) {
               this.ds = (DataSource)(new InitialContext()).lookup("jdbc/SBMCommonDB");
               this.conn = this.ds.getConnection();
               String qry = null;
               if (agentDetails.get("BAS_CODE").toString().contains("DIRECT")) {
                  qry = "select round(SUM(GWP),2) as GWP, round(((sum(claims_incurred)/sum(earned_prem_amt))*100),2) as LossRatio, round((-100)*(sum(OD_DTDASUM)/sum(BASICODRATE)),2) as AVG_OD_DISCOUNT from IMD_IN_SAVVION where agent_code like '%" + agentDetails.get("AGENT_CODE").toString() + "%'";
               } else {
                  qry = "select round(SUM(GWP),2) as GWP, round(((sum(claims_incurred)/sum(earned_prem_amt))*100),2) as LossRatio, round((-100)*(sum(OD_DTDASUM)/sum(BASICODRATE)),2) as AVG_OD_DISCOUNT from IMD_IN_SAVVION where bas_code like '%" + agentDetails.get("BAS_CODE").toString() + "%'";
               }

               this.stmt = this.conn.createStatement();

               String avg_od_discount;
               for(this.rs = this.stmt.executeQuery(qry); this.rs.next(); j.put("AVG_OD_DISCOUNT", avg_od_discount)) {
                  String gwp = this.rs.getString("GWP");
                  if (gwp == null) {
                     gwp = "N.A";
                  }

                  j.put("GWP", gwp);
                  String loss_ratio = this.rs.getString("LossRatio");
                  if (loss_ratio == null) {
                     loss_ratio = "N.A";
                  }

                  j.put("LOSS_RATIO", loss_ratio);
                  avg_od_discount = this.rs.getString("AVG_OD_DISCOUNT");
                  if (avg_od_discount == null) {
                     avg_od_discount = "N.A";
                  }
               }
            }
         } catch (Exception var20) {
            throw new RuntimeException(var20);
         } finally {
            if (this.stmt != null) {
               try {
                  this.stmt.close();
               } catch (SQLException var19) {
               }
            }

            if (this.rs != null) {
               try {
                  this.rs.close();
               } catch (SQLException var18) {
               }
            }

            if (this.conn != null) {
               try {
                  this.conn.close();
               } catch (SQLException var17) {
               }
            }

         }
      }

      return j;
   }
}
