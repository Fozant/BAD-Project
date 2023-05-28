package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.Produk;

import data.Cartt;
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



public class Cart extends BorderPane{
	
	
	
	TableView<Cartt> carttabel= new TableView<>();
	Connect connect = Connect.getConnection();
	BorderPane bp;
	Scene scenecart;
	MenuBar menubar;
	Menu menu, account;
	MenuItem list,cart, logout;
	TextField idfield;
	Productlist produklizt;
	String terimak,awga;
	Button refreshtable;
	Spinner<Integer> spin ;
	Button checkout, remove ;
	public Cart() throws SQLException {
		terima();
		refreshtable = new Button("CLICK HERE TO REFRESH TABLE");
		bp= new BorderPane();
		FlowPane fp = new FlowPane();
		GridPane gp= new GridPane();
		produklizt = new Productlist ();
		scenecart = new Scene(bp,700,700);
		
		menubar = new MenuBar();
		menu = new Menu ("Menu");
		account = new Menu("Account");
		list = new MenuItem("Product list");
		cart = new MenuItem("cart");
		logout = new MenuItem("logout");
		
		
		menubar.getMenus().addAll(menu,account);
		menu.getItems().addAll(list,cart);
		account.getItems().add(logout);
		
		
		
		TableColumn<Cartt, String>  column1 = new TableColumn<>("Product ID");
		TableColumn<Cartt, String>  column2 = new TableColumn<>("Brand");
		TableColumn<Cartt, String> column3 = new TableColumn<>("Qty");
		TableColumn<Cartt, String> column4 = new TableColumn<>("Price");
		TableColumn<Cartt, String> column5 = new TableColumn<>("total");
		carttabel.getColumns().addAll(column1,column2,column3,column4,column5);
		
		
		column1.setCellValueFactory(new PropertyValueFactory<>("productID"));
		column2.setCellValueFactory(new PropertyValueFactory<>("brand"));
		column3.setCellValueFactory(new PropertyValueFactory<>("qty"));
		column4.setCellValueFactory(new PropertyValueFactory<>("price"));
		column5.setCellValueFactory(new PropertyValueFactory<>("total"));
		fp.getChildren().add(carttabel);
		
		Label total = new Label("total price =");
		Label productid = new Label("ProductID");
		Label qty = new Label("qty");
		
		idfield = new TextField();
		
		spin = new Spinner<>(1,100,1);
		
		 remove = new Button("remove from cart");
		 checkout = new Button("checkout");
		
		gp.add(carttabel, 0, 0);
		gp.add(total, 0, 1);
		gp.add(productid, 0, 2);
		gp.add(idfield, 0, 3);
		gp.add(qty, 0, 4);
		gp.add(spin, 0, 5);
		gp.add(remove, 0, 6);
		gp.add(checkout, 0, 7);
		gp.setVgap(5);
		
		
		bp.setCenter(gp);
		bp.setTop(menubar);
		bp.setRight(refreshtable);
		//bp.setBottom(gp);
		
		refreshtable.setOnAction((event) -> {
			try {
				refresh();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	
		
		remove();
	
		refresh();
		cekott();
		
		
	}
	public  void refresh() throws SQLException {
		carttabel.getItems().clear();

		String query =String.format("SELECT userID,productID,brand,qty,price"
				+ " FROM carts join products on productID= id where userID ='%s'", terimak); 
		System.out.println(query);
		PreparedStatement ps = connect.Prepare(query);
		
		
			ResultSet res = ps.executeQuery();
			try {
			while (res.next()) {
			
				int qtyyyy =res.getInt("qty");
				int priceee=res.getInt("price");
				Cartt cart = new Cartt();
				
				cart.setProductID(res.getString("productID")); 
				cart.setBrand(res.getString("brand"));
				cart.setQty(qtyyyy);
				cart.setPrice(priceee);
				cart.setTotal(priceee*qtyyyy);
				carttabel.getItems().add(cart);
			
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void terima() {
		terimak = Main.Userid;
	}
	public void cekott() {
		
		checkout.setOnAction((event) -> {
		
			String deletID = idfield.getText();
			String query =String.format("SELECT userID,productID,brand,qty,price,stock "
					+ "	FROM carts join products on productID= id where id ='%s'", deletID); 
			
			PreparedStatement ps = connect.Prepare(query);
			
			
			
			
				try {
					ResultSet res = ps.executeQuery();
					
				if (res.next()) {
				int quanititi = spin.getValue();
				int getstok = res.getInt("stock");
				System.out.println(quanititi);
				System.out.println(getstok);
					if (quanititi>getstok) {
						
						Alert ERRORalert = new Alert(AlertType.ERROR);
						ERRORalert.setContentText("quantity is more then stock!! ");
						ERRORalert.setHeaderText("Warning");
						System.out.println(ERRORalert.getResult());
						ERRORalert.show();
						System.out.println("1");
					}
					else {	
						int berapa = res.getInt("qty"); 
						
						String query2 = String.format
						("UPDATE products SET stock=stock-'%s' WHERE id = '%s'",quanititi,deletID);
						String query3 = String.format
						("DELETE FROM carts WHERE productID = '%s'",deletID);
						
						System.out.println("produk id adalah == "+deletID);
					
					
					Integer result = connect.exceuteUpdate(query2);
					 result = connect.exceuteUpdate(query3);

								Alert information = new Alert(Alert.AlertType.INFORMATION);
								information.setContentText("succesfully checkout ");
								information.setAlertType(Alert.AlertType.INFORMATION);
								information.show();
							System.out.println("2");

					}
				}
				else {
					Alert ERRORalert = new Alert(AlertType.ERROR);
					ERRORalert.setContentText("ID NOT FOUND ");
					ERRORalert.setHeaderText("Warning");
					System.out.println(ERRORalert.getResult());
					ERRORalert.show();
					System.out.println("1");
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
					
	
		});
	}
	public void remove(){
				remove.setOnAction((event) -> {
					String deletID = idfield.getText();
					String query =String.format("SELECT userID,productID,brand,qty,price,stock "
							+ "	FROM carts join products on productID= id where id ='%s'", deletID); 
					
					PreparedStatement ps = connect.Prepare(query);
					
					
					
					
					try {
						ResultSet res = ps.executeQuery();
						
					if (res.next()) {
						String query3 = String.format
								("DELETE FROM carts WHERE productID = '%s'",deletID);
						System.out.println("delet suces");
						System.out.println(deletID);
						
						Integer result = connect.exceuteUpdate(query3);
						Alert information = new Alert(Alert.AlertType.INFORMATION);
						information.setContentText("succesfully delete ");
						information.setAlertType(Alert.AlertType.INFORMATION);
						information.show();
						
						
					}
					else {
						Alert ERRORalert = new Alert(AlertType.ERROR);
						ERRORalert.setContentText("ID not found");
						ERRORalert.setHeaderText("Warning");
						System.out.println(ERRORalert.getResult());
						ERRORalert.show();
					
					}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
			
		});
	}
}
