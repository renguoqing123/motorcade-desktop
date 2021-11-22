package com.motorcade.desktop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.motorcade.desktop.constants.AlertMessageDialog;
import com.motorcade.desktop.dao.ResourceDao;
import com.motorcade.desktop.model.Resource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController extends BaseController implements Initializable{
	
	@Autowired
	private ResourceDao resourceDao;
	
	@FXML private TreeView tv;
	
	@FXML private Label clickedPair;
	
	@FXML private Pane homePane;
	
	@FXML private AnchorPane anchorPaneStage;
	
	@FXML private AnchorPane anchorPaneBly;
	
	@FXML private AnchorPane anchorPaneButton;
	
	@FXML private AnchorPane loadAnchorPane;

	@FXML private TableView tableViewResource;
	
	@FXML private TableColumn<?, ?> ID;
	@FXML private TableColumn<?, ?> RES_NAME;
	@FXML private TableColumn<?, ?> PARENT_ID;
	@FXML private TableColumn<?, ?> PARENT_RES_NAME;
	@FXML private TableColumn<?, ?> RES_TYPE;
	@FXML private TableColumn<?, ?> URL;
	@FXML private TableColumn<?, ?> SORT_INDEX;
	@FXML private TableColumn<?, ?> CREATE_TIME;
	@FXML private TableColumn BJ;
	@FXML private TableColumn SC;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clickedPair.setText("菜单项");
		loadAnchorPane();
		initTreeView();
	}

	private void loadAnchorPane() {
		System.out.println("加载中。。。。。");
		Label label = new Label();
		label.setAlignment(Pos.CENTER);
		label.setText("正在加载资源，请稍后。。");
		ProgressBar pb=new ProgressBar();
		pb.setPrefWidth(200.0);
		final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(label, pb);
        loadAnchorPane.setTopAnchor(hb, 0.0);
        loadAnchorPane.setLeftAnchor(hb, 0.0);
        loadAnchorPane.setRightAnchor(hb, 0.0);
        loadAnchorPane.setBottomAnchor(hb, 0.0);
		loadAnchorPane.getChildren().addAll(hb);
	}

	private void initTreeView() {
		saleTreeValue();
		tv.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Node node = event.getPickResult().getIntersectedNode();
                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    String name = (String) ((TreeItem)tv.getSelectionModel().getSelectedItem()).getValue();
                    System.out.println("Node click: " + name);
                    double newWidth = anchorPaneBly.getWidth();
                    double newHeight = anchorPaneBly.getHeight();
                    System.out.println(newWidth+"---"+newHeight);
                    AnchorPane pane = new AnchorPane();//面板
                	pane.setPrefWidth(newWidth);
                	pane.setPrefHeight(newHeight);
                    String url = mapTreeCache.get(name);
                    if(!StringUtils.isEmpty(url)) {
                    	Parent parent = loader.load(url);
                        pane.getChildren().setAll(parent);
                    }
//                	homePane.setPrefWidth(newWidth);
//                	homePane.setPrefHeight(newHeight);
                    homePane.getChildren().setAll(pane);
//                  addListener(anchorPaneBly,pane,parent);
                }
				
			}

		});
	}
	
	/**
	 * 监听窗口变化
	 * @param homePane 
	 * @param pane 
	 * @param parent 
	 * @param anchorPaneBly 
	 */
//	private void addListener(AnchorPane anchorPaneBly,AnchorPane pane,Parent parent) {
//		Stage stage=(Stage)anchorPaneStage.getScene().getWindow();
//		stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				System.out.println("窗口变化监听："+oldValue+newValue);
//				Stage stageWin=(Stage)anchorPaneBly.getScene().getWindow();
//                double newWidth = stageWin.getWidth();
//                double newHeight = stageWin.getHeight();
//                System.out.println("窗口变化监听:"+newWidth+"---"+newHeight);
//                System.out.println("窗口变化监听:"+stageWin.getX()+"---"+stageWin.getY());
//                homePane.getChildren().setAll(pane);
//			}
//			
//		});
//	}
	
	/**
	 * 菜单项
	 */
	private void saleTreeValue() {
		TreeItem<String> itemRoot = new TreeItem<>("根节点");
		itemRoot.setExpanded(false);//是否是展开状态
        
		List<Resource> list = resourceDao.findAll(new Sort(Direction.ASC, "sortIndex"));
		log.info("list:{}", JSON.toJSON(list));
		if(!CollectionUtils.isEmpty(list)) {
			TreeItem<String> rootItem=null;
			Integer id = 0;
			for(int i=0;i<list.size();i++) {
				Resource resource = list.get(i);
				String resName = resource.getResName();
				String url = resource.getUrl();
				Integer resType = resource.getResType();
				Integer parentid = resource.getParentId();
				if(null==parentid && null!=rootItem) {
					itemRoot.getChildren().add(rootItem);
				}
				if(resType==0) {
					id = resource.getId();
					rootItem = new TreeItem<String> (resName);
					rootItem.setExpanded(true);
					continue;
				}
				if(resType==1 && parentid==id) {
					TreeItem<String> item = new TreeItem<> (resName);
					rootItem.getChildren().add(item);
					//cache tree 
					mapTreeCache.put(resName, url);
				}
			}
			itemRoot.getChildren().add(rootItem);
		}
        tv.setShowRoot(false);// 根节点隐藏
        tv.setRoot(itemRoot);
	}
	
	@FXML
	public void resouceAdmin() throws IOException {
		double newWidth = anchorPaneBly.getWidth();
        double newHeight = anchorPaneBly.getHeight();
        System.out.println(newWidth+"---"+newHeight);
        AnchorPane pane = new AnchorPane();//面板
    	pane.setPrefWidth(newWidth);
    	pane.setPrefHeight(newHeight);
    	Parent parent = loader.load("/fxml/resouce/resouce.fxml");
    	pane.getChildren().setAll(parent);
    	homePane.getChildren().setAll(pane);
    	
    	//加载comboBoxLine
    	loadComboBoxLine();
    	
    	loadData(0,pageSize());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadData(Integer pageNum,Integer pageSize) {
		Sort s = new Sort(Direction.ASC, "sortIndex");
		Pageable pageable = PageRequest.of(pageNum, pageSize, s);
    	Page<Resource>  page = resourceDao.findAll(pageable);
		List<Resource> resources = page.getContent();
    	Pageable pagePrevious = page.previousPageable();//上一页
    	Pageable pageNext = page.nextPageable();//下一页
    	first.setUserData(0);
    	if(page.hasPrevious()) {
        	prev.setUserData(pagePrevious.getPageNumber());
        }
        if(page.hasNext()) {
        	next.setUserData(pageNext.getPageNumber());
        }
    	last.setUserData(page.getTotalPages()-1);
    	ObservableList<Resource> list = FXCollections.observableArrayList();
    	for(Resource resource:resources) {
    		list.add(resource);
    	}
    	ID.setCellValueFactory(new PropertyValueFactory("id"));
    	RES_NAME.setCellValueFactory(new PropertyValueFactory("resName"));
    	PARENT_ID.setCellValueFactory(new PropertyValueFactory("parentId"));
    	PARENT_RES_NAME.setCellValueFactory(new PropertyValueFactory("parentResName"));
    	RES_TYPE.setCellValueFactory(new PropertyValueFactory("resType"));
    	URL.setCellValueFactory(new PropertyValueFactory("url"));
    	SORT_INDEX.setCellValueFactory(new PropertyValueFactory("sortIndex"));
    	CREATE_TIME.setCellValueFactory(new PropertyValueFactory("createTime"));
    	
    	//添加按钮
    	BJ.setCellFactory((col)->{
    	    
            //Resource换成你自己的实体名称
            TableCell<Resource, String> cell = new TableCell<Resource, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Button button1 = new Button("编辑");
                    button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff;-fx-alignment: CENTER");
                    button1.setOnMouseClicked((col) -> {

                        //获取list列表中的位置，进而获取列表对应的信息数据
                    	Resource resource = list.get(getIndex());
                        //按钮事件自己添加
                    	System.out.println("resource:"+resource);
                    	System.out.println("getIndex():"+getIndex());
                    });

                    if (empty) {
                        //如果此列为空默认不添加元素
                        setText(null);
                        setGraphic(null);
                    } else {
                    	//加载按钮
                        this.setGraphic(button1);
                    }
                }
            };
            return cell;
        });
    	
    	
    	SC.setCellFactory((col)->{
    	    
            //Resource换成你自己的实体名称
            TableCell<Resource, String> cell = new TableCell<Resource, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Button button1 = new Button("删除");
                    button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff;-fx-alignment: CENTER");
                    button1.setOnMouseClicked((col) -> {

                        //获取list列表中的位置，进而获取列表对应的信息数据
                    	Resource resource = list.get(getIndex());
                        //按钮事件自己添加

                    });

                    if (empty) {
                        //如果此列为空默认不添加元素
                        setText(null);
                        setGraphic(null);
                    } else {
                    	//加载按钮
                        this.setGraphic(button1);
                    }
                }
            };
            return cell;
        });
    	tableViewResource.setEditable(true);
    	tableViewResource.setItems(list);
	}
	
	@FXML
	public void exitLogin() throws IOException {
		System.err.println("关闭当前窗体");
		Stage stage =(Stage)tv.getScene().getWindow();
		stage.close();
		Stage primaryStage=new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setTitle("登录界面");
		primaryStage.setScene(scene);
		/*primaryStage.setResizable(false);*///窗体缩放（默认为true）
		primaryStage.show();
	}
	
	@FXML
	public void pullData() {
		Stage stage = (Stage) tv.getScene().getWindow();
		if(AlertMessageDialog.alertConfirmDialog("确认要从服务器拉取数据吗","如果拉取失败，请检查网络是否正常。",stage)) {
			System.err.println("开始从服务器拉取数据");
			AlertMessageDialog.alertInformationDialog("拉取成功！","数据已存入本地。",stage);
		}
	}
	
	@FXML
	public void pushData() {
		Stage stage = (Stage) tv.getScene().getWindow();
		if(AlertMessageDialog.alertConfirmDialog("确认要全量提交本地数据吗","如果提交失败，请检查网络是否正常。注：该操作不可逆。",stage)) {
			System.err.println("开始全量同步提交数据");
			AlertMessageDialog.alertInformationDialog("提交成功！","远程服务器数据接收完成。",stage);
		}
	}
}
