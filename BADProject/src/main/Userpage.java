package main;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class Userpage extends BorderPane {

	BorderPane bp;
	Scene sceneuser;
	MenuBar menubar;
	Menu menu, account;
	MenuItem list,cart, logout;
	public Userpage() {
		bp= new BorderPane();
		sceneuser = new Scene(bp,700,700);
		
		menubar = new MenuBar();
		menu = new Menu ("Menu");
		account = new Menu("Account");
		list = new MenuItem("Product list");
		cart = new MenuItem("cart");
		logout = new MenuItem("logout");
		
		
		menubar.getMenus().addAll(menu,account);
		menu.getItems().addAll(list,cart);
		account.getItems().add(logout);
		
		bp.setTop(menubar);
	}

}
