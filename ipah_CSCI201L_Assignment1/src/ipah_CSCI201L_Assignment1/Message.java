package ipah_CSCI201L_Assignment1;

import java.io.Serializable;

public class Message implements Serializable{
	public static final long serialVersionUID = 1;
	private String message;
	private String mType;
	
	
	public Message(String type, String message){
		this.message = message; 
		mType = type;
	}
	
	public String getMessage(){
		return message; 
	}
	public String getType(){
		return mType;
	}
	
	public static String waitingMessage(){
		Message m = new Message("wait", "Waiting for all users to connect.");
		return m.getMessage();
	}
}
