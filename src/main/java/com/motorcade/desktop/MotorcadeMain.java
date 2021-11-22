package com.motorcade.desktop;

import com.motorcade.desktop.constants.SpringFxmlLoader;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MotorcadeMain extends Application {

    private static SpringFxmlLoader loader = new SpringFxmlLoader();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = loader.load("/fxml/login.fxml");
//			BorderPane root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setTitle("登录界面");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);//窗体缩放（默认为true）
	        primaryStage.centerOnScreen();// 设置让对话框处于屏幕中间
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
