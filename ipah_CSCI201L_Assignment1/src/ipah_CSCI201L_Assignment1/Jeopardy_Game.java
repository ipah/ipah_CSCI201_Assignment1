package ipah_CSCI201L_Assignment1;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;



public class Jeopardy_Game implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int numberOfTeams = 1;
	private String fileName; 
	private int numberOfQuestionsAsked = 0; // counts number of questions asked so far.
	private Vector<Categories> categories = new Vector<Categories>();
	private ArrayList<String> pointVals = new ArrayList<String>();
	private ArrayList<String> catStringName = new ArrayList<String>();
	
	private FinalJeopardyQuestion fj = new FinalJeopardyQuestion();
	
	//private ImageIcon catBG, bttnEnabled, bttnDisabled; //background images for buttons
//	private Vector<ImageIcon> imageHolder = new Vector<ImageIcon>(); //holds images
	private Vector<String> imageHolder = new Vector<String>();
	private int lineCount = 0;
	private int _numRates, totRates;
	private Vector<String> teamNames; 
	private String gameType;
	
	ArrayList<String> fileLines; //saves lines of file
	
	
	
	public boolean isFJCorrect(String ans){
		String[] answer = ans.toLowerCase().split(" ");
		
		if( answer[0].toLowerCase().equals("who") || answer[0].toLowerCase().equals("what") || answer[0].toLowerCase().equals("where") || answer[0].toLowerCase().equals("when")){
			
			if( (answer[1].toLowerCase().equals("is") || answer[1].toLowerCase().equals("are")) && (answer[2].toLowerCase().equals("the") || answer[2].toLowerCase().equals("a")) ){ //if "the" article is needed
			
				String [] subarray = Arrays.copyOfRange(answer, 3, answer.length);
				String [] qArray = fj.getFJAnswer().toLowerCase().split(" ");
			
				if(Arrays.equals(qArray, subarray)){
					/*team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
					System.out.println("Correct!");*/
					return true;
				}
				else{
					/*System.out.println("Incorrect!");
					team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));*/
					return false;
				}
				//FinalJeopardy();
			}
			else{
				String [] subarray = Arrays.copyOfRange(answer, 2, answer.length);
				String [] qArray = fj.getFJAnswer().toLowerCase().split(" ");
				/*for(int i = 0; i < questArray.length; i++){
					System.out.print("filr: "+ questArray[i]);
				}
				
				for(int i = 0; i < answer.length; i++){
					System.out.print("filr: "+ answer[i]);
				}*/
				if(Arrays.equals(subarray,  qArray)){
					/*team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
					System.out.println("Correct!");*/
					return true;
				}
				else{
					/*System.out.println("Incorrect!");
					team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));*/
					return false;
				}
			
			}
		}
		
		return false;
		
		
		
	}
	//THIS FUNCTION CHECKS IF THE GIVEN ANSWER IS CORRECT
	public boolean isCorrect(String userAnswer, String correctAnswer){
		
		String[] answer = userAnswer.toLowerCase().split(" ");
		
		
		String [] questArray = 	correctAnswer.toLowerCase().split(" ");
		
		if( answer[0].toLowerCase().equals("who") || answer[0].toLowerCase().equals("what") || answer[0].toLowerCase().equals("where") || answer[0].toLowerCase().equals("when")){
			
			if( (answer[1].toLowerCase().equals("is") || answer[1].toLowerCase().equals("are")) && (answer[2].toLowerCase().equals("the") || answer[2].toLowerCase().equals("a")) ){ //if "the" article is needed
			
				String [] subarray = Arrays.copyOfRange(answer, 3, answer.length);
				String [] qArray = correctAnswer.toLowerCase().split(" ");
			
				if(Arrays.equals(qArray, subarray)){
					/*team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
					System.out.println("Correct!");*/
					return true;
				}
				else{
					/*System.out.println("Incorrect!");
					team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));*/
					return false;
				}
				//FinalJeopardy();
			}
			else{
				String [] subarray = Arrays.copyOfRange(answer, 2, answer.length);
				String [] qArray = correctAnswer.toLowerCase().split(" ");
				/*for(int i = 0; i < questArray.length; i++){
					System.out.print("filr: "+ questArray[i]);
				}
				
				for(int i = 0; i < answer.length; i++){
					System.out.print("filr: "+ answer[i]);
				}*/
				if(Arrays.equals(subarray,  qArray)){
					/*team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
					System.out.println("Correct!");*/
					return true;
				}
				else{
					/*System.out.println("Incorrect!");
					team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));*/
					return false;
				}
			}
		}
		
		else{//go to next team
			
			return false; 
		}
	
	}

	
	public void updateFile() throws IOException{
		fileLines = new ArrayList<String>();
		File f = new File(fileName);
		Scanner input = new Scanner(f);
		while(input.hasNextLine()){
			String line = input.nextLine();
			fileLines.add(line);
		}
		
		fileLines.set(28,Integer.toString(getNumOfRate())); 
		fileLines.set(29,Integer.toString(getTotRate()));
		
		
		//WRITE TO FILE
		
		FileOutputStream oos = new FileOutputStream(fileName);
		BufferedWriter br = new BufferedWriter( new OutputStreamWriter(oos));
			
		for(int i = 0; i < fileLines.size(); i++){
			
			br.write(fileLines.get(i));
			br.newLine();	
			br.flush();
			
			
		}
		
		br.close();
		oos.close();
			
		
		
		
	}
	
	
	public Jeopardy_Game(String file) throws FileNotFoundException{
		File_reader(file);
	}
	
	public Jeopardy_Game(String file, Vector<String> tNames, String gameType) throws FileNotFoundException{//displays first prompts
		fileName = file;
		teamNames = new Vector<String>();
		
		teamNames = tNames; 
		this.gameType = gameType;
		File_reader(fileName);

		
		Scanner response = new Scanner(System.in);
	
		String num_teams; 
		

	}
	
	public Vector<String> getTeamNames(){
		return teamNames;
	}
	public String getGameType(){
		return gameType;
	}
	//FINAL JEOPARDY
	public void FinalJeopardy(){
		Scanner input = new Scanner(System.in);
		System.out.println("Starting Final Jeopardy!");
		
		for(int i = 0; i < team_holder.size(); i++){//sets bets for each team
			System.out.println("Team " + team_holder.get(i).getName()+" please enter your bet.");
			String bet_amount = input.nextLine();
			if(bet_amount.toLowerCase().equals("exit")){
				exit();
			}
			if(bet_amount.toLowerCase().equals("replay")){
				replay();
			}
			while(!isBetValid(team_holder.get(i), Integer.parseInt(bet_amount) ) ){
				System.out.println("Invalid bet amount.");
				bet_amount = input.nextLine();
				if(bet_amount.toLowerCase().equals("exit")){
					exit();
				}
				if(bet_amount.toLowerCase().equals("replay")){
					replay();
				}
			}
			team_holder.get(i).setBetAmount(bet_amount);
		}
		System.out.println(fj.getFJQuestion());//print fj question
		
		for(int i =0; i< team_holder.size(); i++){//prompts users to make guesses
			System.out.println("Team " + team_holder.get(i).getName()+" please enter your guess.");
			String guess = input.nextLine();
			if(guess.toLowerCase().equals("exit")){
				exit();
			}
			if(guess.toLowerCase().equals("replay")){
				replay();
			}
			team_holder.get(i).setGuess(guess);
		}
		
		for(int i = 0; i<team_holder.size(); i++){//sees who got its right
			if(team_holder.get(i).getGuess().equals(fj.getFJAnswer())){//answer was correct
				int addBet = Integer.parseInt(team_holder.get(i).getBetAmount());
				team_holder.get(i).changeScore(addBet); 
				System.out.println(team_holder.get(i).getName() + " you are correct!");
			}
			else{//incorrect answer
				int addBet = Integer.parseInt(team_holder.get(i).getBetAmount());
				team_holder.get(i).changeScore((-1)*addBet);
				System.out.println(team_holder.get(i).getName() + " you are incorrect!");
			}
		}
		
		System.out.println("Congratulations, " + winner());
		input.close();
	}
	public String winner(){
		int max = team_holder.get(0).getScore();
		int maxIndex=0;
		int smScrIndx= 0;
		for(int i = 1; i < team_holder.size(); i++){
			if(team_holder.get(i).getScore() > max){
				max = team_holder.get(i).getScore();
				maxIndex = i;
				//System.out.println("maxScr: " + team_holder.get(0).getScore());
				//System.out.println("maxTeam: " + team_holder.get(0).getName());
				//System.out.println("maxScr index: " + i);
			}
			else if( team_holder.get(i).getScore() == max ){
				//TODO when there is a tie
				smScrIndx = i;
				//return (team_holder.get(maxIndex).getName() );
			}
		}
		
		if(team_holder.get(smScrIndx).getScore() == max){
			return ("There are no winners.");
		}
		return (team_holder.get(maxIndex).getName()  );
			
	}
	public boolean isBetValid(Jeopardy_Teams team, int bet){
		if(bet > 0 && bet <= team.getScore()){
			return true;
		}
		return false;
	}
	
	public void setNumOfRate(int numRates){
		_numRates = numRates;
		
	}
	public void setTotRate(int tot){
		totRates = tot;
	}
	
	public void incToNumOfRates(){ //increases of raters
		_numRates++;
	}
	
	public void addRating(int rate){ //adds players rating 
		totRates += rate;
	}
	public int getNumOfRate(){
		return _numRates;
	}
	public int getTotRate(){
		return totRates; 
	}
	public String getAvg(){
		int avg = (totRates/_numRates);
		String s = Integer.toString(avg);
		return (s+"/5");
	}
	//FILE READER	
	public void File_reader(String fileName) throws FileNotFoundException{
		//Scanner scanner = new Scanner(System.in);
	
		File f = new File(fileName);
		Scanner scanner = new Scanner(f);
		
		
		
		
		
		
		String[] catSplit;
		String[] srcSplit; 
		//Reads and stores categories
		catSplit = scanner.nextLine().split("::");
		
		
		
		if(catSplit.length < 6){
			System.out.println("here " +catSplit.length);
			System.out.println("(not enough categories)ERROR: Invalid file format. Not enough data in this section.");
			scanner.close();
			System.exit(0);
		}
		else if(catSplit.length > 6){
			System.out.println("ERROR:Invalid file format. Too much data in this section.");
			scanner.close();
			System.exit(0);
		}
		else{
			for(int i =0; i< catSplit.length; i++){
				if( i == 5){
					//catBG = new ImageIcon(catSplit[i]);
					imageHolder.add(catSplit[i]);
				}
				else{
					catStringName.add(catSplit[i]); //saves string version of categories
					Categories new_cat = new Categories(catSplit[i]); 
					categories.addElement(new_cat); 
				}
			}
			lineCount++;
		}
		
		
		for(int i = 0; i < catSplit.length; i++){
			if(catSplit[i].equals("") || catSplit[i].equals(" ")){
				System.out.println("ERROR: Incorrect file format. Section contains whitespace/empty string.");
				System.exit(0);
			}
		}
		
		//Reads and stores points
		srcSplit = scanner.nextLine().split("::");
	
		if(srcSplit.length < 7){
			System.out.println("(not enough pts)ERROR: Invalid file format. Not enough data in this section.");
			scanner.close();
			System.exit(0);
		}
		else if(srcSplit.length > 7){
			System.out.println("ERROR:Invalid file format. Too much data in this section.");
			scanner.close();
			System.exit(0);
		}
		else{
			for(int i =0; i< srcSplit.length; i++){
				if(i == 5 ){
					/*bttnEnabled = new ImageIcon(srcSplit[i]);
					imageHolder.add(bttnEnabled);*/
					imageHolder.add(srcSplit[i]);
				}
				else if(i == 6){
					//bttnDisabled = new ImageIcon(srcSplit[i]);
					imageHolder.add(srcSplit[i]);
				}
				else{
					pointVals.add(srcSplit[i]);
				}
				
			}
			
			lineCount++;
		}
		for(int i = 0; i < srcSplit.length; i++){
			if(srcSplit[i].equals("") || srcSplit[i].equals(" ")){
				System.out.println("ERROR: Incorrect file format. Section contains whitespace/empty string.");
				System.exit(0);
			}
		}
		
		//Reads rest of file
		while(scanner.hasNextLine()){
			String[] split = scanner.nextLine().split("::");
			//split[1] = category; 2 = score; 3 = question; 4 = answer
			
			if(split.length == 1){
				if(lineCount == 29){
					setNumOfRate(Integer.parseInt(split[0]));
					
					
				}
				
				if(lineCount == 30){
					setTotRate(Integer.parseInt(split[0]));
				}
			}
			
			
			else if(split[1].equals("FJ")  ){// final Jeopardy 
				//create final jeopardy question
				/*Categories finalJ = new Categories(split[1]);
				categories.addElement(finalJ);
				Questions fj = new Questions();
				fj.makeFJQuest(split[2],split[3]);
				getCategory(split[1]).addQuestions(fj);	*/
				if(split.length < 4){
					System.out.println("(FJQ)ERROR: Invalid file format. Not enough data in this section.");
					scanner.close();
					System.exit(0);
				}
				else if(split.length > 4){
					System.out.println("ERROR:Invalid file format. Too much data in this section.");
					scanner.close();
					System.exit(0);
				}
				fj.setFJQuestion(split[2]);
				fj.setFJAnswer(split[3]);
				lineCount++;
			}	
			else if(split.length < 5){
				System.out.println("(reading lines w/ questions)ERROR: Invalid file format. Not enough data in this section.");
				scanner.close();
				System.exit(0);
			}
			else if(split.length > 5){
				System.out.println("ERROR:Invalid file format. Too much data in this section.");
				scanner.close();
				System.exit(0);
			}
			
			for(int i = 1; i < split.length; i++){
				if(split[i].equals("") || split[i].equals(" ")){
					System.out.println("ERROR: Incorrect file format. Section contains whitespace/empty string.");
					System.exit(0);
				}
			}
			if( (split.length!=1) && isCategory(split[1])  && isScore(split[2]) ){
				//System.out.println(split[1]);				//then should make question
				Questions q = new Questions();
				q.makeQuestions(split[2], split[3], split[4]);
				getCategory(split[1]).addQuestions(q); 
			}
			
			lineCount++;
			
		}//end of while loop.	
		
		for(int i = 0; i < categories.size(); i++){
			if(categories.get(i).getVecSize() < 5){
				System.out.println(i + ": " +categories.get(i).getVecSize());
				System.out.println("(not enough categories)ERROR: Incorrect file format. There are not enough questions present.Cannot play game.");
				System.exit(0);
			}
			else if(categories.get(i).getVecSize() > 5){
				System.out.println("ERROR: Incorrect file format. There are too many questions.Cannot play game.");
				System.exit(0);
			}
			
			if(categories.get(i).getVecSize() != 5){
				System.out.println("ERROR: Incorrect file format. There are not enough questions presetnt.Cannot play game.");
				System.exit(0);
			}
		}
		scanner.close();
	}
	public String getFJQuestion(){
		return fj.getFJQuestion();
	}
	
	//TEAMS 
	//private int numberOfTeams;
	private Vector<Jeopardy_Teams> team_holder = new Vector<Jeopardy_Teams>();; //array that holds teams.

	public void setNumOfTeams(int num){
		numberOfTeams = num;
		//System.out.println("in method: " + numberOfTeams);
		
	}
	public void addTeamToGame(String name){ //adds teams to array and sets team names
		Jeopardy_Teams new_team = new Jeopardy_Teams();
		new_team.setName(name);
		team_holder.addElement(new_team);
		
		
	}
	public void printAll(){
		for(int i =0; i < numberOfTeams; i++){
			team_holder.get(i).printTeamInfo();
		}
		System.out.println(categories.size());
		for(int i = 0; i < categories.size(); i++){
			System.out.println(categories.get(i).getCategoryName());
		}
	}
	//GAME QUESTIONS/CATEGORIES
	//-vector of the five categories
	public void printNumOfQuest(){
		System.out.println("questions asked: " +numberOfQuestionsAsked);
	}
	
	public boolean quickPlayFinished(){//should only ask 5 questions before final jeopardy
		if(numberOfQuestionsAsked == 5){
			return true;
		}
		return false;
	}
	
	public void resetNumQuestionsAsked(){
		numberOfQuestionsAsked = 0;
	}
	
	public void incrementQuestionsAsked(){
		numberOfQuestionsAsked++;
		
	}

	public boolean categoryFinished(String c){ //checks to see if there are available questions in category
		return getCategory(c).isFinished();
	}
	public boolean allQuestionsFinished(){
		for(int i = 0; i < categories.size(); i++){
			if(categories.get(i).isFinished() == false){ //there are still questions to be answered
				return false;
			}
		}
		return true;
	}
	public boolean isCategory(String category){

		for(int i = 0; i < categories.size(); i++){
			//System.out.println("indexed" + categories.get(i).getCategoryName());
			if(categories.get(i).getCategoryName().equals(category)){
				//System.out.println("true");
				return true;
			}						
		}
		//System.out.println("false");
		return false; 
	}
	
	public Categories getCategory(String c){ //returns category object
		
		for(int i = 0; i < categories.size(); i++){
			if(categories.get(i).getCategoryName().equals(c)){
				return categories.get(i);
			}
		}
		return null;
		
		
	}
	
	public boolean isScore(String score){
		//System.out.println(score);
		for(int i = 0; i<pointVals.size(); i++){
			if( pointVals.get(i).equals(score)){
				return true;
			}
		}
		return false;
	}
	public void addCategories(Categories cat){
		categories.addElement(cat);
	}
	/*public void setPointVals(String[] vals){
		pointVals = vals; 
	}*/
	
	public void exit(){
		System.out.println("Game over...");
		System.exit(0);
	
	}
	public void replay(){
		//System.out.println("Restarting game...");
		for(int i = 0; i < team_holder.size(); i++){
			team_holder.get(i).resetScore();
		}
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				categories.get(i).getQuestionwIndex(j).resetQuestions();
			}
		}
	}
	
	public void clear(){
		numberOfTeams = 1; 
		team_holder.clear();
		fileName = "";
		categories.clear();
		pointVals.clear();
		resetNumQuestionsAsked();
	}
	
	public Vector<Jeopardy_Teams> getTeamHolder(){
		return team_holder;
	}
	
	public ArrayList<String> getPointVals(){
		return pointVals;
	}
	public ArrayList<String> getCatStringName(){
		return catStringName;
	}
	
	public Vector<String> getImages(){
		return imageHolder; 
	}
	
	public String getFile(){
		File f = new File(fileName);
		return fileName;
	}
	
	
}
