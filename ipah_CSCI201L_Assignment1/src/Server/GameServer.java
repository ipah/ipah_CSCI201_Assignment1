package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

import ipah_CSCI201L_Assignment1.Constants;
import ipah_CSCI201L_Assignment1.GameBoard;
import ipah_CSCI201L_Assignment1.JeopardyGUI;
import ipah_CSCI201L_Assignment1.Jeopardy_Game;
import ipah_CSCI201L_Assignment1.Message;

public class GameServer extends Thread{
	private ServerSocket ss;
	private static GameServerListener gsl; 
	private GameBoard game; 
	private Vector<ServerThread> serverThreads; 
	private int numOfTeams;
	private int mPort;
	private Jeopardy_Game mGameData;
	
	// TODO: Pass thru number of teams!!!! 
	public GameServer( int port, int numOfTeams, Jeopardy_Game gameData){
		mGameData = gameData; 
		mPort = port; 
		this.numOfTeams = numOfTeams; 
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverThreads = new Vector<ServerThread>();
		
		this.start();
		

	}
	public void run(){
		try {
			
			int currTeam = 0;
			while( currTeam < numOfTeams){
				Socket s = ss.accept();
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
				currTeam++;
				//System.out.println("current team connection: " + currTeam);
				if(currTeam == numOfTeams){
					//send game info to clients
					File f = new File("src/" + mGameData.getFile());
					byte [] jpFile = Files.readAllBytes(new File(mGameData.getFile()).toPath());
					
					Vector<Object> neededInfo = new Vector<Object>();
					neededInfo.add(mGameData.getFile());//0
					Vector<String> teamNames = new Vector<String>();
					for(int i = 0; i < mGameData.getTeamHolder().size(); i++){
						teamNames.add(mGameData.getTeamHolder().get(i).getName());
					}
					neededInfo.add(teamNames);//1
					neededInfo.add(mGameData.getGameType());//2
					sendGameToAllClients(neededInfo);
					System.out.println("all players have joined");
					
				}
				else{
					//send "still waiting" message
					System.out.println("wait"); 
					Message wait = new Message("wait", "Waiting for all users to connect.");
					sendGameToAllClients(wait);
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if( ss != null){
				System.out.println("not null");
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ioe closing...");
				}
			}
		}
	}
	
	public void sendGameToAllClients(Object message){
		for( ServerThread st : serverThreads){
			st.sendGame(message);
		}
	}
	
	public void addTeamToGame(String team){
		mGameData.addTeamToGame(team);
	}
	
	
	
}
