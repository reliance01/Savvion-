package com.savvion.rcf;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CalldeskDBResource {
	private static final String TREE_DATASOURCE = "jdbc/SBMCommonDB";

	DataSource ds = null;

	public CalldeskDBResource() {
		try {
			this.ds = (DataSource) new InitialContext().lookup(TREE_DATASOURCE);
		} catch (Exception e) {
			throw new RuntimeException("Error while getting getting DataSource Connection", e);
		}
	}

	public Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			if (useDataSource()) {
				conn = ds.getConnection();
			} else {
				conn = CalldeskConnectionPool.self().getConnectionPool().getConnection();
			}
		} catch (Exception ex) {
			throw new RuntimeException("Error while getting getting Database Connection", ex);
		}
		return conn;
	}

	public boolean useDataSource() {
		if (ds != null) {
			return true;
		}
		return false;
	}

}