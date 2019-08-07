package com.jinPortFolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	ResultSet rs;
	AdminVO adminVO = null;
	
	public AdminDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/djagpwls";
			String id = "root";
			String pw = "1234";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		} // catch end
	}// public AdminDAO end
	
	public boolean isAdmin(String id, String pw) {
		boolean result = false;
		
		try {
			String sql = "SELECT adminId "
					+ "FROM ADMIN "
					+ "WHERE adminId = ? AND adminPw = ?";
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = true;
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
