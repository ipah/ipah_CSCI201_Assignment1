package Server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import ipah_CSCI201L_Assignment1.GameBoard;
import ipah_CSCI201L_Assignment1.JeopardyGUI;

public class GameServer {
	private ServerSocket ss;
	private static GameServerListener gsl; 
	private GameBoard game; 
	private Vector<ServerThread> serverThreads; 
	
	
	public GameServer( int port){
		ss= null;
		serverThreads = new Vector<ServerThread>();
		
		try {
			ss = new ServerSocket(port);
			while(true){
				System.out.println("waiting for connection...");
				Socket s = ss.accept();
				ServerThread st = new ServerThread();
				serverThreads.add(st);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if( ss != null){
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ioe closing...");
				}
			}
		}
		
		
	
	}
	
	public void sendGameToAllClients(GameBoard gameBoard){
		for( ServerThread st : serverThreads){
			st.sendGame(gameBoard);
		}
	}
	
	
	
}
