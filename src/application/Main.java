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
import javafx.scene.control.ComboBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(this);
			Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("MailboxSimButtons.fxml"));
			
			newRfid.setDisable(true);
			
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
	private Button newRfid = new Button();
	
	@FXML
	private Button wrongRfid = new Button();
	
	@FXML
	private ComboBox<String> regRfid = new ComboBox<String>();
	
	
	
	@FXML public void insideBtnPushed(ActionEvent event){
		newRfid.setDisable(false);
	}
	
	@FXML public void scanNewRfid(ActionEvent event){
	}
	
	@FXML public void scanRegRfid(ActionEvent event){
	}
	
	@FXML public void scanWrong(ActionEvent event){
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}





