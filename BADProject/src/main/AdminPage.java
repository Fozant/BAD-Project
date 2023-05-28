package main;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class AdminPage extends BorderPane{
	BorderPane bp;
	MenuBar menubar;
	Menu menu, account;
	MenuItem manage, logout;
	Scene scnadmin;
	public AdminPage() {
		bp= new BorderPane();
		scnadmin = new Scene(bp,700,700);
		
		menubar = new MenuBar();
		menu = new Menu ("Menu");
		account = new Menu("Account");
		manage = new MenuItem("manage product");
		logout = new MenuItem("logout");
		
		
		menubar.getMenus().addAll(menu,account);
		menu.getItems().add(manage);
		account.getItems().add(logout);
		
		bp.setTop(menubar);
		
	}

}
