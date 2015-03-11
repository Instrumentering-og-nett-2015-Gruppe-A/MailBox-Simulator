package application;
	
import java.util.ArrayList;

import mailboxService.MailboxService;
import mailboxService.RealMailboxService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;





public class Main extends Application {
	
	public MailboxService mailboxService = new RealMailboxService();

	private int mailboxId = -1;
	private ArrayList<String> m_rfids;
	
	
	@Override
	public void start(Stage primaryStage) {
	
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(this);
			Parent root = (Parent) fxmlLoader.load(this.getClass().getResourceAsStream("MailboxSimButtons.fxml"));
	
			mailboxId = -1;
			
			newRfid.setDisable(true);
			scanner.setDisable(true);
			
			opensMailbox.setOpacity(0.0);
			wrongRfid.setOpacity(0.0);

			Scene scene = new Scene(root,400,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private CheckBox postSensor = new CheckBox();
	
	@FXML
	private Button insideButton = new Button();
	
	@FXML
	private Button scanBtn = new Button();
	
	@FXML
	private Button register = new Button();
	
	@FXML
	private TextField scanner = new TextField();
	
	@FXML
	private TextField newRfid = new TextField();
	
	@FXML
	private TextField mailboxIdField = new TextField();
	
	@FXML
	private TextField serverIPField = new TextField();
	
	@FXML
	private Label wrongRfid = new Label();
	
	@FXML
	private Label opensMailbox = new Label();
	
	@FXML
	private TextArea lcdPanel = new TextArea();
	
	
	@FXML public void register(ActionEvent event){
		if (mailboxId == -1){
			mailboxId = mailboxService.registerMailbox();
			lcdUpdater(mailboxId);
			System.out.println("MailboxId from registration is:"+mailboxId);
		}
	}
	
	@FXML public void useServerIP(ActionEvent event){
		String serverIPstr =  serverIPField.getText();
		mailboxService.setServerIP(serverIPstr);
		if (mailboxId != -1){
			lcdUpdater(mailboxId);
		}
		System.out.println("ServerIP from gui is:"+serverIPstr);
	}
	
	@FXML public void setMailboxId(ActionEvent event){
		String mailboxIdstr =  mailboxIdField.getText();
		mailboxId = Integer.parseInt(mailboxIdstr);
		System.out.println("MailboxId from gui is:"+mailboxId);
		if ((serverIPField.getText()).length() > 0 ){
			lcdUpdater(mailboxId);
		}
	}
	
	@FXML public void insideBtnPushed(ActionEvent event){
		newRfid.setDisable(false);
	}
	
	@FXML public void scanNewRfid(ActionEvent event){
		mailboxService.registerRFID(newRfid.getText(),mailboxId);
		System.out.println("New Rfid registered.");
	}
	
	@FXML public void scanRfid(ActionEvent event){
		scanner.setDisable(false);
	}
	
	@FXML public boolean checkRfid(ActionEvent event){
		ArrayList<String> new_rfids = mailboxService.getRFIDForMailbox(mailboxId);
		if (new_rfids != null){
			m_rfids = new_rfids;
		}
		if (m_rfids.contains(scanner.getText())){
			System.out.println("Opens mailBox.");
			opensMailbox.setOpacity(1.0);
			wrongRfid.setOpacity(0.0);
			return true;
		}
		else {
			System.out.println("Wrong card, Rfid not found.");
			wrongRfid.setOpacity(1.0);
			opensMailbox.setOpacity(0.0);
			return false;
		}		
	}
	
	public void updateLCDText(int mailboxID){
		String newText = mailboxService.getLCDText(mailboxID);
		if (newText != ""){
			lcdPanel.setText(newText);
		}
	}
	
	public void lcdUpdater(int mailboxID){	
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(2500),
		        ae -> updateLCDText(mailboxId)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
		
	@FXML public void isPost(ActionEvent event){
		if (postSensor.isSelected() == true){
			mailboxService.updateMailboxStatus(true, mailboxId);
			System.out.println("You have post.");
		}	
		else {
			mailboxService.updateMailboxStatus(false,mailboxId);
			System.out.println("You don't have post.");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}





