package com.spring.board.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.service.BoardService;
import com.spring.common.CommandMap;
import com.spring.utils.PagingUtil;

@Controller // 현재 클래스가 컨트롤러다.
public class BoardController {
	Logger log = Logger.getLogger(this.getClass());
	private int pageSize = 10;
	private int blockCount = 10;

	// Service 영역 접근을 위한 선언. boardServiceImpl를 사용하기 위한 선언. 필요한 Bean을 수동으로 등록.
	@Resource(name = "boardService")
	private BoardService boardService;

	@RequestMapping(value = "/board/openBoardList.do") // 디스패쳐서블릿이 이 어노테이션을 기준으로
														// 컨트롤러의 메소드가 호출되어야할지 결정
	public ModelAndView openBoardList(Map<String, Object> commandMap,
			@RequestParam(value = "pageNum", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyField", defaultValue = "") String keyField,
			@RequestParam(value = "keyWord", defaultValue = "") String keyWord) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardList");
		// ""에서 이 컨트롤러가 실행되고 나서 보여줄 view를 설정.

		String pagingHtml = "";
		commandMap.put("keyField", keyField);
		commandMap.put("keyWord", keyWord);
		int count = boardService.getCount(commandMap);

		PagingUtil page = new PagingUtil(keyField, keyWord, currentPage, count, pageSize, blockCount,
				"openBoardList.do");
		pagingHtml = page.getPagingHtml().toString();
		commandMap.put("start", Integer.valueOf(page.getStartCount()));
		commandMap.put("end", Integer.valueOf(page.getEndCount()));

		List<Map<String, Object>> list = null;
		if (count > 0) {
			list = boardService.selectBoardList(commandMap);
		} else {
			list = Collections.emptyList();
		}

		int number = count - (currentPage - 1) * this.pageSize;
		mv.addObject("count", Integer.valueOf(count));
		mv.addObject("currentPage", Integer.valueOf(currentPage));
		mv.addObject("pagingHtml", pagingHtml);
		mv.addObject("list", list);
		// 서비스 로직의 결과를 ModelAndView 객체에 담아서 클라이언트(JSP)에서 결과를 사용할 수 있도록 한다.
		// "list"라는 이름은 sampleService.selectBoardList 메서드에서 얻어온 결과인 list를
		// "list"로 저장한 것이다.
		// 키워드 키필드
		mv.addObject("keyField", keyField);
		mv.addObject("keyWord", keyWord);
		mv.addObject("pageNum", currentPage);

		mv.addObject("number", Integer.valueOf(number));
		return mv;
	}

	// 글쓰기폼
	@RequestMapping(value = "/board/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardWrite");
		return mv;
	}

	// 글쓰기
	@RequestMapping(value = "board/insertBoard.do")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
		boardService.insertBoard(commandMap.getMap(), request);
		return mv;
	}
	// 답글쓰기폼
	@RequestMapping(value = "/board/openBoardReply.do")
	public ModelAndView openBoardReply(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardReply");
		Map<String, Object> map = boardService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		return mv;
	}
	
	// 답글쓰기
	@RequestMapping(value = "board/insertReply.do")
	public ModelAndView insertReply(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
		boardService.insertReply(commandMap.getMap(), request);
		return mv;
	}

	// 글 상세보기
	@RequestMapping(value = "board/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		Map<String, Object> map = null;
		boardService.updateHitCnt(commandMap.getMap());
		map = boardService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map")); // 기존 게시물 정보를 갖고있음
		mv.addObject("list", map.get("list")); // 첨부파일의 목록을 갖고있음.
		return mv;
	}

	// 패스워드 체크
	@ResponseBody
	@RequestMapping(value = "/board/passCheck.do",method=RequestMethod.POST)
	public String passCheck(CommandMap commandMap) throws Exception {
		int result = 0;
		result = boardService.passCheck(commandMap.getMap());
		return result+"";
	}
	
	// 글 수정폼
	@RequestMapping(value = "/board/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardUpdate");
		Map<String, Object> map = boardService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		return mv;
	}

	// 글 수정
	@RequestMapping(value = "/board/updateBoard.do")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/board/openBoardDetail.do");
		boardService.updateBoard(commandMap.getMap(), request);
		mv.addObject("SEQ", commandMap.get("SEQ"));
		return mv;
	}

	// 글 삭제
	@RequestMapping(value = "/board/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
		boardService.deleteBoard(commandMap.getMap(), request);
		mv.addObject("SEQ", commandMap.get("SEQ"));
		return mv;
	}
}
