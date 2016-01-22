package com.spring.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.spring.board.dao.BoardDAO;
import com.spring.utils.FileUtils;


@Service("boardService")
public class BoardServiceImpl implements BoardService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "fileUtils") //FileUtils 에 component 처리 했으므로, 서비스단에서 resource 처리
	private FileUtils fileUtils;
	
	@Resource(name="boardDAO")  //BoardDAO에서 등록한 @Repository(name = "boardDAO")
	private BoardDAO boardDAO;
	
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return boardDAO.selectBoardList(map);
	}
	
	//페이징 처리
	@Override
	public int getCount(Map<String, Object> map) throws Exception{
		return boardDAO.getCount(map);
	}

//	@Override // 일반 글쓰기
//	public void insertBoard(Map<String, Object> map) throws Exception {
//		boardDAO.inserBoard(map);
//	}
	@Override // 글쓰기 + 파일 업로드
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		boardDAO.insertBoard(map); //게시물 생성.
		//파일 업로드 정보 DB에 전달.
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(map, request); 
		//fileUtils에서 파일 저장 후 파일 정보를 DB에 저장하기위해 list에 담기.
		for(int i=0, size=list.size(); i<size; i++){
			boardDAO.insertFile(list.get(i));
		}
		
		/*
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		// 첨부파일은 Multipart 형식의 데이터, HttpServletRequest에 담겨서 서버로 전송됨. Http~ 를 MultipartHttpServletRequest로 형변환함.
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		// iterator 패턴을 이용. 데이터의 집합체에서 컬렉션으로부터 정보를 얻어올수 있는 인터페이스. 
		MultipartFile multipartFile = null;
		while(iterator.hasNext()){
			//hasNext 메서드를 통해서 Iterator에 다음 값이 있는 동안 반복해서 다른 작업을 수행.
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			//Multipart 객체에 request에서 파일 객체를 가져오는 부분. multipartHttpServletRequest의 getFile()메서드는 request에 저장된 파일의 name을 인자로 받는다.
			if(multipartFile.isEmpty() == false){ // 실제 파일 정보가 있는지 검사 한 후, 파일 정보 출력
				log.debug("----------- file start ----------");
				log.debug("name : " + multipartFile.getName());
				log.debug("filename : "+ multipartFile.getOriginalFilename());
				log.debug("size : "+multipartFile.getSize());
				log.debug("----------- file end -----------\n");
			}
		}
		*/
	}
	//답글 쓰기
	@Override
	public void insertReply(Map<String, Object> map, HttpServletRequest request) throws Exception{
		boardDAO.insertReply(map); //게시물 생성.
		//파일 업로드 정보 DB에 전달.
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(map, request); 
		//fileUtils에서 파일 저장 후 파일 정보를 DB에 저장하기위해 list에 담기.
		for(int i=0, size=list.size(); i<size; i++){
			boardDAO.insertFile(list.get(i));
		}
	}
	
	//조회수 증가
	@Override
	public void updateHitCnt(Map<String, Object> map) throws Exception {
		boardDAO.updateHitCnt(map);
	}
	@Override  //게시물 읽기.
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> tempMap = boardDAO.selectBoardDetail(map); 
		resultMap.put("map", tempMap); //게시물의 상세정보를 받아오고 resultMap에 저장
		
		List<Map<String, Object>> list = boardDAO.selectFileList(map); //첨부파일의 목록을 받아오기
		resultMap.put("list", list);
		
		return resultMap;
	}
	
//	@Override
//	public void updateBoard(Map<String, Object> map) throws Exception {
//		boardDAO.updateBoard(map);
//	}
	@Override
	public void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		boardDAO.updateBoard(map); //게시글 내용 수정
		//파일 목록 수정
		boardDAO.deleteFileList(map); //기존 파일 DEL_GB = "Y"로 변경. 우선 삭제 처리.
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(map, request); 
		Map<String, Object> tempMap = null;
		for(int i = 0, size=list.size(); i<size; i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){ //신규 파일과 기존 파일 구분 (게시글 수정 시 추가된 파일은 IS_NEW="Y")
				boardDAO.insertFile(tempMap); //신규 파일 일 경우 insert
			}else{
				boardDAO.updateFile(tempMap); //기존 파일 일 경우 update (DEL_GB = "N")
			}
		}
	}

//	@Override
//	public void deleteBoard(Map<String, Object> map) throws Exception {
//		boardDAO.deleteBoard(map);
//	}
	@Override
	public void deleteBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		boardDAO.deleteBoard(map);
		
	}
//	패스워드 체크
	@Override
	public int passCheck(Map<String, Object> map) throws Exception {
		int result= 0;
		result = boardDAO.passCheck(map);
		return result;
	}
}
