package com.spring.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.common.CommandMap;
import com.spring.common.MemberVO;
import com.spring.common.service.MemberService;

@Controller
public class MemberController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@ModelAttribute
	public MemberVO initMemberVO(){
		return new MemberVO();
	}
	//회원가입 폼
	@RequestMapping("/member/openMemberJoin.do")
	public String openMemberJoin(HttpSession session) throws Exception {
		if(session.getAttribute("sid")!=null){
			return "redirect:/board/openBoardList.do";
		}
		return "/member/memberJoin";
	}
	
	@RequestMapping("/member/insertMember.do")
	public String insertMember(@ModelAttribute("member") @Valid MemberVO member, BindingResult result) throws Exception {

		if(result.hasErrors()){
			if(log.isDebugEnabled()){
				log.debug("필수입력 안했어요~");
			}
			return "/member/memberJoin";
		}
		
		if(log.isDebugEnabled()){
			log.debug("가입 입력 내용 : "+member.toString());
		}
		//이미 중복된 아이디가 존재
//		if(memberService.idCheck(member.getMemid())>0){
//			if(log.isDebugEnabled()){
//				log.debug("이미 존재하는 아이디");
//			}
//			result.reject("dualID", new Object[]{member.getMemid()}, null);
//			return "memberJoin";
//		}
//		if(log.isDebugEnabled()){
//			log.debug("새로운 아이디");
//		}
		memberService.insertMember(member);
		return "redirect:/board/openBoardList.do";
	}
}
