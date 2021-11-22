package com.motorcade.desktop.controller;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用分页
 * @author rengq
 *
 */
@Slf4j
public abstract class PageController {
	
	/*****通用分页*******/

	@FXML public Button first;
	@FXML public Button prev;
	@FXML public Button next;
	@FXML public Button last;
	
	@FXML public ComboBox comboBoxLine;	

	abstract void loadData(Integer pageNum, Integer pageSize);
	
	public void first() throws IOException{
		Object userData = first.getUserData();
		if(null != userData) {
			Integer pageNum = Integer.parseInt(String.valueOf(userData));
			loadData(pageNum,pageSize());
		}
	}

	public void prev() throws IOException{
		Object userData = prev.getUserData();
		if(null != userData) {
			Integer pageNum = Integer.parseInt(String.valueOf(userData));
			loadData(pageNum,pageSize());
		}
	}
	
	public void next() throws IOException{
		Object userData = next.getUserData();
		if(null != userData) {
			Integer pageNum = Integer.parseInt(String.valueOf(userData));
			loadData(pageNum,pageSize());
		}
	}
	
	public void last() throws IOException{
		Object userData = last.getUserData();
		if(null != userData) {
			Integer pageNum = Integer.parseInt(String.valueOf(userData));
			loadData(pageNum,pageSize());
		}
	}
	
	
	public Integer pageSize() {
		Object line = comboBoxLine.getValue();
    	Integer pageSize = 20;
    	if(null != line) {
    		pageSize = Integer.parseInt(String.valueOf(line));
    	}
		return pageSize;
	}
	
	@SuppressWarnings("unchecked")
	public void loadComboBoxLine() {
		ObservableList<String> apiList=FXCollections.observableArrayList();
		apiList.add("20");
        apiList.add("50");
        apiList.add("80");
        comboBoxLine.setItems(apiList);
        comboBoxLine.getSelectionModel().select(0);
        comboBoxLine.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				log.info("成功选中:"+newValue);
				//清空分页
				first.setUserData(null);
				prev.setUserData(null);
				next.setUserData(null);
				last.setUserData(null);
				Integer pageSize = Integer.parseInt(String.valueOf(newValue));
				loadData(0,pageSize);
			}
		});
	}
}
