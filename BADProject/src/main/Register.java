package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class Register extends BorderPane{
	Label name,uname,pw,cpw,email,pnum, gender,agree;
	Button register,login,back;
	TextField Tname,Tuname,Temail,Tpnum;
	PasswordField Fpw,Fcpw;
	CheckBox cagree;
	
	RadioButton mgender,fgender;
	ToggleGroup groupgender;
	
	
	FlowPane fp,agre,btn;
	GridPane gp;
	BorderPane bp;
	Scene registersc;
	public Register() {
		bp = new BorderPane();
		registersc = new Scene(bp,700,700);
		
		name = new Label("Name");
		Tname = new TextField();
		
		uname = new Label("Username");
		Tuname= new TextField();
		
		pw = new Label("Password");
		Fpw = new PasswordField();
		
		cpw = new Label("Confirm Password");
		Fcpw= new PasswordField();
		
		email = new Label("email");
		Temail = new TextField();
		
		pnum = new Label("Phone Number");
		Tpnum = new TextField();
		
		gender = new Label("gender");
		agree = new Label("I agree with terms and condition");
		cagree = new CheckBox();
		register = new Button("Register");
		back = new Button("back to login");
		login = new Button("login");
		mgender= new RadioButton("Male");
		fgender= new RadioButton("Female");
		
		Tname.setPrefWidth(300);
		
		gp = new GridPane();
		fp = new FlowPane();
		agre=new FlowPane();
		btn = new FlowPane();
		
		groupgender = new ToggleGroup();
		
		fgender.setToggleGroup(groupgender);
		mgender.setToggleGroup(groupgender);
		btn.getChildren().addAll(register,login);
		
		fp.getChildren().addAll(mgender);
		fp.getChildren().addAll(fgender);
		agre.getChildren().addAll(cagree,agree);
		
		
		gp.setVgap(5);
		gp.setHgap(5);
		bp.setCenter(gp);
		
		gp.add(name, 0, 0);
		gp.add(Tname, 0, 1);
		gp.add(uname, 0, 2);
		gp.add(Tuname, 0, 3);
		gp.add(pw, 0, 4);
		gp.add(Fpw, 0, 5);
		gp.add(cpw, 0, 6);
		gp.add(Fcpw, 0, 7);
		gp.add(email, 0, 8);
		gp.add(Temail, 0,9);
		gp.add(pnum, 0,10);
		gp.add(Tpnum, 0, 11);
		gp.add(gender, 0, 12);
		gp.add(fp, 0, 13);
		gp.add(agre, 0, 14);
		gp.add(btn, 0, 15);
		
		gp.setAlignment(Pos.CENTER);
		
//		register.setOnAction((event) ->{
//			String getname = Tname.getText();
//			String getusername = Tuname.getText();
//			String getpassword = Fpw.getText();
//			String getconfirmpassword = Fcpw.getText();
//			String getemail = Temail.getText();
//			String getphonenumber = Tpnum.getText();
//			
//			Alert warningalert = new Alert(AlertType.WARNING);
//			if(getname.length()<5 || getname.length()>20) {
//				
//				Alert alert = new Alert(AlertType.WARNING);
//				
//				
//				alert.setContentText("Name must be between 5 to 20 word!");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//			} else if(getusername.length()<3 || getusername.length()>10) {
//				Alert alert = new Alert(AlertType.WARNING);
//				
//				
//				alert.setContentText("Username must be between 3 to 10 word!");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//			}else if(getpassword.length()<8) {
//				Alert alert = new Alert(AlertType.WARNING);
//				
//				
//				alert.setContentText("password must be >7 and alphanumeric");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//				
//			}else if(!getconfirmpassword.equals(getpassword)) {
//				Alert alert = new Alert(AlertType.WARNING);
//				//error,warning, none, confirmation
//				
//				alert.setContentText("Password doesn't match!");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//				
//			}else if(!getemail.endsWith("@gmail.com")) {
//				Alert alert = new Alert(AlertType.WARNING);
//			
//				
//				alert.setContentText("Email must be unique, and ends with @gmail.com");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//			}else if(getphonenumber.length() <10 || !getphonenumber.startsWith("+62")) {
//				Alert alert = new Alert(AlertType.WARNING);
//				
//				
//				alert.setContentText("Phonenumber must start with +62 and >=10 character ");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//			}
//			
//			else if(groupgender.getSelectedToggle()== null) {
//				Alert alert = new Alert(AlertType.WARNING);
//				
//				
//				alert.setContentText("Gender must be chosen");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//			}
//			else if(!cagree.isSelected()) {
//				Alert alert = new Alert(AlertType.WARNING);
//				//error,warning, none, confirmation
//				
//				alert.setContentText("you must agree with terms and condition");
//				alert.setHeaderText("Warning");
//				System.out.println(alert.getResult());
//				alert.show();
//			}
//			else {
//				
//				String getname2 = Tname.getText();
//				String getusername2 = Tuname.getText();
//				String getpassword2 = Fpw.getText();
//				String getconfirmpassword2 = Fcpw.getText();
//				String getemail2 = Temail.getText();
//				String getphonenumber2 = Tpnum.getText();
//				String getgender="";
//				String getid = "";
//				String roletest = "user";
//				Random random = new Random();
//			;
//				int a = random.nextInt(10);
//				int b = random.nextInt(10);
//				int c = random.nextInt(10);
//				int d = random.nextInt(10);
//				getid = "U"+a+b+d+c;
//				if (fgender.isSelected()) {
//					getgender = "female";
//				}
//				else if (mgender.isSelected()) {
//					getgender = "male";
//				}
//				
//					String query = String.format("INSERT INTO users VALUES('%s', '%s','%s','%s','%s','%s','%s','%s')",getid,getname2,roletest,getusername2,getpassword2,getemail2,getphonenumber2,getgender);
//					Connect connect = Connect.getConnection();
//					
//					Integer result = connect.exceuteUpdate(query);
//					Main main = new Main();
//					
//					if(result!= null ) {
//				
//					Alert	information = new Alert(Alert.AlertType.INFORMATION);
//					information.setContentText("succesfully registered an account");
//					information.setAlertType(Alert.AlertType.INFORMATION);
//					information.show();
//					Tname.setText("");
//					Tuname.setText("");
//					Fpw.setText("");
//					Fcpw.setText("");
//					Temail.setText("");
//					Tpnum.setText("");
//					cagree.setSelected(false);
//					fgender.setSelected(false);
//					mgender.setSelected(false);
//					
//					
//					}
//					else {
//						warningalert.setContentText("account registration failed");
//						warningalert.show();
//					}
//					
//				
//			}});
	}
	

}
