package com.spring.common.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.spring.common.MemberVO;
import com.spring.common.dao.MemberDAO;

@Service("memberService")
public class MemberServieceImpl implements MemberService {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="memberDAO")
	private MemberDAO memberDAO;
	
	@Override
	public void insertMember(MemberVO member) throws Exception {
		memberDAO.insertMember(member);
	}

}
