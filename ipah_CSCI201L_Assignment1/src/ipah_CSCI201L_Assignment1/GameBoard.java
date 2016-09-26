package ipah_CSCI201L_Assignment1;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;



public class GameBoard extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Jeopardy_Game game;
	private JFrame gameWindow; 
	private CardLayout cardLayout; 
	private int numberOfTeams = 1;
	private int current_team;//team that is currently answering a question
	private JTextArea question;
	private JPanel rightPanel;
	private JPanel questionBoxes;
	private JLabel t1ScoreLabel,t2ScoreLabel,t3ScoreLabel,t4ScoreLabel;
	private Vector<JLabel> tScoreLabels; 
	private Vector<String> teams = new Vector<String>();
	private JTextField ans;
	private JButton submit;
	private String _gameType; //tells if its quickplay or regular play
	private JSlider team1Slider, team2Slider, team3Slider,team4Slider;
	private Vector<JSlider> sliderHolder = new Vector<JSlider>();
	private Hashtable<Integer, JLabel> sliderTable;
	private JButton fjT1, fjT2, fjT3, fjT4; 
	private JTextArea text;
	public void tProgressUpdate(){
		
	}
	public void tScoreUpdate(){ //updates score panel
		for( int i = 0; i < teams.size(); i++){
			tScoreLabels.get(i).setText(teams.get(i) + "  " + "$"+ game.getTeamHolder().get(i).getScore());
		}
	}
	
	public void restart(){
		for( int i = 0; i < teams.size(); i++){
			game.getTeamHolder().get(i).resetScore();
			tScoreLabels.get(i).setText(teams.get(i) + "  " + "$"+ game.getTeamHolder().get(i).getScore() );
		}
	}
	
	public void setTeams(Vector<String> t){
		for(int i =0; i < t.size(); i++){
			game.addTeamToGame(t.get(i));
			System.out.println(t.get(i));
		}
	}
	
	GameBoard(String f, Vector<String> teamsFromUser, String gameType){
		_gameType = gameType; 
		try {
			game = new Jeopardy_Game(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTeams(teamsFromUser);
		teams = teamsFromUser; 
		numberOfTeams = teamsFromUser.size();
		initializeComps();
		System.out.println(f);
		createGUI();
		
		gameWindow.setVisible(true);
		
		System.out.println("end of constructor");
	}
	
	
	private void createMenu(){
		JMenuBar menu = new JMenuBar(); //Jeopardy Menu Window
		JMenu m = new JMenu("Menu");
		menu.add(m);
		JMenuItem mExit =  new JMenuItem("Exit Game");
		JMenuItem mRestart = new JMenuItem("Restart this Game");
		JMenuItem mChooseNew = new JMenuItem("Choose New Game File");
		
		m.add(mRestart);
		m.add(mChooseNew);
		m.add(mExit);
		gameWindow.setJMenuBar(menu);
		
		
		
		mExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		
		mRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				game.replay();
				text.setText("");
				restart(); //sets scores to 0
				
				Random random = new Random();
				
				if( numberOfTeams ==1){
					current_team = 0;
				}
				else{
					current_team= random.nextInt(numberOfTeams) ; // team that will start first
				}
				
				text.setText("Welcome to Jeopardy! " + game.getTeamHolder().get(current_team).getName()+ " will be going first!" + "\n");
				
			}
			
		});
		
		mChooseNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	public void initializeComps(){
		
		gameWindow = new JFrame("Jeopardy!");
		
		gameWindow.setLayout(new BorderLayout());
		cardLayout = new CardLayout();
		
		tScoreLabels = new Vector<JLabel>();
		t1ScoreLabel = new JLabel();
		t2ScoreLabel = new JLabel();
		t3ScoreLabel = new JLabel();
		t4ScoreLabel = new JLabel();
		
		tScoreLabels.add(t1ScoreLabel);
		tScoreLabels.add(t2ScoreLabel);
		tScoreLabels.add(t3ScoreLabel);
		tScoreLabels.add(t4ScoreLabel);
		
		sliderHolder.add(team1Slider);
		sliderHolder.add(team2Slider);
		sliderHolder.add(team3Slider);
		sliderHolder.add(team4Slider);
		
		fjT1 = new JButton("Set Bet");
		fjT2 = new JButton("Set Bet");
		fjT3 = new JButton("Set Bet");
		fjT4 = new JButton("Set Bet");
		
	}
	
	
	private void createGUI(){ //
		//pane.setLayout( new BorderLayout() );
		
		
		createMenu();
		gameWindow.setSize(1500, 1000);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2,1));
		JPanel h = new JPanel( new GridLayout(4,1));
		h.removeAll();
	    h.revalidate();
		h.repaint();
	
		//CURRENT TEAM SCORES
		for(int i = 0; i < numberOfTeams; i++){ // adds team names to pane
			tScoreLabels.get(i).setText(teams.get(i) + "  " + "$"+ game.getTeamHolder().get(i).getScore());
			tScoreLabels.get(i).setFont(tScoreLabels.get(i).getFont().deriveFont(32.0f));
			System.out.println(teams.get(i));
			h.add(tScoreLabels.get(i));
		}
		
		h.setBorder(BorderFactory.createLineBorder(Color.black));
		//h.setBackground(Color.black);
		
		rightPanel.add(h); 
		//PROGRESS 
		JPanel cont = new JPanel(new BorderLayout());
		text = new JTextArea(5,5);
		//text.setFont(text.getFont().deriveFont(25.0f));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setFont(text.getFont().deriveFont(20.0f));
		JScrollPane progressScroll = new JScrollPane(text);
		
		
		cont.add(progressScroll);
		//progress.setHbarPolicy(ScrollBarPolicy.NEVER);
		progressScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		progressScroll.setPreferredSize(new Dimension(25,25));
		
		cont.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//progress.setBackground(Color.blue);
		JLabel gLabel = new JLabel("Game Progress");
		cont.add(gLabel, BorderLayout.NORTH);
		gLabel.setFont(gLabel.getFont().deriveFont(40.0f));
		gLabel.setHorizontalAlignment(JLabel.CENTER);
		//Progress Pane should now say who's going first
		Random random = new Random();
		
		if( numberOfTeams ==1){
			current_team = 0;
		}
		else{
			current_team= random.nextInt(numberOfTeams) ; // team that will start first
		}
		//JLabel upFirst = new JLabel("Welcome to Jeopardy! " + game.getTeamHolder().get(current_team).getName()+ " will be going first!");
		//upFirst.setFont(upFirst.getFont().deriveFont(15.0f));
		text.setText("Welcome to Jeopardy! " + game.getTeamHolder().get(current_team).getName()+ " will be going first!" + "\n");
	
		//cont.add(progress);
		rightPanel.add(cont);
		
		rightPanel.setPreferredSize(new Dimension(400,400));
		
		
		//CREATING QUESTION SELECTION BOXES
		questionBoxes = new JPanel();
		questionBoxes.setLayout(cardLayout);
		//questionBoxes.setLayout(new BorderLayout());
				
		
		questionBoxes.setBorder(BorderFactory.createMatteBorder(10,10,40,10, Color.gray));
		//Object[][] scoreBoxes = { {pointVals}, {pointVals}, {pointVals}, {pointVals},{pointVals} };
		
		
		Object[][] data = {
			    {game.getPointVals().get(0), game.getPointVals().get(0),game.getPointVals().get(0),game.getPointVals().get(0), game.getPointVals().get(0)},
			    {game.getPointVals().get(1),game.getPointVals().get(1),game.getPointVals().get(1),game.getPointVals().get(1), game.getPointVals().get(1)},
			    {game.getPointVals().get(2),game.getPointVals().get(2),game.getPointVals().get(2),game.getPointVals().get(2), game.getPointVals().get(2)},
			    {game.getPointVals().get(3),game.getPointVals().get(3),game.getPointVals().get(3),game.getPointVals().get(3), game.getPointVals().get(3)},
			    {game.getPointVals().get(4),game.getPointVals().get(4),game.getPointVals().get(4),game.getPointVals().get(4), game.getPointVals().get(4)}
			};
		
		Object [] holder = new String[5];
		for(int i = 0; i < game.getCatStringName().size(); i++){
			holder[i] = game.getCatStringName().get(i);
		
		}
		TableModel model = new DefaultTableModel(data, holder); 
		JTable questB = new JTable(model);
		questB.getTableHeader().setReorderingAllowed(false);
		questB.setCellSelectionEnabled(true); 
		TableColumn col = null; 
		
		for( int i = 0; i < 5; i++){
			col = questB.getColumnModel().getColumn(i);
			col.setPreferredWidth(100);
			 
		}
		questB.setRowHeight(100);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //CENTERS TEXT IN CELL
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		questB.setDefaultRenderer(String.class, centerRenderer);
		
		
		JPanel qScreen = new JPanel( new BorderLayout()); //CARD 1: QUESTION SCREEN
		qScreen.add(questB, BorderLayout.CENTER);
		//questionBoxes.add(questB, BorderLayout.CENTER);
		qScreen.add(questB.getTableHeader(), BorderLayout.NORTH);
		//questionBoxes.setBackground(Color.green);
		
		//ANSWER SCREEN
		JPanel aScreen = new JPanel(new BorderLayout());//screen for user to answer question
		JPanel submitPanel = new JPanel();
		JPanel questionDisplay = new JPanel();
		JPanel info = new JPanel(new GridLayout(1,3)); //panel with team category and question score
		
		info.setBackground(Color.blue);
		JLabel team = new JLabel("put team name here");
		JLabel cat = new JLabel("put category here");
		JLabel points = new JLabel("put points here");
		info.add(team);
		info.add(cat);
		info.add(points);
		//info.setSize(new Dimension(300, 24));
		
		
		ans = new JTextField();
		ans.setPreferredSize( new Dimension( 200, 24 ) );
		submit = new JButton("Submit");
		submitPanel.add(ans);
		submitPanel.add(submit);
		
		question = new JTextArea(15,25);
		question.setEditable(false);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		//question.setFont(upFirst.getFont().deriveFont(28.0f));
		questionDisplay.add(question);
		questionDisplay.setSize( new Dimension(100,100));
		questionDisplay.setBackground(new Color(102,178,255));
		
		aScreen.add(info, BorderLayout.NORTH);
		aScreen.add(questionDisplay, BorderLayout.CENTER);
		aScreen.add(submitPanel, BorderLayout.PAGE_END);
		
		aScreen.setBorder(BorderFactory.createLineBorder(Color.black));

		
		
		JPanel fjScreen = new JPanel(new BorderLayout());
		JLabel fjLabel = new JLabel("Final Jeopardy!");
		fjLabel.setFont(fjLabel.getFont().deriveFont(45.0f));
		fjScreen.add(fjLabel, BorderLayout.PAGE_START);
		
		JPanel fjSliderPanel = new JPanel(new GridBagLayout());
		
		
		fjScreen.add(fjSliderPanel, BorderLayout.CENTER);
		
		JPanel fjQuestion = new JPanel();
		JLabel qLabel = new JLabel(game.getFJQuestion());
		fjQuestion.add(qLabel);
		fjScreen.add(qLabel, BorderLayout.SOUTH);
		questionBoxes.add(qScreen, "1");
		questionBoxes.add(aScreen, "2");
		questionBoxes.add(fjScreen,"3");
		cardLayout.show(questionBoxes, "1");
		
		
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String a = ans.getText();
				String currScore = (String) data[questB.getSelectedRow()][questB.getSelectedColumn()];
				String getCat = game.getCatStringName().get(questB.getSelectedColumn());
				boolean rightAnswer = game.isCorrect(a, game.getCategory(getCat).getQuestion(currScore).getAnswer());
				
				//addToPane = new JLabel(game.getTeamHolder().get(current_team).getName()+" got the answer correct. $" + currScore + " will be added to your score.");
				
				if(rightAnswer){
					//add points to team
					//redisplay 
					
					game.getTeamHolder().get(current_team).changeScore(Integer.parseInt(currScore));
					text.append(game.getTeamHolder().get(current_team).getName()+" got the answer correct. $" + currScore + " will be added to your score." + "\n");
					
					System.out.println("Correct!");
					cardLayout.show(questionBoxes, "1");
				}
				else{
					game.getTeamHolder().get(current_team).changeScore((-1)*Integer.parseInt(currScore));
					text.append(game.getTeamHolder().get(current_team).getName()+" got the answer incorrect. $" + currScore + " will be deducted from to your score." + "\n");
					cardLayout.show(questionBoxes, "1");
				}
				
				tScoreUpdate();
				
				
				current_team++;
				if( current_team == teams.size()){
					current_team = 0;
				}
				int max; //max value for slider
				if( ( _gameType.equals("Quick Play" ) && game.quickPlayFinished() ) || ( _gameType.equals("Standard") &&game.allQuestionsFinished() ) ){ //if nec. questions have been asked
					text.append("All questions have been finished. Time for final Jeopardy!");
					
					GridBagConstraints c = new GridBagConstraints();
					
					
					if(numberOfTeams ==1){
						c.gridx = 0;
						c.gridy = 0;
						c.weightx = 1.0; 
								
						max = Math.abs(game.getTeamHolder().get(0).getScore());
						team1Slider = new JSlider(JSlider.HORIZONTAL,0,max,0);
						fjSliderPanel.add(new JLabel(teams.get(0)), c );
						
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team1Slider, c);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT1, c);
						
						team1Slider.setSize(20, 4);
						team1Slider.revalidate();
						team1Slider.repaint();
						fjSliderSetup(team1Slider, max);
					}
					
					if(numberOfTeams ==2){
						int max1 = Math.abs(game.getTeamHolder().get(0).getScore());
						team1Slider = new JSlider(JSlider.HORIZONTAL,0,max1,0);
						
						int max2 = Math.abs(game.getTeamHolder().get(1).getScore());
						team2Slider = new JSlider(JSlider.HORIZONTAL,0,max2,0);
						
						c.gridx = 0;
						c.gridy = 0;
						c.weightx = 1.0; 
						fjSliderPanel.add(new JLabel(teams.get(0)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team1Slider,c);
						fjSliderSetup(team1Slider, max1);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT1, c);
						
						c.gridx = 0; 
						c.gridy = 1;
						fjSliderPanel.add(new JLabel(teams.get(1)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team2Slider, c);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT1, c);
						fjSliderSetup(team2Slider, max2);
					}
					if(numberOfTeams ==3){
						int max1 = Math.abs(game.getTeamHolder().get(0).getScore());
						team1Slider = new JSlider(JSlider.HORIZONTAL,0,max1,0);
						
						int max2 = Math.abs(game.getTeamHolder().get(1).getScore());
						team2Slider = new JSlider(JSlider.HORIZONTAL,0,max2,0);
						
						int max3 = Math.abs(game.getTeamHolder().get(2).getScore());
						team3Slider = new JSlider(JSlider.HORIZONTAL,0,max3,0);
						
						c.gridx = 0;
						c.gridy = 0;
						c.weightx = 1.0; 
						fjSliderPanel.add(new JLabel(teams.get(0)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team1Slider,c);
						fjSliderSetup(team1Slider, max1);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT1, c);
						
						c.gridx = 0;
						c.gridy = 1;
						fjSliderPanel.add(new JLabel(teams.get(1)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team2Slider, c);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT2, c);
						fjSliderSetup(team2Slider, max2);
						
						c.gridx = 0;
						c.gridy = 2;
						fjSliderPanel.add(new JLabel(teams.get(2)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team2Slider, c);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT3, c);
						fjSliderSetup(team2Slider, max3);
						
					}
					if(numberOfTeams ==4){
						int max1 = Math.abs(game.getTeamHolder().get(0).getScore());
						team1Slider = new JSlider(JSlider.HORIZONTAL,0,max1,0);
						
						int max2 = Math.abs(game.getTeamHolder().get(1).getScore());
						team2Slider = new JSlider(JSlider.HORIZONTAL,0,max2,0);
						
						int max3 = Math.abs(game.getTeamHolder().get(2).getScore());
						team3Slider = new JSlider(JSlider.HORIZONTAL,0,max3,0);
						
						int max4 = Math.abs(game.getTeamHolder().get(3).getScore());		
						team4Slider = new JSlider(JSlider.HORIZONTAL,0,max4,0);
						
						c.gridx = 0;
						c.gridy = 0;
						c.weightx = 1.0; 
						fjSliderPanel.add(new JLabel(teams.get(0)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team1Slider,c);
						fjSliderSetup(team1Slider, max1);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT1, c);
						
						c.gridx = 0;
						c.gridy = 1;
						fjSliderPanel.add(new JLabel(teams.get(1)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team2Slider, c);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT2, c);
						fjSliderSetup(team2Slider, max2);
						
						c.gridx = 0;
						c.gridy = 2;
						fjSliderPanel.add(new JLabel(teams.get(2)),c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team2Slider, c);
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT3, c);
						fjSliderSetup(team2Slider, max3);
						
						c.gridx = 0;
						c.gridy = 3;
						fjSliderPanel.add(new JLabel(teams.get(3)), c );
						c.gridx = 1;
						c.weightx = 2.0;
						fjSliderPanel.add(team4Slider, c);	
						c.gridx = 2;
						c.weightx = 1.0;
						fjSliderPanel.add(fjT4, c);
						fjSliderSetup(team4Slider, max4);
					}
					
					
					
					
					
					cardLayout.show(questionBoxes, "3");
				}
				
				else{ //keep playing
					text.append("It is " + teams.get(current_team) + "'s turn." );
					
				}
				 //game.printNumOfQuest();
				
			}
			
		});
		
		
		questB.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//should display question
				//should switch to other cardPanel
				
				//System.out.println("row: " +questB.getSelectedRow());
				//System.out.println("col: " +questB.getSelectedColumn());
				ans.setText("");// clears textField
				String currScore = (String) data[questB.getSelectedRow()][questB.getSelectedColumn()];
				String getCat = game.getCatStringName().get(questB.getSelectedColumn() );
				
				
				if(game.getCategory(getCat).getQuestion(currScore).questionUsed()){ //
					//DO NOTHING
				}
				//System.out.println(currScore);
				//System.out.println(getCat);
				else{
					text.append(teams.get(current_team) + " has selected " + getCat + " for $"+ currScore + "." + "\n");
					cardLayout.show(questionBoxes, "2");
					question.setText(game.getCategory(getCat).getQuestion(currScore).getQuestionName());
				
					game.getCategory(getCat).getQuestion(currScore).asked();//says marks a question as asked
					game.incrementQuestionsAsked();
				}
				//Rectangular r = new Rectangular();
				
				
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		gameWindow.add(rightPanel, BorderLayout.LINE_END);
		gameWindow.add(questionBoxes, BorderLayout.CENTER);
		
		
	}

	public void fjSliderSetup(JSlider slider, int max){
		//slider.setSize(300, 30);
		
		if(max <= 100){
			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(5);
		}
		
		else if(max <=  1000){
			slider.setMajorTickSpacing(100);
			slider.setMinorTickSpacing(50);
		}
		else{
			slider.setMajorTickSpacing(200);
			slider.setMinorTickSpacing(100);
		}
		
		
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		
	}
	
	
}
