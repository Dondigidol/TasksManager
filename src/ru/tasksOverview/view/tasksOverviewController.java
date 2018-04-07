package ru.tasksOverview.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class tasksOverviewController {
	private final DataFormat labelFormat = new DataFormat("ru.tasksOverview.formats.Label");
	private Label draggingLabel;
	@FXML
	AnchorPane tasksColumn = new AnchorPane();
	@FXML
	AnchorPane freeTasksColumn = new AnchorPane();
	@FXML
	VBox tasksContainer = new VBox();
	@FXML
	VBox freeTasksContainer = new VBox();
	
	public void getTasks() {
		 
		 tasksContainer.getChildren().add(createPane("Тестовое поле", true));
		 tasksContainer.getChildren().add(createPane("Тестовое поле 2", true));
		 
		 
		 
	}
	
	private Label createPane(String text, boolean draggable) {
		Label task = new Label(text);
		task.setMinSize(100, 30);
		task.setMaxSize(700, 100);
		task.setAlignment(Pos.CENTER);
		
		if (draggable) {
			task.setStyle("-fx-background-color: #99FF99");
			task.setOnDragDetected(e-> {
				Dragboard db = task.startDragAndDrop(TransferMode.MOVE);
				db.setDragView(task.snapshot(null, null));
				ClipboardContent cc = new ClipboardContent();
				cc.put(labelFormat, "pane");
				db.setContent(cc);
				draggingLabel = task;
			});
			task.setOnMouseEntered(new EventHandler<MouseEvent>() {			
				 public void handle(MouseEvent e) {
					task.getScene().setCursor(Cursor.MOVE);
				}
			 });		
			task.setOnMouseExited(new EventHandler<MouseEvent>() {
				 public void handle(MouseEvent e) {
					 task.getScene().setCursor(Cursor.DEFAULT);
				 }
			 });		
		}		
		return task;
	}
	
	private void addDropHandling(Pane pane) {
		pane.setOnDragOver(e-> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(labelFormat) && draggingLabel != null) {
				e.acceptTransferModes(TransferMode.MOVE);
				
			}
		});
		pane.setOnDragDropped(e-> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(labelFormat)) {
				((Pane)draggingLabel.getParent()).getChildren().remove(draggingLabel);
				pane.getChildren().add(draggingLabel);
				e.setDropCompleted(true);
			}
		});
		
	}
	
	
	
	
	
	
	@FXML
	public void initialize(){
		addDropHandling(tasksContainer);
		addDropHandling(freeTasksContainer);
		getTasks();
	}	
}
