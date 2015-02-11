package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(this);
			Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("MailboxSimButtons.fxml"));
			
			newRfid.setDisable(true);
			scanner.setDisable(true);
			
			
			Scene scene = new Scene(root,400,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@FXML
	private CheckBox isPost = new CheckBox();
	
	@FXML
	private Button insideButton = new Button();
	
	@FXML
	private Button scanBtn = new Button();
	
	@FXML
	private TextField scanner = new TextField();
	
	@FXML
	private TextField newRfid = new TextField();
	
	
	
	
	
	@FXML public void insideBtnPushed(ActionEvent event){
		newRfid.setDisable(false);
	}
	
	@FXML public void scanNewRfid(ActionEvent event){
		
	}
	
	@FXML public void scanRfid(ActionEvent event){
		scanner.setDisable(false);
	}
	
	@FXML public void checkRfid(ActionEvent event){
		
	}
	
	@FXML public void isPost(ActionEvent event){
		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}





