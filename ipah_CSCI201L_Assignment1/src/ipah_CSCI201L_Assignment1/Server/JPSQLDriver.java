package Server;

//import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import com.mysql.jdbc.Driver;

import ipah_CSCI201L_Assignment1.LoginGUI;


public class JPSQLDriver {
	private Connection con;
	private final static String selectName = "SELECT * FROM USERINFO WHERE username=?";
	private final static String selectPW ="SELECT * FROM USERINFO WHERE username=? AND password=?";
	private final static String addUser = "INSERT INTO USERINFO (username,password) VALUES (?,?)";
	
	public JPSQLDriver(){
		try{
			new Driver();
		}catch(SQLException e){
			
			e.printStackTrace();
		}
	}
	
	public void connect(){
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jeopardy?user=root&password=planet1!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void stop(){
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace(); 
		}
	}
	
	public boolean doesExist(String username){
		try {
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				return true;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginGUI.sendWarning();
		return false;
		
	}
	public boolean correctCredentials(String user, String pw){
		try {
			if(doesExist(user)){
				
			}
			PreparedStatement ps1 = con.prepareStatement(selectPW);
			ps1.setString(1, user);
			ps1.setString(2, pw);
			ResultSet rs = ps1.executeQuery();
			while(rs.next()){
				return true;			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginGUI.sendWarning();
		return false;
		
	}
	public void add(String user, String password){
		try{
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1, user);
			ps.setString(2, password);
			ps.executeUpdate();
		}catch(SQLException s){
			
		}
	}
	

}
