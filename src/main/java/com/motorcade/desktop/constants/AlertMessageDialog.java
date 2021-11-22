package com.motorcade.desktop.constants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * 提示框
 * @author rengq
 *
 */
public class AlertMessageDialog {
	
	/**
	 * 确认对话框
	 * @param header
	 * @param message
	 * @param stage
	 * @return
	 */
	public static boolean alertConfirmDialog(String header, String message,Stage stage) {
		// 按钮部分可以使用预设的也可以像这样自己 new 一个
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, new ButtonType("取消", ButtonData.NO),
				new ButtonType("确定", ButtonData.YES));
		// 设置窗口的标题
		alert.setTitle("确认");
		alert.setHeaderText(header);
		// 设置对话框的 icon 图标，参数是主窗口的 stage
		alert.initOwner(stage);
		// showAndWait() 将在对话框消失以前不会执行之后的代码
		Optional<ButtonType> buttonType = alert.showAndWait();
		// 根据点击结果返回
		if (buttonType.get().getButtonData().equals(ButtonData.YES)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 信息框
	 * @param header
	 * @param message
	 * @param stage
	 */
	public static void alertInformationDialog(String header, String message,Stage stage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("信息");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.show();
    }
	
	/**
	 * 错误消息提示框
	 * @param content
	 * @param errorMsg
	 * @param stage
	 */
	public static void createAlertDialog(final String content, final Exception errorMsg,Stage stage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeight(200);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.initOwner(stage);
		// Create expandable Exception.
		GridPane expContent = createExpandablePane(errorMsg);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
	}

	private static GridPane createExpandablePane(final Exception errorMsg) {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		errorMsg.printStackTrace(pWriter);
		String exceptionText = sWriter.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		return expContent;
	}
}
