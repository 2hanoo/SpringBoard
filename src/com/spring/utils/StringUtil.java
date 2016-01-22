package com.spring.utils;


public class StringUtil {
	public static String parseBr(String msg){
		
		if(msg == null) return null;
		
		return msg.replace("\r\n", "<br>")
                  .replace("\n", "<br>");
	}
	public static String filterSpecialChars(String msg){
		if(msg == null) return null;
		
		msg = msg.replaceAll("&","&amp;");
		msg = msg.replaceAll("<","&lt;");
		msg = msg.replaceAll(">","&gt;");
		msg = msg.replaceAll("\"","&quot;");
		msg = msg.replaceAll("\'","&#x27;");
		msg = msg.replaceAll("/","&#x2F;");
		
		return msg;
	}
}
