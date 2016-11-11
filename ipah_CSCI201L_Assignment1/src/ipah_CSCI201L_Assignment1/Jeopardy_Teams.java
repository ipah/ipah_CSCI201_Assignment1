package ipah_CSCI201L_Assignment1;

import java.io.Serializable;

public class Jeopardy_Teams implements Serializable{
	private static final long serialVersionUID = 1L;
	private String team_bet;//amount that team bets for FJ
	private String team_name;
	private int team_score = 0;
	
	private String guessAns; //answer that teams guess
	
	public void resetScore(){
		team_score = 0;
	}
	public void setGuess(String g){
		guessAns = g;
	}
	public String getGuess(){
		return guessAns;
	}
	public void setBetAmount(String bet){
		team_bet = bet;
	}
	public String getBetAmount(){
		return team_bet;
	}
	public void setName(String name){
		team_name = name;
	}
	public int getScore(){
		return team_score;
	}
	public String getName(){
		return team_name;
	}
	public void changeScore(int change){
		team_score += change; 
	}
	public void printTeamInfo(){//prints out name and score of a team. 
		System.out.println(team_name+ ": " + team_score);
		
	}
}
