package com.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("boardDAO")
public class BoardDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>)selectList("board.selectBoardList", map); 
		//결과값 형 변환. "board.selectBoardList"  board는 SQL의 매퍼 네임스페이스
	}
	
	//페이징 처리
	public int getCount(Map<String, Object> map) throws Exception {
		return ((Integer) selectOne("getCount", map)).intValue();
	}
	
	//글 쓰기
	public void insertBoard(Map<String, Object> map)throws Exception {
		insert("board.insertBoard", map);
	}
	
	//조회수 증가
	public void updateHitCnt(Map<String, Object> map) throws Exception{
		update("board.updateHitCnt", map);
	}
	
	//글 상세보기
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("board.selectBoardDetail", map);
	}
	
	//답글 쓰기
	public void insertReply(Map<String, Object> map)throws Exception {
		insert("board.insertReply", map);
	}
	
	//글 수정
	public void updateBoard(Map<String, Object> map)throws Exception {
		update("board.updateBoard",map);
	}

	//글 삭제
	public void deleteBoard(Map<String, Object> map) throws Exception{
		delete("board.deleteBoard", map);
	}

	//글 쓰기 시 파일 업로드
	public void insertFile(Map<String, Object> map)throws Exception{
		insert("board.insertFile",map);
	}
	
	//글 상세보기 시 파일 리스트 조회
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map)throws Exception {
		return (List<Map<String, Object>>) selectList("board.selectFileList",map);
	}
	
	//글 수정시 파일 삭제
	public void deleteFileList(Map<String, Object> map)throws Exception {
		update("board.deleteFileList", map);
	}
	
	//글 수정시 파일 추가
	public void updateFile(Map<String, Object> map)throws Exception {
		 update("board.updateFile", map);
	}

	public int passCheck(Map<String, Object> map) {
		return (Integer)selectOne("board.passCheck", map);
	}
}
