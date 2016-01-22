package com.spring.common.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.spring.board.dao.AbstractDAO;

@Repository("commonDAO")
public class CommonDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectFileInfo(Map<String, Object> map)throws Exception{
		System.out.println("COMMONDAO에 map 어떤게 들어왔니?"+map.toString());
		return (Map<String, Object>)selectOne("common.selectFileInfo", map);
	}

}
