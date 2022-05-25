package com.rgicl.marcom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tdiinc.userManager.AdvanceGroup;
import com.tdiinc.userManager.UserManager;

import oracle.jdbc.OracleTypes;

public class BasicUtility {
	public static String getFormatedDate(long time) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		return fmt.format(new Date(time));
	}

	public static String getNameOfUser(String userName) {
		Connection connection = null;
		CallableStatement cstmt = null;
		ResultSet rset = null;
		String qry = "{?=call getUserName(?)}";
		String name = null;
		try {
			if (userName != null && userName.trim().length() > 0) {
				connection = DBUtility.getDBConnection();
				cstmt = connection.prepareCall(qry);
				cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
				cstmt.setString(2, userName.trim());
				cstmt.execute();
				name = cstmt.getString(1);
			}
		} catch (Exception ex) {
			System.out.println("Error in getNameOfUser : " + ex.getMessage());
		} finally {
			DBUtility.releaseResouce(rset, cstmt, connection);
		}
		return name;
	}

	public static String getUserGrpName(String userName) {
		String grpName = "NoGrpMember";
		String[] srchGrpName = { "MarcomGrp", "OnlineSalesTeamGrp" };
		try {
			for (int i = 0; i < srchGrpName.length; i++) {
				String[] grpMembers = ((AdvanceGroup) UserManager
						.getGroup(srchGrpName[i])).getAllUserMemberNames();
				for (int j = 0; j < grpMembers.length; j++) {
					if (i == 0) {
						if (grpMembers[j].equalsIgnoreCase(userName.trim())) {
							grpName = "MarcomGrpMember";
							break;
						}
					}
					if (i == 1) {
						if (grpMembers[j].equalsIgnoreCase(userName.trim())) {
							grpName = "OnlineGrpMember";
							break;
						}
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error in getUserGrpName : " + ex.getMessage());
			throw new RuntimeException(ex);
		}
		return grpName;
	}
	
	public String[] getAllGrpMembers(String grpName){
		String[] memberArr = null;
		if(grpName != null && grpName.trim().length() > 0){
			memberArr = UserManager.getGroup(grpName).getMemberNames();
		}
		return memberArr;
	}
	

}
