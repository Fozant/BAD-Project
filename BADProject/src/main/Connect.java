package main;
import java.sql.*;

import data.Cartt;
import javafx.scene.control.Alert;
public  final class Connect {
	private final String HOST ="localhost:3306";
	private final String USERNAME ="root";
	private final String PASSWORD ="";
	private final String DATABASE ="carethree_db";
	
	private final String CONNECTION 
	=String.format("jdbc:mysql://%s/%s", HOST,DATABASE);
	
	private Connection con;
	private Statement st;
	private static Connect connect;
	public ResultSet rs;
	public ResultSetMetaData rsm;
	String terimak;
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION,USERNAME,PASSWORD);
			st= con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized Connect getConnection() {
		if (connect == null)
		{
			return new Connect();
		}
		return connect;
	}
	public ResultSet executeQuery(String query) {
		
		rs= null;
		
		try {
			rs= st.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
		
	}


	//for update delete, inser query in database 
	public Integer exceuteUpdate(String query) {
			Integer res=null; 
		
		try {
			res= st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

		public PreparedStatement Prepare ( String query) {
			PreparedStatement ps = null;
			
			try {
				ps = con.prepareStatement(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ps;
		}
	
		public void removedata (String id) {
			
			
			
			
			String query = "DELETE FROM products WHERE id = ?";
			
		
			
			try {PreparedStatement ps = connect.Prepare(query);
				ps.setString(1, id);
				ps.executeUpdate();
				
				

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
				
	}
		public void execUpdate(String query) {
			try {
				st.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void removedataa(String id) {
			String query = "DELETE FROM products WHERE id = ?";
			try {	PreparedStatement ps = connect.Prepare(query);
				
				ps.setString(1, id);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	
}
