package com.motorcade.desktop.client;

import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.motorcade.desktop.config.FeignApi;

import feign.Headers;
import feign.RequestLine;

@FeignApi
public interface MemberClient {
	
	@Headers({"Content-Type: application/json","Accept: application/json;charset=UTF-8"})
	@RequestLine("POST /member/list/all")
	public JSONObject queryMembers(@RequestBody JSONObject query);
	
}
