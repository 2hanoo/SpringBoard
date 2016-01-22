package com.spring.utils;

import java.util.UUID;

public class CommonUtils {

	public static String getRandomString(){
		return UUID.randomUUID().toString().replace("-", "");
		//ramdomUUID는 32글자의 랜덤한 문자열을 만들어서 반환해주는 기능
	}
}
