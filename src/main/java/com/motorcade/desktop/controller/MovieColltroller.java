package com.motorcade.desktop.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

@Controller
public class MovieColltroller implements Initializable{
	
	@FXML private StackPane stackPane;
	
	@FXML private Pane pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		Pane paneParent = (Pane)stackPane.getParent();
//		double ds= paneParent.getPrefHeight();
//		double d =paneParent.getPrefWidth();
	}

}
