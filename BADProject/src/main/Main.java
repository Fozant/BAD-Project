package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Stage;

public class Main extends Application{
	

			

	Scene sceneutama;
	GridPane gp;
	BorderPane	bp;
	FlowPane flow;

	Label email ,Password;
	
	TextField emailTF;
	PasswordField pw;
	
	Button	 loginbtn,registerbtn;
	static String Userid;
	static Cart cart;
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane mainpane = new BorderPane();
		Register register = new Register();
		AdminPage adminpage = new AdminPage();
		Manageproduct manageproduct= new Manageproduct();
		
		Userpage userpage = new Userpage();
		Productlist productlist = new Productlist();
		cart = new Cart();
		
		Alert	warningalert = new Alert(AlertType.WARNING);
	
		
		gp = new GridPane();
		flow = new FlowPane();
		sceneutama = new Scene(mainpane,700,700);

		email = new Label("Email");
		Password = new Label("Password");
		emailTF = new TextField();
		pw = new PasswordField();
		loginbtn = new Button("login");
		registerbtn =new Button("register");;
		
		loginbtn.setOnAction((e) ->{
			Alert	ERRORalert = new Alert(AlertType.ERROR);
			String getemail = emailTF.getText();
			String getpassword = pw.getText();
			if(getemail.equals("")) {
				
			
			warningalert.setContentText("Email cannot be empty!");
			warningalert.setHeaderText("Warning");
			System.out.println(warningalert.getResult());
			warningalert.show();
			}
			else if (getpassword.equals("")) {
			
				
				warningalert.setContentText("password cannot be empty!");
				warningalert.setHeaderText("Warning");
				System.out.println(warningalert.getResult());
				warningalert.show();
			} 
			
			String query = " SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1";
			Connect connect = Connect.getConnection();
			PreparedStatement ps = connect.Prepare(query);
			
			try {
				
				ps.setString(1, emailTF.getText());
				ps.setString(2, pw.getText());
				ResultSet res = ps.executeQuery();
				
			
				if (res.next()) {
					Userid =res.getString("id");
					cart.terima();
					productlist.terima(Userid);
					
				
				String roleee =	res.getString("role");
				
				
					if (roleee.equalsIgnoreCase("user")) {
						System.out.println(Userid);
						System.out.println("----------");
						System.out.println(Userid);
						cart.refresh();
					}
					stage.setScene(userpage.sceneuser);
					
					 if (roleee.equalsIgnoreCase("admin")) {
						
						stage.setScene(adminpage.scnadmin);
					}
				}
				
				else {
					
					ERRORalert.setContentText("credential doesnt match ");
					ERRORalert.setHeaderText("Warning");
					System.out.println(ERRORalert.getResult());
					ERRORalert.show();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		});
		
	
		
		gp.setAlignment(Pos.CENTER);
		
		flow.getChildren().add(loginbtn);
		flow.getChildren().add(registerbtn);
		
		gp.add(email, 0, 0);
		gp.add(emailTF, 0, 1);
		gp.add(Password, 0, 2);
		gp.add(pw, 0, 3);
		gp.add(flow, 0, 4);
		mainpane.setCenter(gp);
		
		
		
		registerbtn.setOnAction((e) ->{
			emailTF.clear();
			pw.setText("");
		stage.setScene(register.registersc);
		
		});
	
		register.login.setOnAction((e) ->{
		stage.setScene(sceneutama);
		});
		
		register.register.setOnAction((event) ->{
			String getname = register.Tname.getText();
			String getusername = register.Tuname.getText();
			String getpassword = register.Fpw.getText();
			String getconfirmpassword = register.Fcpw.getText();
			String getemail = register.Temail.getText();
			String getphonenumber = register.Tpnum.getText();
			
		
			if(getname.length()<5 || getname.length()>20) {
				
				Alert alert = new Alert(AlertType.WARNING);
				
				
				alert.setContentText("Name must be between 5 to 20 word!");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
			} else if(getusername.length()<3 || getusername.length()>10) {
				Alert alert = new Alert(AlertType.WARNING);
				
				
				alert.setContentText("Username must be between 3 to 10 word!");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
			}else if(getpassword.length()<8) {
				Alert alert = new Alert(AlertType.WARNING);
				
				
				alert.setContentText("password must be >7 and alphanumeric");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
				
			}else if(!getconfirmpassword.equals(getpassword)) {
				Alert alert = new Alert(AlertType.WARNING);
				//error,warning, none, confirmation
				
				alert.setContentText("Password doesn't match!");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
				
			}else if(!getemail.endsWith("@gmail.com")) {
				Alert alert = new Alert(AlertType.WARNING);
			
				
				alert.setContentText("Email must be unique, and ends with @gmail.com");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
			}else if(getphonenumber.length() <10 || !getphonenumber.startsWith("+62")) {
				Alert alert = new Alert(AlertType.WARNING);
				
				
				alert.setContentText("Phonenumber must start with +62 and >=10 character ");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
			}
			
			else if(register.groupgender.getSelectedToggle()== null) {
				Alert alert = new Alert(AlertType.WARNING);
				
				
				alert.setContentText("Gender must be chosen");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
			}
			else if(!register.cagree.isSelected()) {
				Alert alert = new Alert(AlertType.WARNING);
				//error,warning, none, confirmation
				
				alert.setContentText("you must agree with terms and condition");
				alert.setHeaderText("Warning");
				System.out.println(alert.getResult());
				alert.show();
			}
			else {
				
				String getname2 = register.Tname.getText();
				String getusername2 = register.Tuname.getText();
				String getpassword2 = register.Fpw.getText();
				String getconfirmpassword2 = register.Fcpw.getText();
				String getemail2 = register.Temail.getText();
				String getphonenumber2 = register.Tpnum.getText();
				String getgender="";
				String getid = "";
				String roletest = "user";
				Random random = new Random();
			;
				int a = random.nextInt(10);
				int b = random.nextInt(10);
				int c = random.nextInt(10);
				int d = random.nextInt(10);
				getid = "U"+a+b+d+c;
				if (register.fgender.isSelected()) {
					getgender = "female";
				}
				else if (register.mgender.isSelected()) {
					getgender = "male";
				}
				String getEmailregis= register.Temail.getText();
				String query5 =String.format("SELECT *from users  where email ='%s'", getEmailregis);
				Connect connect = Connect.getConnection();
				PreparedStatement ps = connect.Prepare(query5);
				System.out.println(getEmailregis);
				System.out.println(getEmailregis);
				System.out.println(getEmailregis);
				try {
					ResultSet res = ps.executeQuery();
					
					if (res.next()) {
				
				
				
					
				
				
						Alert warningalertt = new Alert(AlertType.WARNING);
						warningalertt.setContentText("account EMAIL ALREADY EXIST failed");
						warningalertt.show();
					
					
					}
					else {
						String query = String.format("INSERT INTO users VALUES('%s', '%s','%s','%s','%s','%s','%s','%s')",getid,getname2,roletest,getusername2,getpassword2,getemail2,getphonenumber2,getgender);
						
						
						Integer result = connect.exceuteUpdate(query);
						
						

						Alert	information = new Alert(Alert.AlertType.INFORMATION);
						information.setContentText("succesfully registered an account");
						information.setAlertType(Alert.AlertType.INFORMATION);
						information.show();
						register.Tname.setText("");
						register.Tuname.setText("");
						register.Fpw.setText("");
						register.Fcpw.setText("");
						register.Temail.setText("");
						register.Tpnum.setText("");
						register.cagree.setSelected(false);
						register.fgender.setSelected(false);
						register.mgender.setSelected(false);
					}
					
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
		
		userpage.logout.setOnAction((e) ->{
			stage.setScene(sceneutama);
			});
		userpage.list.setOnAction((e) ->{
			stage.setScene(productlist.sceneproduklist);
			});
		
		userpage.cart.setOnAction((e) ->{
			stage.setScene(cart.scenecart);
			});
		
		productlist.cart.setOnAction((e) ->{
			stage.setScene(cart.scenecart);
			});
		productlist.logout.setOnAction((e) ->{
			stage.setScene(sceneutama);
			});
		cart.list.setOnAction((e) ->{
			stage.setScene(productlist.sceneproduklist);
			});
		cart.logout.setOnAction((e) ->{
			stage.setScene(sceneutama);
			});
		adminpage.logout.setOnAction((e) ->{
			stage.setScene(sceneutama);
			});
		adminpage.manage.setOnAction((e) ->{
			stage.setScene(manageproduct.scenemanage);
			});
		manageproduct.logout.setOnAction((e) ->{
			stage.setScene(sceneutama);
			});
		
		
//		
		stage.setScene(sceneutama);
		stage.setTitle("test");
		stage.show();
		
		
	}
	
	
	public static void main(String[] args) {
		
		launch(args);
	}


	public String getUserid() {
		return Userid;
	}


	public void setUserid(String userid) {
		Userid = userid;
	}
	
	
}
