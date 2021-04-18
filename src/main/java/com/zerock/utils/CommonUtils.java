package com.zerock.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

public class CommonUtils {
	@Value("#{global['db.driver']}") 
	public static String dbDriver;
	
	@Value("#{global['db.url']}") 
	public static String dbUrl;
	
	@Value("#{global['db.username']}") 
	public static String dbUser;
	
	@Value("#{global['db.password']}") 
	public static String dbPassword;
	
	@Value("#{global['file.path']}") 
	public static String filePath;
	
	// List의 null Check
	public static <T> boolean isNull(List<T> list) {
		return list == null || list.size() == 0;
	}
	
	// UUID 얻기
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	// yyyy-mm-dd 얻기
	public static String getDateWithHyphen(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
}
