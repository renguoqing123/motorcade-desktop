package com.motorcade.desktop.config;

public class Config{

	public static String getFeignHttp()  {
		return PropertiesUtil.getValue("feign.http");
	}

}
