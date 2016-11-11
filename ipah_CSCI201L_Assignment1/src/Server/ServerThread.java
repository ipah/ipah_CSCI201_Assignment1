package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ipah_CSCI201L_Assignment1.Constants;
import ipah_CSCI201L_Assignment1.GameBoard;
import ipah_CSCI201L_Assignment1.Jeopardy_Game;
import ipah_CSCI201L_Assignment1.Message;

public class ServerThread extends Thread{
	private GameServer gs;
	private ObjectOutputStream oos;
	private ObjectInputStream ois; 
	
	public ServerThread(Socket s, GameServer gs){
		
		try {
			this.gs = gs;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendGame(Object message){
		//System.out.println("sending games!");
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		try {
			while(true){
			
				Object message = ois.readObject();
				if( message instanceof Jeopardy_Game){
					
				}
				if( message instanceof Message){
					String ms = ((Message)message).getType();
					if(ms.equals(Constants.ADD_TEAM)){
						String teamName = ((Message)message).getMessage();
						gs.addTeamToGame(teamName);
						System.out.println("added: " + teamName);
					}
				}
				
				gs.sendGameToAllClients(message);
			}
				
		} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
}
