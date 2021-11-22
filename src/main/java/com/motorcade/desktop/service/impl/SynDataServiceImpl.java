package com.motorcade.desktop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.motorcade.desktop.client.MemberClient;
import com.motorcade.desktop.service.SynDataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value="synDataService")
public class SynDataServiceImpl implements SynDataService{

	@Autowired
	private MemberClient memberClient;
	
	@Override
	public void synDataInitialize() {
		log.info("---------------start加载资源到本地---------------");
		JSONObject js = new JSONObject();
		js.put("page", "0");
		js.put("size", "10");
		JSONObject data = memberClient.queryMembers(js);
		log.info("---------------end加载资源到本地---------------");
	}

}
