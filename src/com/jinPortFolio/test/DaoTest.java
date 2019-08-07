package com.jinPortFolio.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jinPortFolio.BoardDAO;

public class DaoTest {
	static BoardDAO bDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bDAO = new BoardDAO();
		System.out.println("@BeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("@AfterClass");
	}
	
	@Before
	public void 단위테스트전() throws Exception {
		System.out.println("@단위테스트전");
	}

	@After
	public void 단위테스트후() throws Exception {
		System.out.println("@단위테스트후");
	}
	
	@Test
	public void 게시글상세이후() {
		System.out.println(bDAO.getAfterBoardId(5));	
	}
	

}
