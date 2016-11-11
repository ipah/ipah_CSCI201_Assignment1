package Client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import ipah_CSCI201L_Assignment1.GameBoard;
import ipah_CSCI201L_Assignment1.JeopardyGUI;
import ipah_CSCI201L_Assignment1.Jeopardy_Game;
import ipah_CSCI201L_Assignment1.Message;

public class GameClient extends Thread{
	private ObjectOutputStream oos;
	private ObjectInputStream ois; 
	private String mTeamName;
	private Socket s;
	private GameBoard game; 
	public GameClient(int port, String teamName){
		mTeamName = teamName;
		
		try {
			s = new Socket("localhost", port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			
			oos.writeObject(new Message("add team", teamName));
			oos.flush();
			this.start();
			System.out.println("28");
//			
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}

	public void run(){
		try{
			//System.out.println("59");
			while(true){
				Object message = ois.readObject();
				
				if( message instanceof byte[]){
					
					Jeopardy_Game jp = (Jeopardy_Game)message;
					game = new GameBoard(jp);
				}
				else if( message instanceof Vector<?>){
					Vector<Object> vec = (Vector<Object>)message; 
					
					//Jeopardy_Game jp = new Jeopardy_Game( (String)message );
					game = new GameBoard((String)vec.get(0), (Vector<String>)vec.get(1), (String)vec.get(2));
					
					
				}
				else if( message instanceof Jeopardy_Game){
					Jeopardy_Game jp = (Jeopardy_Game)message; 
					game = new GameBoard(jp);
				}
				else if (message instanceof Message){
					
					System.out.println(((Message)message).getMessage());
				}
				// message.getMessage() "start game"
				//System.out.println("Client: " + ((Message)message).getMessage());
				//System.out.println("63:" +message.getMessage());
				//then it should update the GameBoard GUI accordingly??
			}
		}catch(ClassNotFoundException cnfe){
			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
