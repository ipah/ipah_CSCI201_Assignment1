package ipah_CSCI201L_Assignment1;

import java.io.Serializable;


public class User implements Serializable{
	public static final long serialVersionUID = 1;
	private String username, password;

	
	
	public User(String uN, String pW){
		
		username = uN;
		password = pW;
	}
	
	public void printUserInfo(){
		System.out.println("username: " + username + " password: " + password);
	}
	
	public String correctPassword(){
		return password;
	}
	
	public String getUsername(){
		return username;
	}
}
