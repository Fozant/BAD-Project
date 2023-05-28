package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.util.Random;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.Produk;
public class Manageproduct extends BorderPane {
	//test
	TableView<Produk> manageTabel = new TableView<>();
	
	BorderPane bp;
	FlowPane fp;
	GridPane gp;
	Scene scenemanage;
	Menu menu ,account;
	MenuBar menubar;
	MenuItem manage, logout;
	
	Connect connect = Connect.getConnection();
	
	TextField Tproduct,Tbrand;
	Label productid,brand,type,price,stock;
	Spinner<Integer> stockS;
	Spinner<Integer> priceS;
	Button insert,update,remove;
	ComboBox<String> Ctype;
	Random random = new Random();
	public Manageproduct() {
		bp = new BorderPane();
		fp = new FlowPane();
		gp = new GridPane();
		scenemanage = new Scene(bp,700,700);
		menubar = new MenuBar();
		menu = new Menu ("Menu");
		account = new Menu("Account");
		manage = new MenuItem("manage product");
		logout = new MenuItem("logout");
		
		
		menubar.getMenus().addAll(menu,account);
		menu.getItems().add(manage);
		account.getItems().add(logout);
		
		bp.setTop(menubar);
		
		productid=new Label("product id "); 
		brand=new Label("brand");
		type=new Label("type");
		price=new Label("price"); 
		stock=new Label("Stock");
		
		Tproduct = new TextField();
		Tbrand 	 = new TextField();
		
		Ctype = new ComboBox<String>();
		Ctype.getItems().addAll("--CHOOSE TYPE--","Sanitary","Drink","food");
		Ctype.getSelectionModel().selectFirst();
		
		
	 stockS	= new Spinner<>(30,1000,30);
	 priceS = new Spinner<>(1000,100000,1000);
	 
	 insert = new Button("insert");
	 update = new Button("update");
	 remove= new Button("remove");
		
	 gp.add(productid, 0, 0);
	 gp.add(Tproduct, 0, 1);
	 gp.add(brand, 0, 2);
	 gp.add(Tbrand, 0, 3);
	 gp.add(type, 0, 4);
	 gp.add(Ctype, 0, 5);
	 gp.add(price, 0, 6);
	 gp.add(priceS, 0, 7);
	 gp.add(stock, 1, 6);
	 gp.add(stockS, 1, 7);
	 gp.add(insert, 2, 0);
	 gp.add(update, 2, 2);
	 gp.add(remove, 2, 4);
	 
		gp.setVgap(5);
		gp.setHgap(10);
		gp.setPadding(new Insets(20));
		
		
		
		TableColumn<Produk, String> column1 = new TableColumn<>("ID");
	
		TableColumn<Produk, String> column3 = new TableColumn<>("type");
		TableColumn<Produk, String> column2 = new TableColumn<>("Brand");
		TableColumn<Produk, String> column4 = new TableColumn<>("price");
		TableColumn<Produk, String> column5 = new TableColumn<>("stock");
		
		column1.setCellValueFactory(new PropertyValueFactory<>("id"));
		column3.setCellValueFactory(new PropertyValueFactory<>("type"));
		column2.setCellValueFactory(new PropertyValueFactory<>("brand"));
		column4.setCellValueFactory(new PropertyValueFactory<>("price"));
		column5.setCellValueFactory(new PropertyValueFactory<>("stock"));
		manageTabel.getColumns().addAll(column1,column2,column3,column4,column5);
		fp.getChildren().add(manageTabel);
		
		
		bp.setTop(menubar);
		bp.setBottom(gp);
		bp.setCenter(fp);
		
		insert.setOnAction((e) ->{
			String getid = Tproduct.getText();
			String getbrand = Tbrand.getText();
			Alert	warningalert = new Alert(AlertType.WARNING);
		
			if(getid.equals("")) {
				
				
				warningalert.setContentText("ID cannot be empty!");
				warningalert.setHeaderText("Warning");
				System.out.println(warningalert.getResult());
				warningalert.show();}
			
			else if (getbrand.equals("")) {
				
				
				warningalert.setContentText("brand cannot be empty!");
				warningalert.setHeaderText("Warning");
				System.out.println(warningalert.getResult());
				warningalert.show();
				}		
			
			else if (Ctype.getSelectionModel().getSelectedIndex()==0) {
				
				
				warningalert.setContentText("item type must be choosen");
				warningalert.setHeaderText("Warning");
				System.out.println(warningalert.getResult());
				warningalert.show();
				}
			else {
				insert();
			}
			
			});
		update.setOnAction((e) ->{
			String getid = Tproduct.getText();
			String getbrand = Tbrand.getText();
			Alert	warningalert = new Alert(AlertType.WARNING);
		
			if(getid.equals("")) {
				
				
				warningalert.setContentText("ID cannot be empty!");
				warningalert.setHeaderText("Warning");
				System.out.println(warningalert.getResult());
				warningalert.show();}
			
			
			
			});
		remove.setOnAction((e) ->{
			String getid = Tproduct.getText();
			String getbrand = Tbrand.getText();
			Alert	warningalert = new Alert(AlertType.WARNING);
		
			if(getid.equals("")) {
				
				
				warningalert.setContentText("ID cannot be empty!");
				warningalert.setHeaderText("Warning");
				System.out.println(warningalert.getResult());
				warningalert.show();}
			
			remove ();

			});
		
		 try {
			refresh();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		 UPDATE();
		 
	}

	public void refresh() throws SQLException {
		manageTabel.getItems().clear();
		
		String query = "SELECT * FROM products ORDER BY id";
		
		
		PreparedStatement ps = connect.Prepare(query);
		ResultSet res = ps.executeQuery();
	
		

		
		try {
			while (res.next()) {
			Produk produk = new Produk();
				produk.setId(res.getString("id")); 
				produk.setType(res.getString("type"));
				produk.setBrand(res.getString("brand"));
				
				produk.setPrice(res.getInt("price"));
				produk.setStock(res.getInt("stock"));
				
				manageTabel.getItems().add(produk);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void insert() {
		insert.setOnAction((event) -> {
			String idmanage="";
			String getbrandd = Ctype.getSelectionModel().getSelectedItem();
			String typeee = Tbrand.getText();
			Integer PRICEEE = priceS.getValue();
			Integer STOOCK = stockS.getValue();
			int a = random.nextInt(10);
			int b = random.nextInt(10);
			int c = random.nextInt(10);
			int d = random.nextInt(10);
			char awal = ' ';
			String awals;
			awal = typeee.charAt(0);
			awals = String.valueOf(awal);
			idmanage = awals+a+b+c+d;
			
			String query = String.format("INSERT INTO products VALUES('%s', '%s','%s','%s','%s')",idmanage,getbrandd,typeee,PRICEEE,STOOCK);
			
			Integer result = connect.exceuteUpdate(query);
			
			
			if(result!= null ) {
		
			Alert	information = new Alert(Alert.AlertType.INFORMATION);
			information.setContentText("succesfully insert daata ");
			information.setAlertType(Alert.AlertType.INFORMATION);
			information.show();
		
			
			}
			else {
				Alert warningalertt = new Alert(AlertType.ERROR);
				warningalertt.setContentText("failed insert daata ");
				warningalertt.show();
			}
			
			
			
			
			
			try {
				refresh();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	public void UPDATE(){
		update.setOnAction((event) -> {
			String idmanage= Tproduct.getText() ;
			String getbrandd = Tbrand.getText();
			String typeee = Ctype.getSelectionModel().getSelectedItem();
			Integer PRICEEE = priceS.getValue();
			Integer STOOCK = stockS.getValue();
			String temp ="";
			
			
			String query5 =String.format("SELECT id,brand,price,stock "
					+ "	FROM products  where id ='%s'", idmanage);
			
			PreparedStatement ps = connect.Prepare(query5);
			
			
			
			String query = String.format("UPDATE products SET type= '%s' ,brand= '%s' , price= '%s', stock='%s' WHERE id ='%s' ",
					typeee,getbrandd,PRICEEE,STOOCK,idmanage);
			System.out.println(idmanage);
			System.out.println(idmanage);
			
			
			try {
				ResultSet res = ps.executeQuery();
				
				if (res.next()) {
					
					Integer result = connect.exceuteUpdate(query);
			
			
				
				refresh();
				
				
					
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setContentText("succesfully UPDATE daata ");
						information.setAlertType(Alert.AlertType.INFORMATION);
						information.show();
					
					
			}
				else {
					Alert warningalertt = new Alert(AlertType.ERROR);
					warningalertt.setContentText("ID NOT FOUND ");
					warningalertt.show();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
	}
	
		public void remove () {
			remove.setOnAction((event) -> {
				String idmanage= Tproduct.getText() ;
				
				String query5 =String.format("SELECT id,brand,price,stock "
						+ "	FROM products  where id ='%s'", idmanage);
				
				PreparedStatement ps = connect.Prepare(query5);
				String query = String.format("DELETE FROM products WHERE id = '%s' ", idmanage);
				try {
					ResultSet res = ps.executeQuery();
					
					if (res.next()) {
					
					Integer result = connect.exceuteUpdate(query);
					refresh();
					Alert information = new Alert(Alert.AlertType.INFORMATION);
					information.setContentText("succesfully remove daata ");
					information.setAlertType(Alert.AlertType.INFORMATION);
					information.show();
					}
					else {
						Alert warningalertt = new Alert(AlertType.ERROR);
						warningalertt.setContentText("ID NOT FOUND ");
						warningalertt.show();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				});
	}
}