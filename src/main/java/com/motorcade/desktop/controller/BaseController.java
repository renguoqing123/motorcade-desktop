package com.motorcade.desktop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.motorcade.desktop.constants.SpringFxmlLoader;
import com.motorcade.desktop.service.SynDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * 公共抽象类
 * @author rengq
 *
 */
@Slf4j
public class BaseController extends PageController{

	public static SpringFxmlLoader loader = new SpringFxmlLoader();
	
	public static Map<String,String> mapTreeCache = new HashMap<String,String>();

	@Override
	void loadData(Integer pageNum, Integer pageSize) {
		loadData(pageNum, pageSize);
	}
	
	@Autowired
	private SynDataService synDataService;
	
	//连网同步线上数据到本地
	public void initSynGeteWay() {
		System.out.println("加载开始");
		try {
			synDataService.synDataInitialize();
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			log.error("InterruptedException:{}",e);
		} catch (Exception e){
			log.error("Exception:{}",e);
		}
		System.out.println("加载完毕");
	}
}
