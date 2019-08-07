package com.jinPortFolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class BoardDAO {

	private Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	BoardVO vo = null;
	
	public BoardDAO(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/djagpwls";
			String id = "root";
			String pw = "1234";
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		} // catch end
	}// public boardDAO end
	
	/*
	 * 1. 게시글 조회, 게시글 수
	 * 2. 게시글 상세, 게시글 이전글, 이후글
	 * 3. 게시글 작성
	 * 4. 게시글 삭제
	 * 5. 수정
	 * 6. 검색
	 * 7. 조회수 업데이트*/
	// 게시글 조회 - ArrayList
	public ArrayList<BoardVO> getBoard(int start, int end) {
		ArrayList<BoardVO> boardList = new ArrayList<>();
		try {
			String sql = "SELECT board_id, title, content, writer, file, writedate, updatedate, board_hit "
					+ "FROM BOARD "
					+ "ORDER BY WRITEDATE DESC LIMIT ?, ?";
		 pstmt = conn.prepareStatement(sql);
		 pstmt.setInt(1, start-1);
		 pstmt.setInt(2, end);
		 
		 rs = pstmt.executeQuery();
		 
		 while(rs.next()) {
			 boardList.add(
					 new BoardVO(
							 rs.getInt("board_id"), rs.getString("title"), rs.getString("content"), rs.getString("writer"), 
							 rs.getString("file"),  rs.getString("writedate").substring(0, 10), rs.getString("updatedate").substring(0, 10), rs.getString("board_hit")));
		 }
		 rs.close();
		 pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}
	
	// 전체 게시글의 수 - int
	public int boardCount() {
		int boardCount = 0;
		String sql = "SELECT COUNT(BOARD_ID) "
				+ "FROM BOARD";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				boardCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardCount;
	}
	
	// 게시글 상세 - vo
	public BoardVO getBoardDetail(int boardId) {
		try {
			String sql = "SELECT BOARD_ID, TITLE, CONTENT, WRITER, FILE, WRITEDATE, UPDATEDATE, BOARD_HIT "
					+ "FROM BOARD "
					+ "WHERE BOARD_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			
			vo = new BoardVO();
			
			while(rs.next()) {
				vo.setBoardId(rs.getInt("BOARD_ID"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setContent(rs.getString("CONTENT"));
				vo.setWrite(rs.getString("WRITER"));
				vo.setFile(rs.getString("FILE"));
				vo.setWriteDate(rs.getString("WRITEDATE").substring(0, 10));
				vo.setUpdateDate(rs.getString("UPDATEDATE").substring(0, 10));
				vo.setBoardHit(rs.getString("BOARD_HIT"));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	// 게시글 이전글
	public int getPreBoardId(int boardId) {
		int boardNum = 0;
		try {
			String sql = "SELECT BOARD_ID "
					+ "FROM BOARD WHERE BOARD_ID = (SELECT MAX(BOARD_ID) FROM BOARD WHERE BOARD_ID < ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardNum = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardNum;
	}
	
	// 게시글 다음글
	public int getAfterBoardId(int boardId) {
		int boardNum = 0;
		try {
			String sql = "SELECT BOARD_ID "
					+ "FROM BOARD WHERE BOARD_ID = (SELECT MIN(BOARD_ID) FROM BOARD WHERE BOARD_ID > ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardNum = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardNum;
	}
	
	// 게시글 작성
	public boolean postBoard(String title, String content, String writer, String file) {
//		System.out.println(title + " / " + writer + " / " + content + " / " + file);
		boolean result = false;
		try {
			String sql = "INSERT INTO BOARD(TITLE, CONTENT, WRITER, FILE) "
					+ "VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, file);
			
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}

			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 게시글 삭제
	public boolean deleteBoard(int boardId) {
		boolean result = false;
		
		try {
			String sql = "DELETE "
					+ "FROM BOARD "
					+ "WHERE BOARD_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 게시글 수정
	public boolean boardUpdate(int boardId, String title, String content, String writer) {
		boolean result = false;
		
		try {
			String sql = "UPDATE BOARD "
					+ "SET TITLE = ? , CONTENT = ? , WRITER = ? "
					+ "WHERE BOARD_ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setInt(4, boardId);
			
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
			
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 검색
	public ArrayList<BoardVO> getSearchBoard(String search, int start, int end) {
		ArrayList<BoardVO> bvo = new ArrayList<>();
		try {
			String sql = "SELECT BOARD_ID, TITLE, CONTENT, WRITER, FILE, WRITEDATE, UPDATEDATE, BOARD_HIT "
					+ "FROM BOARD "
					+ "WHERE (TITLE LIKE ? OR CONTENT LIKE ? OR WRITER LIKE ?) "
					+ "ORDER BY BOARD_ID DESC LIMIT ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, search);
			pstmt.setString(2, search);
			pstmt.setString(3, search);
			pstmt.setInt(4, start-1);
			pstmt.setInt(5, end);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				bvo.add(
						 new BoardVO(
								 rs.getInt("board_id"), rs.getString("title"), rs.getString("content"), rs.getString("writer"), 
								 rs.getString("file"),  rs.getString("writedate").substring(0, 10), rs.getString("updatedate").substring(0, 10), rs.getString("board_hit")));
			 }
			
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bvo;
	}
	
	// 검색 게시글의 수 - int
	public int boardSearchCount(String searchName) {
		int boardCount = 0;
		String sql = "SELECT COUNT(BOARD_ID) "
				+ "FROM BOARD "
				+ "WHERE (TITLE LIKE ? OR CONTENT LIKE ? OR WRITER LIKE ?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchName);
			pstmt.setString(2, searchName);
			pstmt.setString(3, searchName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardCount;
	}
	
	public int boardHit(int boardId) {
		int boardHitCount = 0;
		String sql = "UPDATE BOARD "
				+ "SET board_hit = board_hit + 1 "
				+ "WHERE BOARD_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			
			if(pstmt.executeUpdate() == 1) {
				boardHitCount = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardHitCount;
	}

}
