package com.spring.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils") // 객체 관리를 스프링에서.
public class FileUtils {
	Logger log = Logger.getLogger(this.getClass());
	
	private static final String filePath = "D:\\Project\\file\\";
	//파일이 저장될 path 선언.
	public List<Map<String,Object>> parseInsertFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//클라이언트에서 전송된 파일정보를 담아서 반환해줄 list 선언.
		Map<String, Object> listMap = null;
		
		String boardSeq = (String)map.get("SEQ");
		//신규 생성되는 게시물의 번호를 받아옴.
		String CreaName = (String)map.get("NAME");
		//신규 생성되는 게시물의 이름을 받아옴.
		File file = new File(filePath);
		if(file.exists() == false){
			file.mkdirs();
		}//저장할 경로에 폴더가 없으면 폴더 생성.
		
		while(iterator.hasNext()){
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false){
				originalFileName = multipartFile.getOriginalFilename(); //원본 파일명.
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); //원본 확장자 
				storedFileName = CommonUtils.getRandomString() + originalFileExtension;//랜덤으로 바꾼 언어에 확장자 붙임.
				
				file = new File(filePath + storedFileName); // file = d:/Project/file/파일명.확장자
				multipartFile.transferTo(file); 
				//실제 파일이 저장되는 부분. multipartFile.transferTo(경로); 지정한 경로에 파일을 생성.
				listMap = new HashMap<String, Object>();
				listMap.put("BOARD_SEQ", boardSeq);
				listMap.put("NAME", CreaName);
				listMap.put("ORIGINAL_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);
			}
		}
		return list;
	}
	public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		String boardSeq = (String)map.get("SEQ");
		String CreaName = (String)map.get("NAME");
		//신규 생성되는 게시물의 이름을 받아옴.
		String requestName = null;
		String file_seq = null;
		
		while(iterator.hasNext()){
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false){
				originalFileName = multipartFile.getOriginalFilename(); //원본 파일명.
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); //원본 확장자 
				storedFileName = CommonUtils.getRandomString() + originalFileExtension;//랜덤으로 바꾼 언어에 확장자 붙임.
			
				multipartFile.transferTo(new File(filePath + storedFileName));
				
				listMap = new HashMap<String, Object>();
				listMap.put("IS_NEW", "Y");
				listMap.put("BOARD_SEQ", boardSeq);
				listMap.put("NAME", CreaName);
				listMap.put("ORIGINAL_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);
			}else{
				requestName = multipartFile.getName();
				file_seq = "FILE_SEQ_"+requestName.substring(requestName.indexOf("_")+1);
				if(map.containsKey(file_seq) == true && map.get(file_seq) != null){
					listMap = new HashMap<String,Object>();
	                listMap.put("IS_NEW", "N");
	                listMap.put("FILE_SEQ", map.get(file_seq));
	                list.add(listMap);
				}
			}
		}
		return list;
	}
}
