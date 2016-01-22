package com.spring.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {
	// 리스트 불러오기
	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;
	
	// 글 쓰기
	// void insertBoard(Map<String, Object> map) throws Exception;
	void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;

	//조회수 증가
	void updateHitCnt(Map<String, Object> map) throws Exception;

	// 상세보기
	Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception;
	
	// 글 수정하기
	// void updateBoard(Map<String, Object> map) throws Exception;
	void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	// 글 삭제하기
//	void deleteBoard(Map<String, Object> map) throws Exception;
	void deleteBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	
	// 페이징처리 
	int getCount(Map<String, Object> map) throws Exception;
	
	// 패스워드 체크
	int passCheck(Map<String, Object> map) throws Exception;
	
	// 답글 쓰기
	void insertReply(Map<String, Object> map, HttpServletRequest request) throws Exception;


}
