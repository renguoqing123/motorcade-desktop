package com.motorcade.desktop.constants;


import java.io.IOException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * 控制器工厂
 * @author rengq
 *
 */
public class SpringFxmlLoader {

    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    public Parent load(String url) {
        try {
            URL sourseUrl = this.getClass().getResource(url);
            FXMLLoader loader = new FXMLLoader(sourseUrl);
            loader.setControllerFactory(applicationContext::getBean);
            return loader.load();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

	public static ApplicationContext getApplicationcontext() {
		return applicationContext;
	}
    
    
}