package com.audit.email;

import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.axis.client.Service;

import com.savvion.mom.*;

public class UsersInfo {
	private String wsdlUrl = "";
	private final static String SBM_DB_JNDI = "jdbc/SBMCommonDBXA";

	public UsersInfo() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("IP Address is $ " + ip);
			if (ip.contains("10.65.15.")) {
				wsdlUrl = "http://10.65.15.160:8081/Service1.asmx?wsdl";
			} else {
				wsdlUrl = "http://10.65.5.158:8081/Service1.asmx?wsdl";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void closeResources(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {

		try {

			if (null != rs) {
				rs.close();
				rs = null;
			}
			if (null != pstmt) {
				pstmt.close();
				pstmt = null;
			}
			if (null != conn) {
				conn.close();
				conn = null;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection(String jndi) {
		Connection conn = null;
		try {
			if (null != jndi) {
				conn = ((DataSource) new InitialContext().lookup(jndi))
						.getConnection();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	public String getZonalHead(String region) {
		try {
			String zh = "";
			if (region != null && region.length() > 1) {
				// Service1Soap_BindingStub sb = new
				// Service1Soap_BindingStub(new URL(wsdlUrl), new Service());
				// zh = sb.getZHDetails(region);
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					conn = getConnection(SBM_DB_JNDI);
					pstmt = conn
							.prepareStatement("select employee_name from zom_panel where region_name=?");
					pstmt.setString(1, region);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						zh = rs.getString("employee_name");
					}
				} catch (SQLException sqe) {
					throw new RuntimeException(sqe);
				} finally {
					closeResources(rs, pstmt, conn);
				}
			}
			return zh;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String getEmailId(String empCode) {
		try {
			String email = "";
			if (empCode != null && empCode.length() > 1) {
				Service1Soap_BindingStub sb = new Service1Soap_BindingStub(
						new URL(wsdlUrl), new Service());
				email = sb.getEmployeeEmailId(empCode);
			}
			return email;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String getContactNo(String empCode) {
		try {
			String contactNo = "";
			if (empCode != null && empCode.length() > 1) {
				Service1Soap_BindingStub sb = new Service1Soap_BindingStub(
						new URL(wsdlUrl), new Service());
				contactNo = sb.getEmployeeContactNo(empCode);
			}
			return contactNo;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}