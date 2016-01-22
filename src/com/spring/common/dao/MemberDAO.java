package com.spring.common.dao;

import org.springframework.stereotype.Repository;

import com.spring.board.dao.AbstractDAO;
import com.spring.common.MemberVO;

@Repository("memberDAO")
public class MemberDAO extends AbstractDAO{

	public void insertMember(MemberVO member) throws Exception {
		insert("member.insertMember",member);
	}

}
