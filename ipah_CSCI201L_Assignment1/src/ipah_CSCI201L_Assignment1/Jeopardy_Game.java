package ipah_CSCI201L_Assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Jeopardy_Game {
	private String fileName; 
	
	
	public void startGame(String file) throws FileNotFoundException{//displays first prompts
		
		fileName = file;
		
		Scanner response = new Scanner(System.in);
		
		File_reader(fileName);
		
		System.out.println("Please enter the number of teams that will be playing this game.");
				
		String num_teams; 
		
		num_teams= response.nextLine();
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
					
		System.out.println("There are " + num_teams + " teams playing Jeopardy.");
		
		int team_integer = Integer.parseInt(num_teams);
		setNumOfTeams(team_integer);
				
		String name;
		
		for(int i = 1; i <team_integer+1; i++){
			
			System.out.println("Please choose a name for Team " + i + ".");
			
			name = response.nextLine();
			if(name.toLowerCase().equals("exit")){
				exit();
			}
			if(num_teams.toLowerCase().equals("replay")){
				replay();
			}
			
			
			
			addTeamToGame(name);
			
			
			System.out.println("Team " + i+ " your name is: " + name);
			
			
			
				
			}
		//randomly chooses which team will start first
		Random random = new Random();
		int current_team;
		if( team_integer ==1){
			current_team = 0;
		}
		else{
			current_team= random.nextInt(team_integer) ; // team that will start first
		}
		String choice;
		String dollars;
		System.out.println("first" + current_team);
		System.out.println("Team " + team_holder.get(current_team).getName() + "'s turn.");		//ask first team their question
		while( (current_team < team_integer) || team_integer == 1){
			
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
			
			if( answer[0].toLowerCase().equals("who") || answer[0].toLowerCase().equals("what")){
				
				if(answer[2].toLowerCase().equals("the")){ //if "the" article is needed
				
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
				}
				else{
					String [] subarray = Arrays.copyOfRange(answer, 2, answer.length);
					String [] qArray = getCategory(choice).getQuestion(dollars).getAnswer().toLowerCase().split(" ");
					if(subarray.equals(qArray)){
						team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
						System.out.println("Correct!");
					}
					else{
						System.out.println("Incorrect!");
						team_holder.get(current_team).changeScore((-1)*Integer.parseInt(dollars));
					}
				}
			}
			
			/*for(int i = 0; i < questArray.length; i++){
				System.out.print("filr: "+ questArray[i]);
			}
			
			for(int i = 0; i < answer.length; i++){
				System.out.print("filr: "+ answer[i]);
			}*/
			else if(Arrays.equals(questArray, answer) ){//if they dont put in form of question
				System.out.println("Please enter answer again in the correct format.");
				String [] second = response.nextLine().toLowerCase().split(" ");
				if( second[0].toLowerCase().equals("who") || second[0].toLowerCase().equals("what")){
					if(second[2].toLowerCase().equals("the")){ //if "the" article is needed
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
						if(subarray.equals(qArray)){
							team_holder.get(current_team).changeScore(Integer.parseInt(dollars));
							System.out.println("Correct!");
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
			System.out.println(current_team);
				
			if( current_team == team_integer - 1){
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
		FinalJeopardy();
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
			System.out.println("Team " + team_holder.get(i)+" please enter your guess.");
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
				System.out.println(team_holder.get(i).getName() + " correct!");
			}
			else{//incorrect answer
				int addBet = Integer.parseInt(team_holder.get(i).getBetAmount());
				team_holder.get(i).changeScore((-1)*addBet);
				System.out.println(team_holder.get(i).getName() + " incorrect!");
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
				return (team_holder.get(maxIndex).getName() + team_holder.get(i).getScore());
			}
		}
		return team_holder.get(maxIndex).getName();
			
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
		
		if(catSplit.length != 5){
			System.out.println("ERROR:Invalid number of categories.");
			scanner.close();
			return;
		}
		else{
			for(int i =0; i< catSplit.length; i++){
				Categories new_cat = new Categories(catSplit[i]); 
				categories.addElement(new_cat); 
			}
		}
		
		//Reads and stores points
		srcSplit = scanner.nextLine().split("::");
	
		if(srcSplit.length != 5){
			System.out.println("ERROR:Invalid number of categories.");
			scanner.close();
			return;
		}
		else{
			for(int i =0; i< srcSplit.length; i++){
				pointVals[i] = srcSplit[i];
			}
		}
		
		//Reads rest of file
		while(scanner.hasNextLine()){
			String[] split = scanner.nextLine().split("::");
			//split[1] = category; 2 = score; 3 = question; 4 = answer
			
			if( isCategory(split[1])  && isScore(split[2]) ){
				//System.out.println(split[1]);				//then should make question
				Questions q = new Questions();
				q.makeQuestions(split[2], split[3], split[4]);
				getCategory(split[1]).addQuestions(q); 
			}
			
			else if(split[1].equals("FJ") ){// final Jeopardy 
				//create final jeopardy question
				/*Categories finalJ = new Categories(split[1]);
				categories.addElement(finalJ);
				Questions fj = new Questions();
				fj.makeFJQuest(split[2],split[3]);
				getCategory(split[1]).addQuestions(fj);	*/
				fj.setFJQuestion(split[2]);
				fj.setFJAnswer(split[3]);
			}	
		}	
		scanner.close();
	}
	
	
	//TEAMS 
	private int numberOfTeams;
	private Vector<Jeopardy_Teams> team_holder = new Vector<Jeopardy_Teams>();; //array that holds teams.

	public void setNumOfTeams(int num){
		numberOfTeams = num;
		
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
	
	
	private Vector<Categories> categories = new Vector<Categories>();
	private String[] pointVals = new String[5];
	
	private FinalJeopardyQuestion fj = new FinalJeopardyQuestion();

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
		for(int i = 0; i<pointVals.length; i++){
			if( pointVals[i].equals(score)){
				return true;
			}
		}
		return false;
	}
	public void addCategories(Categories cat){
		categories.addElement(cat);
	}
	public void setPointVals(String[] vals){
		pointVals = vals; 
	}
	
	public void exit(){
		System.out.println("Game over...");
		System.exit(0);
	
	}
	public void replay(){
		System.out.println("Restarting game...");
		for(int i = 0; i < team_holder.size(); i++){
			team_holder.get(i).resetScore();
		}
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				categories.get(i).getQuestionwIndex(j).resetQuestions();
			}
		}
	}
	
	
}
