package com.jinPortFolio.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.util.Arrays;

import com.jinPortFolio.AdminDAO;
import com.jinPortFolio.BoardDAO;
import com.jinPortFolio.BoardVO;
import com.jinPortFolio.CommentDAO;
import com.jinPortFolio.CommentVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;

import org.codehaus.jackson.map.ObjectMapper;
/**
 * Servlet implementation class JinPortFolio
 */
@WebServlet("/jinPortfolio")
public class JinPortFolio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int count=0;  
	public void init(ServletConfig config) throws ServletException {
		System.out.println(++count+" init()");
	}

	public void destroy() {
		System.out.println(++count+" destroy()");
	}
	
	BoardDAO bDAO;
	AdminDAO aDAO;
	CommentDAO cDAO;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(++count+" service");
		request.setCharacterEncoding("utf-8");

		String cmd = request.getParameter("cmd");

		if(cmd == null) 
			cmd = "";
		String page = "main.jsp";

		switch(cmd) {
			case "logout":
			page = logout(request);
			break;
			case "peopleEnter":	// 관리자외 로그인
				page = peopleEnter(request);
				break;
			case "adminLogin": // 관리자 로그인
				page = adminLogin(request);
				break;
			/*게시판(board)*/
			case "boardList":	// 게시판 목록
				page = boardListNumber(request); // return list --> 게시글 번호를 위해 생성
				page = boardList(request);				// return main
				break;
			case "boardListPaging":	// 게시판 페이징시 사용
				page = boardListPaging(request);
				break;
			case "boardDetail": // 게시판 상세
				page = commentList(request); // 댓글 리스트
				page = boardDetail(request, response);
				break;
			case "boardMoveDetailAction": // 게시판 이동시 아이작스
				page = commentListMove(request); // 댓글 리스트
				page = boardMoveDetailAction(request);
				break;
			case "postBoard":	// 게시판 작성화면
				page = "board/boardWrite.jsp";	
				break;
			case "postBoardAction": // 게시판 작성
				page = postBoardAction(request);
				break;
			case "boardDelete": // 게시글 삭제액션
				page = boardDelete(request);
				break;
			case "boardUpdate": // 게시글 수정페이지 이동
				page = boardUpdate(request); // 수정을 위해 게시글 조회
				break;
			case "boardUpdateAction": // 게시글 수정 액션
				page = boardUpdateAction(request);
				break;
			case "boardSearch": //게시글 검색
				page = boardSearch(request);
				break;
			case "boardSearchListPaging":	// 게시판 검색 페이징시 사용
				page = boardSearchListPaging(request);
				break;
			case "boardListDelete": // 게시판 리스트 선택 삭제
				page = boardListDelete(request);
				break;
				
			/*게시글(board) 댓글*/
			case "commentCreate": // 댓글 작성
				page = commentCreate(request);
				break;
			case "commentDelete":
				page = commentDelete(request);
				break;
				
			/*영화*/
			case "movieList":
			try {
				page = movieList(request);
			} catch (OpenAPIFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			
			/*날씨*/
			case "weatherInfo":
				page = weatherInfo(request);
				break;
		}
		request.getRequestDispatcher("/" + page).forward(request, response); // 4.실제 전송은 해당 html, jsp 페이지가 담당한다.

	}

	
	

	private String weatherInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "weather/weatherInfo.jsp";
	}

	private String movieList(HttpServletRequest request) throws OpenAPIFault, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        int strToday = Integer.parseInt(sdf.format(c1.getTime()));
        int preDay = strToday-1;
        String preDays = "" + preDay;
        String today = "" + preDay;
        
        request.setAttribute("todayyyy", preDays.substring(0, 4));
        request.setAttribute("todaymm", preDays.substring(4, 6));
        request.setAttribute("todaydd", preDays.substring(6, 8));
		
		String targetDt = request.getParameter("targetDt") == null ?  today: request.getParameter("tagetDt");
		String itemPerPage = request.getParameter("itemPerPage") == null ? "10" : request.getParameter("itemPerPage");
		String multiMovieYn = request.getParameter("multiMovieYn") == null ? "" : request.getParameter("multiMovieYn");
		String repNationYn = request.getParameter("repNationYn") == null ? "" : request.getParameter("repNationYn");
		String wideAreaCd = request.getParameter("wideAreaCd") == null ? "" : request.getParameter("wideAreaCd");
		
		String key = "5e4ea646b22d11484652f6a2da1d25c8";
		KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
		
		String dailyResponse = service.getDailyBoxOffice(true, targetDt, itemPerPage, multiMovieYn, repNationYn, wideAreaCd);
		
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> dailyResult = mapper.readValue(dailyResponse, HashMap.class);
		
		request.setAttribute("dailyResult", dailyResult);
		
		String codeResponse = service.getComCodeList(true, "0105000000");
		HashMap<String, Object> codeResult = mapper.readValue(codeResponse, HashMap.class);
		request.setAttribute("codeResult", codeResult);
		return "movieList/movieList.jsp";
	}

	private String commentDelete(HttpServletRequest request) {
		cDAO = new CommentDAO();
		boolean result = false;
		
		int deleteCommentId = Integer.parseInt(request.getParameter("deleteCommentId"));
		int deleteBoardId = Integer.parseInt(request.getParameter("deleteBoardId"));
		
		result = cDAO.commentDelete(deleteCommentId, deleteBoardId);
		if(result) {
			return "";
		}
		
		return "";
	}

	private String commentCreate(HttpServletRequest request) {
		cDAO = new CommentDAO();
		boolean result = false;
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		
		String name = request.getParameter("name");
		
		System.out.println("name : " + name);
		
		if(name == null || "".equals(name) || name == "undefined") {
			name = "익명";
		}
		
		String content =  request.getParameter("content");
		
		System.out.println(boardId+ " / " + name+ " / " + content);
		
		result = cDAO.commentCreate(boardId, name, content);
		
		ArrayList<CommentVO> cList = new ArrayList<CommentVO>();
		
		cList = cDAO.getComment(boardId);
		request.setAttribute("cList", cList);
		
		return "board/commentCreateResult.jsp";
	}

	private String commentListMove(HttpServletRequest request) {
		cDAO = new CommentDAO();

		ArrayList<CommentVO> cList = new ArrayList<CommentVO>();
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
//		int pageNum = Integer.parseInt(request.getParameter("pageNum")); 
//		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		
		cList = cDAO.getComment(boardId);
		request.setAttribute("cList", cList);
		return "board/boardDetailAjax.jsp";
	}
	
	private String commentList(HttpServletRequest request) {
		cDAO = new CommentDAO();

		ArrayList<CommentVO> cList = new ArrayList<CommentVO>();
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum")); 
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		
		cList = cDAO.getComment(boardId);
		request.setAttribute("cList", cList);
		return "jinPortfolio?cmd=boardDetail&boardId=" + boardId + "&pageNum=" + pageNum + "&boardNum=" + boardNum;
	}

	private String boardListDelete(HttpServletRequest request) {
		bDAO = new BoardDAO();
		
//		String[] deleteIds = request.getParameterValues("deleteId");
		System.out.println(request.getParameter("deleteId"));
		String[] deleteIds = request.getParameter("deleteId").toString().split(",");
		for(int i = 0; i < deleteIds.length; i++) {
			int deleteId = Integer.parseInt(deleteIds[i]);
//			System.out.println(deleteId + " 삭제번호");
			boolean result = bDAO.deleteBoard(deleteId);
		}	
		return "jinPortfolio?cmd=boardList";
	}

	private String boardSearchListPaging(HttpServletRequest request) {
		bDAO = new BoardDAO();
		
		ArrayList<BoardVO> bList = new ArrayList<>();
		
		int boardNumber = 0;
		
		int startPage = 0;
		int endPage = 0;
		
		int pageCount = 0;
		
		int pageBlock = 0;
		int pageSize = 9;
		

//		String searchName = request.getParameter("searchName");
//		searchName = "%" +searchName + "%";
//		
//		request.setAttribute("searchName", searchName);
		
		HttpSession session = request.getSession();
		String searchName = (String)session.getAttribute("searchName");
		
		System.out.println(searchName + " searchNamePaging");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}

		int currentPage = Integer.parseInt(pageNum);
		
		boardNumber = bDAO.boardSearchCount(searchName);
		
		if(boardNumber == (currentPage - 1) * pageSize) { 
			currentPage -= 1;
		}
		int startRow = (currentPage - 1) * pageSize + 1;
		// int endRow = (currentPage * pageSize);
		
		if(boardNumber > 0) {
			bList = bDAO.getSearchBoard(searchName, startRow, pageSize);
			pageCount = boardNumber / pageSize + (boardNumber % pageSize == 0 ? 0 : 1);
			startPage = 1;
			
			if(currentPage % 5 != 0) {
				startPage = (int) (currentPage / 5) * 5 + 1;
			}else {
				startPage = ((int) (currentPage / 5) - 1) * 5 + 1;
			}
			pageBlock = 5;
			endPage = startPage + pageBlock - 1;
			if(endPage > pageCount) endPage = pageCount;
		}
		if(bList.isEmpty()) boardNumber = 0;
		
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("bList", bList);
		request.setAttribute("boardNumber", new Integer(boardNumber));
				
		return "board/boardSearchList.jsp";

	}

	private String boardSearch(HttpServletRequest request) {
		bDAO = new BoardDAO();
		
		ArrayList<BoardVO> bList = new ArrayList<>();
		
		int boardNumber = 0;
		
		int startPage = 0;
		int endPage = 0;
		
		int pageCount = 0;
		
		int pageBlock = 0;
		int pageSize = 9;
		
		String searchName = request.getParameter("searchName");
		searchName = "%" +searchName + "%";
		System.out.println(searchName + " searchName");
		request.setAttribute("searchName", searchName);

		HttpSession session = request.getSession(true);
		session.setAttribute("searchName", searchName);
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}

		int currentPage = Integer.parseInt(pageNum);
		
		boardNumber = bDAO.boardSearchCount(searchName);
		
		if(boardNumber == (currentPage - 1) * pageSize) { 
			currentPage -= 1;
		}
		int startRow = (currentPage - 1) * pageSize + 1;
		// int endRow = (currentPage * pageSize);
		
		if(boardNumber > 0) {
			bList = bDAO.getSearchBoard(searchName, startRow, pageSize);
			pageCount = boardNumber / pageSize + (boardNumber % pageSize == 0 ? 0 : 1);
			startPage = 1;
			
			if(currentPage % 5 != 0) {
				startPage = (int) (currentPage / 5) * 5 + 1;
			}else {
				startPage = ((int) (currentPage / 5) - 1) * 5 + 1;
			}
			pageBlock = 5;
			endPage = startPage + pageBlock - 1;
			if(endPage > pageCount) endPage = pageCount;
		}
		if(bList.isEmpty()) boardNumber = 0;
		
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("bList", bList);
		request.setAttribute("boardNumber", new Integer(boardNumber));
				
		return "board/boardSearchList.jsp";
	}

	private String boardUpdateAction(HttpServletRequest request) {
		bDAO = new BoardDAO();
		
		boolean result = false;
		String resultPath = "";
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		result = bDAO.boardUpdate(boardId, title, content, writer);
		
//		System.out.println(title + " / " + content + " / " + writer + " / ");
		
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
//		System.out.println(pageNum);
		

		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		System.out.println(boardNum + "update boardNum");
		
		if(result == true) {
			resultPath = "jinPortfolio?cmd=boardDetail&boardId=" + boardId + "&pageNum=" + pageNum + "&boardNum=" + boardNum;
		}else {
			resultPath = "jinPortfolio?cmd=boardUpdate&boardId" + boardId;
		}
		return resultPath;
	}
	
	private String boardUpdate(HttpServletRequest request) {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		BoardVO bVO = bDAO.getBoardDetail(boardId);
		request.setAttribute("bVO", bVO);
		
		HttpSession session = request.getSession();
		String pageNum = (String)session.getAttribute("originalPageNum");
		request.setAttribute("pageNum", pageNum);
		
		int boardNum = Integer.parseInt(request.getParameter("boardNum"));
		request.setAttribute("boardNum", boardNum);
		
		return "board/boardUpdate.jsp";
	}

	private String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null)
			session.invalidate();
		
		return "main.jsp";
	}
	
	private String peopleEnter(HttpServletRequest request) {
		
		String id = "people";
		request.setAttribute("id", id);	
		
		HttpSession session = request.getSession(true);
		session.setAttribute("id", id);
		
		return "main2.jsp";
	}
	private String adminLogin(HttpServletRequest request) {
		aDAO = new AdminDAO();
		
		boolean result = false;
		String resultPath = "";
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		result = aDAO.isAdmin(id, pw);
		
		request.setAttribute("id", id);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("id", id);
		
		if (result == true) {
			resultPath =  "main2.jsp";
		}else {
			resultPath = "main.jsp";
		}
		
		return resultPath;
	}

	private String boardDelete(HttpServletRequest request) {
		bDAO = new BoardDAO();
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		
		boolean result = false;
		
		result = bDAO.deleteBoard(boardId);
		
		if(result) {
			return "jinPortfolio?cmd=boardList";
		}
		
		return "jinPortfolio?cmd=boardDetail&boardId="+ boardId;
	}

	private String postBoardAction(HttpServletRequest request) throws ServletException, IOException {
		bDAO = new BoardDAO();

		request.setCharacterEncoding("UTF-8");
		String encType = "utf-8";
		int maxSize = 1 * 1024 * 1024; // 최대 업로드 파일 크기
		
		MultipartRequest fileUp = null;
		
		String savePath = request.getServletContext().getRealPath("/assets/uploadFile/boardFile");
		fileUp = new MultipartRequest(request, savePath, maxSize, encType, new DefaultFileRenamePolicy());
		
//		Enumeration<?> files = fileUp.getFileNames();
		
		String file = fileUp.getFilesystemName("file");
		
		String title = fileUp.getParameter("title");
		String writer = fileUp.getParameter("writer");
//		System.out.println("writer : " + writer);
		if(writer == null || "".equals(writer)) {
			writer = "익명";
		}
		String content = fileUp.getParameter("content");
		
		
//		System.out.println(title + " /" + writer + "/ " + content + " / " + file);
		boolean result = false;
		
		result = bDAO.postBoard(title, content, writer, file);
		
		if(result == false) {
			System.out.println("실패");
			return "board/boardWrite.jsp";
		}
		
		return "jinPortfolio?cmd=boardList";
	}
	
	// 게시글 이전/이후 아이작스
	private String boardMoveDetailAction(HttpServletRequest request) {
		bDAO = new BoardDAO();
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String pageNum = request.getParameter("pageNum");
		String boardNum = request.getParameter("boardNum");
		
		//게시글 번호
		request.setAttribute("boardNum", boardNum);
		
		// 페이지 번호 섹션값 주기
		HttpSession session = request.getSession(true);
		session.setAttribute("movePageNum", pageNum);
		
		// 게시글 조회수
		int boardHitCount = 0;
		boardHitCount = bDAO.boardHit(boardId);
		
		// 상세
		BoardVO bVO = bDAO.getBoardDetail(boardId);
		request.setAttribute("bVO", bVO);
		request.setAttribute("pageNum", pageNum);
		
		// 다음글
		int afterBoardId = bDAO.getAfterBoardId(boardId);
		BoardVO bAVO = bDAO.getBoardDetail(afterBoardId);
		request.setAttribute("bAVO", bAVO);
		request.setAttribute("afterBoardId", afterBoardId);

		// 이전글
		int preBoardId = bDAO.getPreBoardId(boardId);
		BoardVO bPVO = bDAO.getBoardDetail(preBoardId);
		request.setAttribute("bPVO", bPVO);
		request.setAttribute("preBoardId", preBoardId);
		
		return "board/boardDetailAjax.jsp";
	}

	// 게시글 상세
	private String boardDetail(HttpServletRequest request, HttpServletResponse response) {
		bDAO = new BoardDAO();
		
		// 쿠기를 이용하여 조회수 확인
		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		
//		System.out.println(cookies + "쿠기확인");
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String pageNum = request.getParameter("pageNum");
		String boardNum = request.getParameter("boardNum");
		
		//게시글 번호
		request.setAttribute("boardNum", boardNum);
		
		// 페이지 번호 섹션값 주기
		HttpSession session = request.getSession(true);
		session.setAttribute("originalPageNum", pageNum);
		
		// 게시글 조회수(쿠기 이용)
		if (cookies != null && cookies.length > 0)  {
			for (int i = 0; i < cookies.length; i++) {
//				System.out.println(cookies[i].getName().equals("cookie"+boardId));
				if (cookies[i].getName().equals("cookie"+boardId)) {
					// 처음 쿠키가 생성한 뒤에 들어옴
					viewCookie = cookies[i];
				}
			}
		}
		
		if (viewCookie == null) { // 쿠기 없음
			// 쿠키 생성(이름, 값)
            Cookie newCookie = new Cookie("cookie"+boardId, "|" + boardId + "|");
            
            // 쿠기 추가
            response.addCookie(newCookie);

    		int boardHitCount = 0;
    		boardHitCount = bDAO.boardHit(boardId);
    		if(boardHitCount > 0) {
    			System.out.println("조회수 증가");
    		}else {
    			System.out.println("조회수 증가 에러");
    		}
		} else { // 쿠키 있음
			// 쿠키값 받아옴
			String value = viewCookie.getValue();
            
//            System.out.println("cookie 값 : " + value);
		}
		
		// 상세
		BoardVO bVO = bDAO.getBoardDetail(boardId);
		request.setAttribute("bVO", bVO);
		request.setAttribute("pageNum", pageNum);
		
		// 다음글
		int afterBoardId = bDAO.getAfterBoardId(boardId);
		BoardVO bAVO = bDAO.getBoardDetail(afterBoardId);
		request.setAttribute("bAVO", bAVO);
		request.setAttribute("afterBoardId", afterBoardId);

		// 이전글
		int preBoardId = bDAO.getPreBoardId(boardId);
		BoardVO bPVO = bDAO.getBoardDetail(preBoardId);
		request.setAttribute("bPVO", bPVO);
		request.setAttribute("preBoardId", preBoardId);
		
		
		return "board/boardDetail.jsp";
	}

	// 게시글 페이징
	private String boardListPaging(HttpServletRequest request) {
		bDAO = new BoardDAO();

		int boardNumber = 0;
		int pageCount = 0;
		int startPage = 0;
		int endPage = 0;
		int pageBlock = 0;
		int pageSize = 9;
		
		String pageNum = request.getParameter("pageNum");

		if(pageNum == null) 
			pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		ArrayList<BoardVO> bList = new ArrayList<BoardVO>();
		
		boardNumber = bDAO.boardCount();
		
		if(boardNumber == (currentPage - 1) * pageSize)
			currentPage -= 1;
		int startRow = (currentPage -1) * pageSize + 1;
		if(boardNumber > 0) {
			bList = bDAO.getBoard(startRow, pageSize);
			pageCount = boardNumber / pageSize + (boardNumber % pageSize == 0 ? 0 : 1);
			startPage = 1;
			
			if(currentPage % 5 != 0) {
				startPage = (int) (currentPage / 5) * 5 + 1;
			}else {
				startPage = ((int) (currentPage / 5) - 1) * 5 + 1;
			}
			pageBlock = 5;
			endPage = startPage + pageBlock -1;
			if(endPage > pageCount) endPage = pageCount;
		}
		if(bList.isEmpty()) boardNumber = 0;
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("bList", bList);
		request.setAttribute("boardNumber", new Integer(boardNumber));
		
//		System.out.println("pageNum" + pageNum +" / "+ "pageCount" + pageCount +" / " + 
//				"currentPage" + currentPage +" / " +"pageBlock" + pageBlock +" / " + 
//				"boardNumber" + new Integer(boardNumber)+" / " +"pageSize" + pageSize);
		
		return "board/boardList.jsp";
	}
	
	// 게시글 전체 숫자
	private String boardListNumber(HttpServletRequest request) {
		bDAO = new BoardDAO();
		int boardNumber = 0;
		int pageCount = 0;
		int startPage = 0;
		int endPage = 0;
		int pageBlock = 0;
		int pageSize = 9;
		
		String pageNum = request.getParameter("pageNum");

//		System.out.println("입장");
		if(pageNum == null) 
			pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		ArrayList<BoardVO> bList = new ArrayList<BoardVO>();
		
		boardNumber = bDAO.boardCount();
		
		if(boardNumber == (currentPage - 1) * pageSize)
			currentPage -= 1;
		int startRow = (currentPage -1) * pageSize + 1;
		if(boardNumber > 0) {
			bList = bDAO.getBoard(startRow, pageSize);
			pageCount = boardNumber / pageSize + (boardNumber % pageSize == 0 ? 0 : 1);
			startPage = 1;
			
			if(currentPage % 5 != 0) {
				startPage = (int) (currentPage / 5) * 5 + 1;
			}else {
				startPage = ((int) (currentPage / 5) - 1) * 5 + 1;
			}
			pageBlock = 5;
			endPage = startPage + pageBlock -1;
			if(endPage > pageCount) endPage = pageCount;
		}
		if(bList.isEmpty()) boardNumber = 0;
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("bList", bList);
		request.setAttribute("boardNumber", new Integer(boardNumber));

//		System.out.println("pageNum" + pageNum +" / "+ "pageCount" + pageCount +" / " + 
//								"currentPage" + currentPage +" / " +"pageBlock" + pageBlock +" / " + 
//								"boardNumber" + new Integer(boardNumber) +" / " +"pageSize" + pageSize);
		return "board/boardList.jsp";
	}
	
	// 게시글 리스트
	private String boardList(HttpServletRequest request) {
		bDAO = new BoardDAO();
		int boardNumber = 0;
		int pageCount = 0;
		int startPage = 0;
		int endPage = 0;
		int pageBlock = 0;
		int pageSize = 9;
		
		String pageNum = request.getParameter("pageNum");

//		System.out.println("입장");
		if(pageNum == null) 
			pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		ArrayList<BoardVO> bList = new ArrayList<BoardVO>();
		
		boardNumber = bDAO.boardCount();
		
		if(boardNumber == (currentPage - 1) * pageSize)
			currentPage -= 1;
		int startRow = (currentPage -1) * pageSize + 1;
		if(boardNumber > 0) {
			bList = bDAO.getBoard(startRow, pageSize);
			pageCount = boardNumber / pageSize + (boardNumber % pageSize == 0 ? 0 : 1);
			startPage = 1;
			
			if(currentPage % 5 != 0) {
				startPage = (int) (currentPage / 5) * 5 + 1;
			}else {
				startPage = ((int) (currentPage / 5) - 1) * 5 + 1;
			}
			pageBlock = 5;
			endPage = startPage + pageBlock -1;
			if(endPage > pageCount) endPage = pageCount;
		}
		if(bList.isEmpty()) boardNumber = 0;
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("bList", bList);
		request.setAttribute("boardNumber", new Integer(boardNumber));
		
//		System.out.println("pageNum" + pageNum +" / "+ "pageCount" + pageCount +" / " + 
//								"currentPage" + currentPage +" / " +"pageBlock" + pageBlock +" / " + 
//								"boardNumber" + new Integer(boardNumber) +" / " +"pageSize" + pageSize);
		return "board/boardMain.jsp";
	}

}
