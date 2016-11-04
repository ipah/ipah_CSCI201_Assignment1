package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient {
	public GameClient(int port){
		Socket s = null;
		
		try {
			s = new Socket("localhost", port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if( s != null){
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Connection Made!");
	}
}
