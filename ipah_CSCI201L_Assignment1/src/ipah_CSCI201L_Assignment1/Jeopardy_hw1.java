package ipah_CSCI201L_Assignment1;

import java.io.FileNotFoundException;

public class Jeopardy_hw1 {
	public static void main(String [] args){
		Jeopardy_Game game = new Jeopardy_Game();
		
		try {
			game.startGame(args[0]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //starts prompt
		
		
		game.printAll();
			
		
		
	}

}

