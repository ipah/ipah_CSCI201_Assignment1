package Server;

import java.net.ServerSocket;
import java.util.Vector;

import ipah_CSCI201L_Assignment1.GameBoard;

public class GameServerListener extends Thread{
	private ServerSocket ss; 
	private GameBoard game; 
	private Vector<JPServerClientComm> sccVector; 
	
	public GameServerListener(ServerSocket ss){
		this.ss = ss;
		sccVector = new Vector<JPServerClientComm>();
		
	}
	
	public void sendGame(GameBoard game){
		this.game = game; 
		for( JPServerClientComm scc: sccVector){
			scc.sendGame(game);
		}
	}
}
