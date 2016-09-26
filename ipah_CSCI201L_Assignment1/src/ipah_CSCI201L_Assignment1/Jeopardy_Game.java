package ipah_CSCI201L_Assignment1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;



public class Jeopardy_Game  {
	
	private int numberOfTeams = 1;
	private String fileName; 
	private int numberOfQuestionsAsked = 0; // counts number of questions asked so far.
	private Vector<Categories> categories = new Vector<Categories>();
	private ArrayList<String> pointVals = new ArrayList<String>();
	private ArrayList<String> catStringName = new ArrayList<String>();
	
	private FinalJeopardyQuestion fj = new FinalJeopardyQuestion();
	
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
		/*
		else if(Arrays.equals(questArray, answer) ){//if they dont put in form of question
			System.out.println("Please enter answer again in the correct format.");
			String [] second = response.nextLine().toLowerCase().split(" ");
			if( second[0].toLowerCase().equals("who") || second[0].toLowerCase().equals("what") || second[0].toLowerCase().equals("where") || second[0].toLowerCase().equals("when")){
				if( (second[1].toLowerCase().equals("is") || second[1].toLowerCase().equals("are")) &&(second[2].toLowerCase().equals("the") || second[2].toLowerCase().equals("a"))){ //if "the"/ "a" article is needed
					String [] subarray = Arrays.copyOfRange(second, 3, second.length);
					String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
					
					
					if(Arrays.equals(subarray, qArray)){
						team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
						System.out.println("Correct!");
					}
					else{
						System.out.println("Incorrect!");
						team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
					}
				}
				else{//cases when there is no "the" article
					String [] subarray = Arrays.copyOfRange(second, 2, second.length);
					String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
					for(int i = 0; i < qArray.length; i++){
						System.out.print("file: "+ qArray[i]);
					}
					

					
					if(Arrays.equals(subarray, qArray)){
						team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
						System.out.println("Correct!");
					}
					else{
						System.out.println("Incorrect!");
						team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
					}
				}
			}
			else{ //incorrect format for the second time
				System.out.println("Incorrect!");
				team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
				
			}
		}*/
		else{//go to next team
			/*
			System.out.println("Incorrect!");
			team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));//subtract points from their score
			*/
			return false; 
		}
	//}
		//System.out.println(current_team);
		/*	
		if( current_team == numberOfTeams - 1){
			current_team = 0;
		}
		else{
			current_team++;
		}
		
		/*System.out.println("Team " + team_holder.get(current_team).getName() +"'s turn.");
		if(allQuestionsFinished()){
			break; 
		}*/
}//end of while loop

	
	
	
	
	
	
	public Jeopardy_Game(String file) throws FileNotFoundException{//displays first prompts
		fileName = file;
		File_reader(fileName);
		
		//System.out.println("in: " + numOfTeams);
		System.out.println("WHAT'S HAPPENING?");

		
		Scanner response = new Scanner(System.in);
		
		
		
		//System.out.println("Please enter the number of teams that will be playing this game.");
				
		//System.out.println("in2: " + numberOfTeams);
		String num_teams; 
		
	/*	num_teams= response.nextLine();
		if(num_teams.toLowerCase().equals("exit")){
			exit();
		}
		if(num_teams.toLowerCase().equals("replay")){
			replay();
		}
			
		while(!num_teams.equals("1") && !num_teams.equals("2") && !num_teams.equals("3") && !num_teams.equals("4") ){
			
			System.out.println("Please enter a valid number of teams.");
			
			System.out.println("Please enter the number of teams that will be playing this game.");
			//response = new Scanner(System.in);
			num_teams = response.nextLine();
			if(num_teams.toLowerCase().equals("exit")){
				exit();
			}
			if(num_teams.toLowerCase().equals("replay")){
				replay();
			}
			
			
		}
					
		System.out.println("There are " + numberOfTeams + " teams playing Jeopardy.");
		//System.out.println("teams.getValue(): " + teams.getValue());
		//int team_integer = Integer.parseInt(num_teams);
		//setNumOfTeams(team_integer);
				
		String name;*/
		//numberofteams is 0 at this point, figure out why
		
		//System.out.println( numberOfTeams);
		/*
		for(int i = 1; i <numberOfTeams+1; i++){
			
			System.out.println("Please choose a name for Team " + i + ".");
			
			name = response.nextLine();
			if(name.toLowerCase().equals("exit")){
				exit();
			}
			if(name.toLowerCase().equals("replay")){
				replay();
			}
			
			
			
			addTeamToGame(name);
			
			
			System.out.println("Team " + i+ " your name is: " + name);
			
			
			
				
			}
	
		for( int i = 0; i < numberOfTeams; i++){
			addTeamToGame(textFieldHolder.get(i).getText());
			System.out.println("Team name: " +textFieldHolder.get(i).getText());
		}
		
		//randomly chooses which team will start first
		Random random = new Random();
		int current_team;
		if( numberOfTeams ==1){
			current_team = 0;
		}
		else{
			current_team= random.nextInt(numberOfTeams) ; // team that will start first
		}
		String choice;
		String dollars;
		//System.out.println("first" + current_team);
		//System.out.println("Team " + team_holder.get(current_team).getName() + "'s turn.");		//ask first team their question
		while( (current_team < numberOfTeams) || numberOfTeams == 1){
			
			System.out.println("Please choose a category: ");
			choice = response.nextLine();
			if(choice.toLowerCase().equals("exit")){
				exit();
			}
			if(choice.toLowerCase().equals("replay")){
				replay();
			}
			while(!isCategory(choice)){
				System.out.println("Invalid category. Please enter another.");
				choice = response.nextLine();
				if(choice.toLowerCase().equals("exit")){
					exit();
				}
				if(choice.toLowerCase().equals("replay")){
					replay();
				}

			}
			
			System.out.println("Please enter the dollar value of the question you wish to answer: ");
			dollars = response.nextLine();
			if(dollars.toLowerCase().equals("exit")){
				exit();
			}
			if(dollars.toLowerCase().equals("replay")){
				replay();
			}

			
			while(!isScore(dollars)){
				System.out.println("Invalid dollar amount. Please enter another.");
				dollars = response.nextLine();
				if(choice.toLowerCase().equals("exit")){
					exit();
				}
				if(dollars.toLowerCase().equals("replay")){
					replay();
				}

			}
			
			
			
			while(getCategory(choice).getQuestion(dollars).questionUsed() == true){ // if question has been asked
				System.out.println("Sorry, question has already been asked.");
				
				System.out.println("Please choose a category: ");
				choice = response.nextLine();
				if(choice.toLowerCase().equals("exit")){
					exit();
				}
				if(choice.toLowerCase().equals("replay")){
					replay();
					continue;
				}

				while(!isCategory(choice)){
					System.out.println("Invalid category. Please enter another.");
					choice = response.nextLine();
					if(choice.toLowerCase().equals("exit")){
						exit();
					}
					if(choice.toLowerCase().equals("replay")){
						replay();
						continue;
					}

				}
				
				System.out.println("Please enter the dollar value of the question you wish to answer: ");
				dollars = response.nextLine();
				if(dollars.toLowerCase().equals("exit")){
					exit();
				}
				if(dollars.toLowerCase().equals("replay")){
					replay();
					continue;
				}

				while(!isScore(dollars)){
					System.out.println("Invalid dollar amount. Please enter another.");
					dollars = response.nextLine();
					if(dollars.toLowerCase().equals("exit")){
						exit();
					}
					if(dollars.toLowerCase().equals("replay")){
						replay();
					}
				}
			}
						
						
						
				//hasnt been asked, then ask
			getCategory(choice).askQ(dollars); //asks question
			getCategory(choice).getQuestion(dollars).asked(); // marks as asked
			
			String[] answer = response.nextLine().toLowerCase().split(" ");
			
			
			String [] questArray = 	getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
			
			if( answer[0].toLowerCase().equals("who") || answer[0].toLowerCase().equals("what") || answer[0].toLowerCase().equals("where") || answer[0].toLowerCase().equals("when")){
				
				if( (answer[1].toLowerCase().equals("is") || answer[1].toLowerCase().equals("are")) && (answer[2].toLowerCase().equals("the") || answer[2].toLowerCase().equals("a")) ){ //if "the" article is needed
				
					String [] subarray = Arrays.copyOfRange(answer, 3, answer.length);
					String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
					
					if(Arrays.equals(qArray, subarray)){
						team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
						System.out.println("Correct!");
					}
					else{
						System.out.println("Incorrect!");
						team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
					}
					//FinalJeopardy();
				}
				else{
					String [] subarray = Arrays.copyOfRange(answer, 2, answer.length);
					String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
					for(int i = 0; i < questArray.length; i++){
						System.out.print("filr: "+ questArray[i]);
					}
					
					for(int i = 0; i < answer.length; i++){
						System.out.print("filr: "+ answer[i]);
					}
					if(Arrays.equals(subarray,  qArray)){
						team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
						System.out.println("Correct!");
					}
					else{
						System.out.println("Incorrect!");
						team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
					}
				}
			}
			
			else if(Arrays.equals(questArray, answer) ){//if they dont put in form of question
				System.out.println("Please enter answer again in the correct format.");
				String [] second = response.nextLine().toLowerCase().split(" ");
				if( second[0].toLowerCase().equals("who") || second[0].toLowerCase().equals("what") || second[0].toLowerCase().equals("where") || second[0].toLowerCase().equals("when")){
					if( (second[1].toLowerCase().equals("is") || second[1].toLowerCase().equals("are")) &&(second[2].toLowerCase().equals("the") || second[2].toLowerCase().equals("a"))){ //if "the"/ "a" article is needed
						String [] subarray = Arrays.copyOfRange(second, 3, second.length);
						String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
						
						
						if(Arrays.equals(subarray, qArray)){
							team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
							System.out.println("Correct!");
						}
						else{
							System.out.println("Incorrect!");
							team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
						}
					}
					else{//cases when there is no "the" article
						String [] subarray = Arrays.copyOfRange(second, 2, second.length);
						String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
						for(int i = 0; i < qArray.length; i++){
							System.out.print("file: "+ qArray[i]);
						}
						
	
						
						if(Arrays.equals(subarray, qArray)){
							team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
							System.out.println("Correct!");
						}
						else{
							System.out.println("Incorrect!");
							team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
						}
					}
				}
				else{ //incorrect format for the second time
					System.out.println("Incorrect!");
					team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
					
				}
			}
			else{//go to next team
				
				System.out.println("Incorrect!");
				team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));//subtract points from their score
				
			}
		//}
			//System.out.println(current_team);
				
			if( current_team == numberOfTeams - 1){
				current_team = 0;
			}
			else{
				current_team++;
			}
			
			System.out.println("Team " + team_holder.get(current_team).getName() +"'s turn.");
			if(allQuestionsFinished()){
				break; 
			}
		}//end of while loop
		FinalJeopardy();*/
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
		for(int i = 0; i < team_holder.size(); i++){
			if(team_holder.get(i).getScore() >= max){
				max = team_holder.get(i).getScore();
				maxIndex = i;
			}
			if( team_holder.get(i).getScore() == max ){
				//TODO when there is a tie
				return (team_holder.get(maxIndex).getName() +" your score is: "+ team_holder.get(i).getScore());
			}
		}
		return (team_holder.get(maxIndex).getName() +" your score is: " + team_holder.get(maxIndex).getScore() );
			
	}
	public boolean isBetValid(Jeopardy_Teams team, int bet){
		if(bet > 0 && bet <= team.getScore()){
			return true;
		}
		return false;
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
		
		if(catSplit.length < 5){
			System.out.println("ERROR: Invalid file format. Not enough data in this section.");
			scanner.close();
			System.exit(0);
		}
		else if(catSplit.length > 5){
			System.out.println("ERROR:Invalid file format. Too much data in this section.");
			scanner.close();
			System.exit(0);
		}
		else{
			for(int i =0; i< catSplit.length; i++){
				catStringName.add(catSplit[i]); //saves string version of categories
				Categories new_cat = new Categories(catSplit[i]); 
				categories.addElement(new_cat); 
			}
		}
		
		
		for(int i = 0; i < catSplit.length; i++){
			if(catSplit[i].equals("") || catSplit[i].equals(" ")){
				System.out.println("ERROR: Incorrect file format. Section contains whitespace/empty string.");
				System.exit(0);
			}
		}
		
		//Reads and stores points
		srcSplit = scanner.nextLine().split("::");
	
		if(srcSplit.length < 5){
			System.out.println("ERROR: Invalid file format. Not enough data in this section.");
			scanner.close();
			System.exit(0);
		}
		else if(srcSplit.length > 5){
			System.out.println("ERROR:Invalid file format. Too much data in this section.");
			scanner.close();
			System.exit(0);
		}
		else{
			for(int i =0; i< srcSplit.length; i++){
				pointVals.add(srcSplit[i]);
				
			}
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
			/*System.out.println(split.length);
			for( int i = 0; i < split.length; i++){
				System.out.println(split[i]);
			}*/
			if(split[1].equals("FJ")  ){// final Jeopardy 
				//create final jeopardy question
				/*Categories finalJ = new Categories(split[1]);
				categories.addElement(finalJ);
				Questions fj = new Questions();
				fj.makeFJQuest(split[2],split[3]);
				getCategory(split[1]).addQuestions(fj);	*/
				if(split.length < 4){
					System.out.println("ERROR: Invalid file format. Not enough data in this section.");
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
			}	
			else if(split.length < 5){
				System.out.println("ERROR: Invalid file format. Not enough data in this section.");
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
			if( isCategory(split[1])  && isScore(split[2]) ){
				//System.out.println(split[1]);				//then should make question
				Questions q = new Questions();
				q.makeQuestions(split[2], split[3], split[4]);
				getCategory(split[1]).addQuestions(q); 
			}
			
			
			
		}//end of while loop.	
		
		for(int i = 0; i < categories.size(); i++){
			if(categories.get(i).getVecSize() < 5){
				System.out.println("ERROR: Incorrect file format. There are not enough questions present.Cannot play game.");
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
	
	public void incrementQuestionsAsked(){
		numberOfQuestionsAsked++;
		System.out.println("in function " + numberOfQuestionsAsked);
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
	
	public Vector<Jeopardy_Teams> getTeamHolder(){
		return team_holder;
	}
	
	public ArrayList<String> getPointVals(){
		return pointVals;
	}
	public ArrayList<String> getCatStringName(){
		return catStringName;
	}
	
	
}
