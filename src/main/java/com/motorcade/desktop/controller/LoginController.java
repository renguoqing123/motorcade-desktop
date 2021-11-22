package com.motorcade.desktop.controller;

import org.springframework.stereotype.Controller;

import com.motorcade.desktop.constants.AlertMessageDialog;
import com.motorcade.desktop.constants.SpringFxmlLoader;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@Controller
public class LoginController extends BaseController{
	
    @FXML private Button closeButton;
    
    @FXML public TextField loginName;
    
    @FXML public TextField password;
    
    private static SpringFxmlLoader loader = new SpringFxmlLoader();
	
	/**
     * 登录按钮
     */
    public void login(){
    	if(checkLogin()) {return;}
        closeWin();
        homeShow();
        System.err.println("*************************************************************************************************");
        System.err.println("*********************************************欢迎登录车队管理系统*************************************");
        System.err.println("*************************************************************************************************");
        System.err.println("**********************************************车队管理系统欢迎您****************************************");
        System.err.println("*************************************************************************************************");
    }

    public Boolean checkLogin() {
    	String login = loginName.getText();
    	String pwd = password.getText();
    	Stage stage=(Stage)closeButton.getScene().getWindow();
    	if(null == login || "".equals(login)) {
    		AlertMessageDialog.createAlertDialog("用户名不能为空！", new Exception("用户名不能为空！"),stage);
    		return Boolean.TRUE;
    	}
    	if(null == pwd || "".equals(pwd)) {
    		AlertMessageDialog.createAlertDialog("密码不能为空！", new Exception("用户名不能为空！"),stage);
    		return Boolean.TRUE;
    	}
    	return Boolean.FALSE;
	}

	//退出程序
    public void closeButton(){
        Stage stage=(Stage)closeButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }


    //关闭当前窗体
    public void closeWin(){
        System.err.println("关闭当前窗体");
        Stage stage=(Stage)closeButton.getScene().getWindow();
        stage.close();
    }
    
    public void homeShow() {
    	Stage stage = new Stage();
        stage.setTitle("欢迎进入车队管理系统");
//        Pane pane = new Pane();
//        pane.getChildren().setAll(loader.load("/fxml/home.fxml"));
        Parent root = loader.load("/fxml/home.fxml");
//        stage.getIcons().add(new Image(
//                ResolverUtil.Test.class.getResourceAsStream("/images/logo.png")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);  //默认窗口最大化
        stage.centerOnScreen();// 设置让对话框处于屏幕中间
        /*stage.setResizable(false);*/ //禁止窗体缩放
        stage.show();
        
        Thread th =new Thread(new Runnable() {
			
			@Override
			public void run() {
				initSynGeteWay();//同步数据
				ObservableList<Node> node = root.getChildrenUnmodifiable();
				for(Node child:node) {
					if( child instanceof AnchorPane) {
						AnchorPane a =(AnchorPane)child; 
						a.setVisible(false);
						a.setManaged(false);
					}
				}
			}
		});
        
        th.start();
	}
    
}
