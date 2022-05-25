package com.rel.dao;

import com.rel.db.DBUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class AuditParamDAO {
   public ArrayList getParameterCount() {
      ArrayList ls = new ArrayList();
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DBUtil db = new DBUtil();
      String sql = "SELECT parameter from TBL_AUDIT_PARAMETER ORDER BY ID";

      ArrayList var11;
      try {
         conn = db.getConnection();
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery(sql);
         long i = 1L;
         if (rs != null) {
            while(rs.next()) {
               LinkedHashMap hm = new LinkedHashMap();
               hm.put("ID", new Long(i));
               hm.put("PARAMETER", rs.getString("parameter"));
               ls.add(hm);
               ++i;
            }
         }

         var11 = ls;
      } catch (Exception var30) {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var29) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var28) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var27) {
         }

         throw new RuntimeException(var30);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var26) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var25) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var24) {
         }

      }

      return var11;
   }

   public ArrayList getParameters(long pid, int id) {
      ArrayList ls = new ArrayList();
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DBUtil db = new DBUtil();
      String sql = "SELECT ID, SUB_ID, PARAMETER, TYPE, CATEGORY, RED, AMBER, GREEN, VALUE, WEIGHTAGE_VALUE, TOTAL_PERCENTAGE FROM TBL_AUDIT_DETAIL WHERE PROCESS_INSTANCE_ID = ? AND ID =? ORDER BY ID, SUB_ID";

      ArrayList var13;
      try {
         conn = db.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setLong(1, pid);
         pstmt.setInt(2, id);
         rs = pstmt.executeQuery();
         if (rs != null) {
            while(rs.next()) {
               LinkedHashMap hm = new LinkedHashMap();
               hm.put("ID", new Long((long)rs.getInt("ID")));
               hm.put("SUB_ID", new Long((long)rs.getInt("SUB_ID")));
               hm.put("PARAMETER", rs.getString("parameter"));
               hm.put("TYPE", rs.getString("type"));
               hm.put("CATEGORY", rs.getString("category"));
               hm.put("RED", rs.getString("red"));
               hm.put("AMBER", rs.getString("amber"));
               hm.put("GREEN", rs.getString("green"));
               hm.put("VALUE", new Long((long)rs.getInt("value")));
               hm.put("WEIGHTAGE_VALUE", rs.getBigDecimal("weightage_value"));
               hm.put("TOTAL_PERCENTAGE", rs.getBigDecimal("total_percentage"));
               ls.add(hm);
            }
         }

         var13 = ls;
      } catch (Exception var32) {
         var32.printStackTrace();

         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var31) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var30) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var29) {
         }

         throw new RuntimeException(var32);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var28) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var27) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var26) {
         }

      }

      return var13;
   }

   public HashMap getEquivalentScoreByPiid(long piid) {
      HashMap hm = new HashMap();
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DBUtil db = new DBUtil();
      String sql = "SELECT count(*), round(sum(nvl(WEIGHTAGE_VALUE,0)),2), round(sum(nvl(WEIGHTAGE_VALUE,0))*100/sum(total_percentage),2), avg(nvl(fraud,0)) FROM TBL_AUDIT_DETAIL WHERE VALUE > 0 AND PROCESS_INSTANCE_ID=?";

      try {
         conn = db.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setLong(1, piid);

         double tot;
         for(rs = pstmt.executeQuery(); rs.next(); hm.put("TOTAL", tot)) {
            hm.put("Count", rs.getLong(1));
            hm.put("WGT_SCORE", rs.getBigDecimal(2));
            hm.put("EQ_SCORE", rs.getBigDecimal(3));
            hm.put("FRAUD", rs.getBigDecimal(4));
            tot = 0.0D;
            if (rs.getBigDecimal(3) != null) {
               tot = rs.getBigDecimal(3).doubleValue();
            }

            if (rs.getBigDecimal(4) != null) {
               tot += rs.getBigDecimal(4).doubleValue();
            }
         }
      } catch (Exception var30) {
         var30.printStackTrace();

         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var29) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var28) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var27) {
         }

         throw new RuntimeException(var30);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var26) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var25) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var24) {
         }

      }

      return hm;
   }

   public ArrayList getEquivalentScoreByBranch(String stDate, String endDate) {
      ArrayList lst = new ArrayList();
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DBUtil db = new DBUtil();
      String sql = "SELECT DISTINCT BRANCH, count(*)/count(DISTINCT t.process_instance_id), round(sum(WEIGHTAGE_VALUE)/count(DISTINCT t.process_instance_id),2), round((sum(WEIGHTAGE_VALUE)/count(DISTINCT t.process_instance_id))*100/(sum(total_percentage)/count(DISTINCT t.process_instance_id)),2), round(sum(nvl(fraud,0))/(count(*)*count(DISTINCT t.process_instance_id)),2) AS fraud, (round((sum(WEIGHTAGE_VALUE)/count(DISTINCT t.process_instance_id))*100/(sum(total_percentage)/count(DISTINCT t.process_instance_id)),3) + round(sum(nvl(fraud,0))/(count(*)*count(DISTINCT t.process_instance_id)),2)) FROM TBL_AUDIT_DETAIL t, processinstance p WHERE t.PROCESS_INSTANCE_ID=p.PROCESS_INSTANCE_ID AND p.PROCESS_TEMPLATE_ID IN (SELECT process_template_id FROM PROCESSTEMPLATE WHERE PROCESS_TEMPLATE_NAME='TechnicalAudit') AND p.START_TIME >= to_date('" + stDate + "','dd-mm-yyyy') AND p.START_TIME <= to_date('" + endDate + "','dd-mm-yyyy') AND VALUE > 0 GROUP BY BRANCH ORDER BY 6 ASC";

      try {
         conn = db.getConnection();
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();

         while(rs.next()) {
            LinkedHashMap hm1 = new LinkedHashMap();
            hm1.put("Branch", rs.getString(1));
            hm1.put("Count", rs.getBigDecimal(2));
            hm1.put("WGT_SCORE", rs.getBigDecimal(3));
            hm1.put("EQ_SCORE", rs.getBigDecimal(4));
            hm1.put("FRAUD", rs.getBigDecimal(5));
            hm1.put("TOT_SCORE", rs.getBigDecimal(6));
            lst.add(hm1);
         }
      } catch (Exception var30) {
         var30.printStackTrace();

         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var29) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var28) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var27) {
         }

         throw new RuntimeException(var30);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var26) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var25) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var24) {
         }

      }

      return lst;
   }

   public boolean updateParameters(long pid, LinkedHashMap hm) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      DBUtil db = new DBUtil();
      boolean bln = false;
      String sql = "UPDATE TBL_AUDIT_DETAIL SET VALUE=? WHERE PROCESS_INSTANCE_ID = ? AND ID =? AND SUB_ID=?";
      String sql1 = "UPDATE TBL_AUDIT_DETAIL SET WEIGHTAGE_VALUE=(Value*total_percentage)/100 WHERE PROCESS_INSTANCE_ID=?";

      try {
         conn = db.getConnection();
         pstmt = conn.prepareStatement(sql);
         Set set = hm.entrySet();
         Iterator it = set.iterator();

         while(it.hasNext()) {
            Entry m = (Entry)it.next();
            String key = (String)m.getKey();
            String value = (String)m.getValue();
            String[] spl = key.split("\\.");
            pstmt.setInt(1, Integer.parseInt(value));
            pstmt.setLong(2, pid);
            pstmt.setInt(3, Integer.parseInt(spl[0]));
            pstmt.setInt(4, Integer.parseInt(spl[1]));
            pstmt.addBatch();
         }

         int[] cnt = pstmt.executeBatch();
         pstmt = conn.prepareStatement(sql1);
         pstmt.setLong(1, pid);
         pstmt.executeUpdate();
         bln = true;
         boolean var18 = bln;
         return var18;
      } catch (Exception var37) {
         var37.printStackTrace();

         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var36) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var35) {
         }

         try {
            if (rs != null) {
               ((ResultSet)rs).close();
            }
         } catch (Exception var34) {
         }

         throw new RuntimeException(var37);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var33) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var32) {
         }

         try {
            if (rs != null) {
               ((ResultSet)rs).close();
            }
         } catch (Exception var31) {
         }

      }
   }

   public void insertParameters(long piid, String branch, String region) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      PreparedStatement pstmt1 = null;
      ResultSet rs = null;
      DBUtil db = new DBUtil();

      try {
         if (piid > 0L && branch != null && region != null) {
            String sql = "SELECT ID, SUB_ID, PARAMETER, TYPE, CATEGORY, RED, AMBER, GREEN, VALUE, WEIGHTAGE_VALUE, TOTAL_PERCENTAGE FROM TBL_AUDIT_SUB_PARAMETER ORDER BY ID, SUB_ID";
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs != null) {
               while(rs.next()) {
                  int id = rs.getInt("ID");
                  int sub_id = rs.getInt("SUB_ID");
                  String parameter = rs.getString("PARAMETER");
                  String type = rs.getString("TYPE");
                  String category = rs.getString("CATEGORY");
                  String red = rs.getString("RED");
                  String amber = rs.getString("AMBER");
                  String green = rs.getString("GREEN");
                  BigDecimal totpr = rs.getBigDecimal("TOTAL_PERCENTAGE");
                  String sql_ins = "insert into TBL_AUDIT_DETAIL values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                  try {
                     pstmt1 = conn.prepareStatement(sql_ins);
                     pstmt1.setLong(1, piid);
                     pstmt1.setInt(2, id);
                     pstmt1.setInt(3, sub_id);
                     pstmt1.setString(4, parameter);
                     pstmt1.setString(5, type);
                     pstmt1.setString(6, category);
                     pstmt1.setString(7, red);
                     pstmt1.setString(8, amber);
                     pstmt1.setString(9, green);
                     pstmt1.setInt(10, new Integer(-1));
                     pstmt1.setBigDecimal(11, new BigDecimal(0));
                     pstmt1.setBigDecimal(12, totpr);
                     pstmt1.setString(13, branch);
                     pstmt1.setString(14, region);
                     pstmt1.setInt(15, new Integer(0));
                     pstmt1.executeUpdate();
                  } catch (Exception var58) {
                     try {
                        pstmt1 = conn.prepareStatement("delete from TBL_AUDIT_DETAIL where process_instance_id=" + piid);
                        pstmt1.executeUpdate();
                        System.out.println("Error hence deleteing records;");
                     } catch (Exception var57) {
                        var57.printStackTrace();
                     }

                     try {
                        if (conn != null) {
                           db.disConnect(conn);
                        }
                     } catch (Exception var56) {
                        var56.printStackTrace();
                     }

                     try {
                        if (pstmt1 != null) {
                           pstmt1.close();
                        }
                     } catch (Exception var55) {
                     }

                     try {
                        if (pstmt != null) {
                           pstmt.close();
                        }
                     } catch (Exception var54) {
                     }

                     try {
                        if (rs != null) {
                           rs.close();
                        }
                     } catch (Exception var53) {
                     }

                     throw new RuntimeException(var58);
                  }
               }
            }
         }
      } catch (Exception var59) {
         var59.printStackTrace();

         try {
            conn.rollback();
         } catch (Exception var52) {
         }

         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var51) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var50) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var49) {
         }

         throw new RuntimeException(var59);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var48) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var47) {
         }

         try {
            if (pstmt1 != null) {
               pstmt1.close();
            }
         } catch (Exception var46) {
         }

         try {
            if (rs != null) {
               rs.close();
            }
         } catch (Exception var45) {
         }

      }

   }

   public boolean updateFValue(long piid, String fraud) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      PreparedStatement pstmt1 = null;
      DBUtil db = new DBUtil();
      boolean bln = false;

      boolean var12;
      try {
         System.out.println("Executing updateFValue");
         if (piid > 0L && fraud != null) {
            String sql = "UPDATE TBL_AUDIT_DETAIL set fraud=? where process_instance_id=?";
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setBigDecimal(1, new BigDecimal(fraud));
            pstmt.setLong(2, piid);
            pstmt.executeUpdate();
            bln = true;
            System.out.println("Executed updateFValue" + bln);
         }

         var12 = bln;
      } catch (Exception var31) {
         System.out.println("Err" + var31.getMessage());
         var31.printStackTrace();

         try {
            conn.rollback();
         } catch (Exception var30) {
         }

         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var29) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var28) {
         }

         throw new RuntimeException(var31);
      } finally {
         try {
            if (conn != null) {
               db.disConnect(conn);
            }
         } catch (Exception var27) {
         }

         try {
            if (pstmt != null) {
               pstmt.close();
            }
         } catch (Exception var26) {
         }

         try {
            if (pstmt1 != null) {
               ((PreparedStatement)pstmt1).close();
            }
         } catch (Exception var25) {
         }

      }

      return var12;
   }
}
