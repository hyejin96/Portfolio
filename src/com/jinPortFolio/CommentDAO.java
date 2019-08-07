package com.jinPortFolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CommentDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	ResultSet rs;
	AdminVO commentVO = null;
	
	public CommentDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/djagpwls";
			String id = "root";
			String pw = "1234";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		} // catch end
	}// public commentDAO end

	/*
	 * 1. 댓글 리스트(=상세) - arrayList
	 * 2. 댓글 생성
	 * 3. 댓글 수정
	 * 4. 댓글 삭제 */
	
	// 1. 댓글 리스트
	public ArrayList<CommentVO> getComment(int boardId) {
		ArrayList<CommentVO> commentList = new ArrayList<>();
		try {
			String sql = "select comment_id, board_id, comment_name, comment_content, create_time "
					+ "from board_comment "
					+ "where board_id = ? "
					+ "order by create_time desc";
		 pstmt = conn.prepareStatement(sql);
		 pstmt.setInt(1, boardId);
		 
		 rs = pstmt.executeQuery();
		 
		 while(rs.next()) {
			 commentList.add(
					 new CommentVO(
							 rs.getInt("comment_id"), rs.getInt("board_id"), rs.getString("comment_name"), rs.getString("comment_content"), 
							 rs.getString("create_time").substring(0, 10)));
		 }
		 rs.close();
		 pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
	
	// 댓글 작성
	public boolean commentCreate(int boardId, String name, String content) {
		boolean result = false;
		try {
			String sql = "INSERT INTO BOARD_COMMENT(board_id, comment_content, comment_name) "
					+ "VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			pstmt.setString(2, content);
			pstmt.setString(3, name);			
			
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}

			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 댓글 수정
	public boolean updateComment(int boardId, int commentId, String name, String content) {
		boolean result = false;
		
		try {
			String sql = "UPDATE BOARD_COMMENT "
					+ "SET name = ? , CONTENT = ? "
					+ "WHERE BOARD_ID = ? and comment_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, content);
			pstmt.setInt(3, boardId);
			pstmt.setInt(4, commentId);
			
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
			
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 댓글 삭제
	public boolean commentDelete(int commentId, int boardId) {
		boolean result = false;
		
		try {
			String sql = "DELETE "
					+ "FROM BOARD_COMMENT "
					+ "WHERE BOARD_ID = ? and comment_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			pstmt.setInt(2, commentId);
			
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
