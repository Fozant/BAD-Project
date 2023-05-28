package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import data.Produk;
public class Productlist extends BorderPane {
	
	Vector<Produk> vektor = new Vector<>();
	TableView<Produk> manageTabel = new TableView<>();
	BorderPane bp;
	Scene sceneproduklist;
	MenuBar menubar;
	Menu menu, account;
	MenuItem list,cart, logout;
	String userID;
	Label productid,qty,totalprice;
	TextField id;
	String terimak,b;
	Button add,repress;
	Spinner<Integer> sqty;
	Cart kerangjang;
	Connect connect = Connect.getConnection();
	public Productlist() {
		terima(b);
		
		bp= new BorderPane();
		FlowPane fp = new FlowPane();
		GridPane gp= new GridPane();
		
		sceneproduklist = new Scene(bp,700,700);
		
		menubar = new MenuBar();
		menu = new Menu ("Menu");
		account = new Menu("Account");
		list = new MenuItem("Product list");
		cart = new MenuItem("cart");
		logout = new MenuItem("logout");
		
		
		menubar.getMenus().addAll(menu,account);
		menu.getItems().addAll(list,cart);
		account.getItems().add(logout);
		
		
		
		productid = new Label("product id ");
		qty = new Label("qty");
		totalprice = new Label("total price");

		id = new TextField();
		sqty = new Spinner<>(1,100,1);
		add = new Button("add to cart");
		repress = new Button("PRESS HERE TO REFRESH TABLE");
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
		gp.add(fp, 0, 0);
		gp.add(totalprice, 0, 1);
		gp.add(productid, 0, 4);
		gp.add(id, 0, 5);
		gp.add(qty, 0, 7);
		gp.add(sqty, 0, 8);
		gp.add(add, 0, 9);
		
		bp.setTop(menubar);
		gp.setVgap(5);
		bp.setCenter(gp);
		manageTabel.getItems().clear();
		bp.setRight(repress);
		
			addtocart();
		
		
			try {
				refresh();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			repress.setOnAction((event) -> {
				try {
					refresh();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		
	}
	public void refresh() throws SQLException  {

		manageTabel.getItems().clear();
		
		String query = "SELECT * FROM products ";
		
		
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
	
			public void addtocart() {
			add.setOnAction((event) -> {
				String deletID = id.getText();
				String query5 =String.format("SELECT id,brand,price,stock "
						+ "	FROM products  where id ='%s'", deletID);
				
				PreparedStatement ps = connect.Prepare(query5);
				
				System.out.println(deletID);
				System.out.println(deletID);
				
				
				try {
					ResultSet res = ps.executeQuery();
					
					if (res.next()) {
				
			String getid = id.getText();
			String query = String.format("INSERT INTO carts VALUES('%s', '%s','%s')",terimak,getid,sqty.getValue());
		
			
			Integer result = connect.exceuteUpdate(query);
			Main.cart.refresh();
			
				
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setContentText("succesfully add cart ");
						information.setAlertType(Alert.AlertType.INFORMATION);
						information.show();
						
						
						
						
			}
			else {
				Alert warningalertt = new Alert(AlertType.WARNING);
				warningalertt.setContentText("PRODUK NOT FOUND");
				warningalertt.show();
				
			}
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
	}
			
			public void terima(String terima) {
				terimak = terima;
			}
}
