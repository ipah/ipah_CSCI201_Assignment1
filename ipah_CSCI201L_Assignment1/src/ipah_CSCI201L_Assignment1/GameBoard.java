package ipah_CSCI201L_Assignment1;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;



public class GameBoard extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Jeopardy_Game mGameData;
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
	private JButton submit, pass;
	private String _gameType; //tells if its quickplay or regular play
	private JPanel h; //panel for player scores
	
	private Vector<JSlider> sliderHolder = new Vector<JSlider>();
	private Hashtable<Integer, JLabel> sliderTable;
	private JButton fjT1, fjT2, fjT3, fjT4; //set bet buttons
	private JLabel fjName1, fjName2, fjName3, fjName4; //Team names
	private JLabel fjBet1, fjBet2, fjBet3, fjBet4; //team bet amounts. 
	private JSlider team1Slider, team2Slider, team3Slider,team4Slider;
	private JTextArea text;
	private JTextField fj1A, fj2A, fj3A, fj4A; //final jeopardy answers
	private JButton fj1Submit, fj2Submit, fj3Submit, fj4Submit; //fj submit buttons
	private JTextArea qLabel;
	private int t1BetAmt, t2BetAmt, t3BetAmt, t4BetAmt;
	private JLabel team, cat, points;
	private LoginGUI loginPage;
	private JeopardyGUI jeopardyStartPage; //page for creating teams 
	private String currWorth, currCat;
	
	private JLabel updateLabel; //gives warning about incorrect format, tells that answer is incorect
	private int originTeam; //team who originally asked a question
	private JPanel qScreen;
	private Vector<JButton> buttnVectR1;
	private Vector<JButton> buttnVectR2;
	private Vector<JButton> buttnVectR3;
	private Vector<JButton> buttnVectR4;
	private Vector<JButton> buttnVectR5;
	private RateScreen rate; 
	
	private Jeopardy_Game game;
	public void tProgressUpdate(){
		
	}
	public void tScoreUpdate(){ //updates score panel
		for( int i = 0; i < teams.size(); i++){
			tScoreLabels.get(i).setText(teams.get(i) + "  " + "$"+ mGameData.getTeamHolder().get(i).getScore());
		}
	}
	
	public void restart(){
		for( int i = 0; i < teams.size(); i++){
			mGameData.getTeamHolder().get(i).resetScore();
			tScoreLabels.get(i).setText(teams.get(i) + "  " + "$"+ mGameData.getTeamHolder().get(i).getScore() );
		}
		mGameData.resetNumQuestionsAsked();
		
		fj1A.setText("");
		fj2A.setText("");
		fj3A.setText("");
		fj4A.setText("");
		
		fj1A.setEnabled(true);
		fj2A.setEnabled(true);
		fj3A.setEnabled(true);
		fj4A.setEnabled(true);
		
		for( int i = 0; i < buttnVectR1.size(); i++){
			buttnVectR1.get(i).setEnabled(true);
			buttnVectR2.get(i).setEnabled(true);
			buttnVectR3.get(i).setEnabled(true);
			buttnVectR4.get(i).setEnabled(true);
			buttnVectR5.get(i).setEnabled(true);
		}
			
		updateLabel.setText("");
		
	}
	public void setTeams(Vector<String> t){
		for(int i =0; i < t.size(); i++){
			game.addTeamToGame(t.get(i));
			
		}
	}
	
	public GameBoard(String f, Vector<String> teamsFromUser, String gameType){
		_gameType = gameType; 
		try {
			mGameData = new Jeopardy_Game(f, teamsFromUser, gameType);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	setTeams(teamsFromUser);
		teams = teamsFromUser; 
		numberOfTeams = teamsFromUser.size();
		for(int i = 0; i < teams.size(); i++){
			mGameData.addTeamToGame(teams.get(i));
		}
		System.out.println("teams" + numberOfTeams);
		initializeComps();
		addEvents();
		createGUI();
		
		gameWindow.setVisible(true);
		
		
	}
	
	public GameBoard(Jeopardy_Game gameData){
		mGameData = gameData;
		_gameType = gameData.getGameType(); 
		
		
		teams = mGameData.getTeamNames(); 
		for(int i = 0; i < teams.size(); i++){
			mGameData.addTeamToGame(teams.get(i));
		}
		numberOfTeams = mGameData.getTeamNames().size();
	
		
		initializeComps();
		addEvents();
		createGUI();
		
		gameWindow.setVisible(true);
		
		
	}
	
	
	private void createMenu(){
		JMenuBar menu = new JMenuBar(); //Jeopardy Menu Window
		JMenu m = new JMenu("Menu");
		menu.add(m);
		JMenuItem mExit =  new JMenuItem("Exit Game");
		JMenuItem mRestart = new JMenuItem("Restart this Game");
		JMenuItem mChooseNew = new JMenuItem("Choose New Game File");
		JMenuItem mLogout = new JMenuItem("Logout");
		m.add(mRestart);
		m.add(mChooseNew);
		m.add(mLogout);
		m.add(mExit);
		gameWindow.setJMenuBar(menu);
		
		mLogout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				try {
					loginPage = new LoginGUI();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				gameWindow.dispose(); 
				loginPage.setVisible(true);
			}
			
		});
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
				mGameData.replay();
				text.setText("");
				restart(); //sets scores to 0
				
				Random random = new Random();
				
				if( numberOfTeams ==1){
					current_team = 0;
				}
				else{
					current_team = random.nextInt(numberOfTeams) ; // team that will start first
				}
				
				text.setText("Welcome to Jeopardy! " + mGameData.getTeamHolder().get(current_team).getName()+ " will be going first!" + "\n");
				cardLayout.show(questionBoxes, "1");
			}
			
		});
		
		mChooseNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//newGame();
				gameWindow.dispose();
				
				try {
					jeopardyStartPage = new JeopardyGUI();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				jeopardyStartPage.setVisible(true);
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
		
		
		
		
		//SET BET BUTTONS
		fjT1 = new JButton("Set Bet");
		fjT1.setVisible(false);
		fjT1.setEnabled(false);
		
		fjT2 = new JButton("Set Bet");
		fjT2.setVisible(false);
		fjT2.setEnabled(false);
		
		fjT3 = new JButton("Set Bet");
		fjT3.setVisible(false);
		fjT3.setEnabled(false);
		
		fjT4 = new JButton("Set Bet");
		fjT4.setVisible(false);
		fjT4.setEnabled(false);
		
		//TEAM NAME LABELS
		fjName1 = new JLabel();
		fjName2 = new JLabel();
		fjName3 = new JLabel();
		fjName4 = new JLabel();
		
		//TEAM BET LABELS
		//this should be updated when the slider moves
		fjBet1 = new JLabel("$0");
		fjBet1.setVisible(false);
		fjBet2 = new JLabel("$0");
		fjBet2.setVisible(false);
		fjBet3 = new JLabel("$0");
		fjBet3.setVisible(false);
		fjBet4 = new JLabel("$0");
		fjBet4.setVisible(false);

		//JSlider with team amounts
		team1Slider = new JSlider();
		team1Slider.setVisible(false);
		
		team2Slider = new JSlider();
		team2Slider.setVisible(false);
		
		team3Slider = new JSlider();
		team3Slider.setVisible(false);
		
		team4Slider = new JSlider();
		team4Slider.setVisible(false);
		
		sliderHolder.add(team1Slider);
		sliderHolder.add(team2Slider);
		sliderHolder.add(team3Slider);
		sliderHolder.add(team4Slider);
		
		fj1A = new JTextField();
		fj2A = new JTextField();
		fj3A = new JTextField();
		fj4A = new JTextField();
		
		fj1A.setVisible(false);
		fj2A.setVisible(false);
		fj3A.setVisible(false);
		fj4A.setVisible(false);
		
		fj1A.setEnabled(true);
		fj2A.setEnabled(true);
		fj3A.setEnabled(true);
		fj4A.setEnabled(true);
		
		fj1Submit = new JButton("Submit Team1");
		fj2Submit = new JButton("Submit Team2");
		fj3Submit = new JButton("Submit Team3");
		fj4Submit = new JButton("Submit Team4");
	
		fj1Submit.setVisible(false);
		fj1Submit.setEnabled(false);
		
		fj2Submit.setVisible(false);
		fj2Submit.setEnabled(false);
		
		fj3Submit.setVisible(false);
		fj3Submit.setEnabled(false);
		
		fj4Submit.setVisible(false);
		fj4Submit.setEnabled(false);
		
		updateLabel = new JLabel();
		updateLabel.setFont(updateLabel.getFont().deriveFont(25.0f));
		
		pass = new JButton("Pass");
		pass.setEnabled(false);
	}
	
	
	public void createGUI(){ //
		//pane.setLayout( new BorderLayout() );
		
		
		createMenu();
		gameWindow.setSize(1500, 1000);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2,1));
		h = new JPanel( new GridLayout(4,1));
		/*h.removeAll();
	    h.revalidate();
		h.repaint();*/
	
		//CURRENT TEAM SCORES
		for(int i = 0; i < numberOfTeams; i++){ // adds team names to pane
			tScoreLabels.get(i).setText(teams.get(i) + "  " + "$"+ mGameData.getTeamHolder().get(i).getScore());
			tScoreLabels.get(i).setFont(tScoreLabels.get(i).getFont().deriveFont(32.0f));
			
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
		text.setText("Welcome to Jeopardy! " + mGameData.getTeamHolder().get(current_team).getName()+ " will be going first!" + "\n");
	
		//cont.add(progress);
		rightPanel.add(cont);
		
		rightPanel.setPreferredSize(new Dimension(400,400));
		
		
		//CREATING QUESTION SELECTION BOXES
		questionBoxes = new JPanel();
		questionBoxes.setLayout(cardLayout);
		//questionBoxes.setLayout(new BorderLayout());
				
		
		questionBoxes.setBorder(BorderFactory.createMatteBorder(10,10,40,10, Color.gray));
		
		
		
		 qScreen = new JPanel( new BorderLayout()); //CARD 1: QUESTION SCREEN
		 gameBoardButtons();
		//qScreen.add(questB, BorderLayout.CENTER);
		//questionBoxes.add(questB, BorderLayout.CENTER);
		JPanel headers = new JPanel(new GridLayout(1,5));
		ImageIcon catIcon = new ImageIcon(mGameData.getImages().get(0));
		ImageIcon icon = new ImageIcon("src/"+ catIcon); //categories
	
		JLabel h = new JLabel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		
		h.setOpaque(false);
		h.setForeground(Color.white);
		h.setText(mGameData.getCatStringName().get(0));
		h.setPreferredSize(new Dimension(200,75));
		h.setHorizontalAlignment(JLabel.CENTER);
		JPanel inner = new JPanel();
		inner.add(h);
		
		inner.setPreferredSize(new Dimension(inner.getWidth(), 50));
		headers.add(inner);
		
		JLabel j = new JLabel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		j.setOpaque(false);
		j.setForeground(Color.white);
		j.setText(mGameData.getCatStringName().get(0));
		j.setPreferredSize(new Dimension(200,75));
		j.setHorizontalAlignment(JLabel.CENTER);
		JPanel inner2 = new JPanel();
		j.setText(mGameData.getCatStringName().get(1) );
		inner2.add(j);
		headers.add(inner2);
		
		JLabel i = new JLabel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		i.setOpaque(false);
		i.setForeground(Color.white);
		i.setText(mGameData.getCatStringName().get(0));
		i.setPreferredSize(new Dimension(200,75));
		i.setHorizontalAlignment(JLabel.CENTER);
		JPanel inner3 = new JPanel();
		i.setText( mGameData.getCatStringName().get(2) );
		inner3.add(i);
		headers.add(inner3);
		
		
		JLabel k = new JLabel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		k.setOpaque(false);
		k.setForeground(Color.white);
		k.setText(mGameData.getCatStringName().get(0));
		k.setPreferredSize(new Dimension(200,75));
		k.setHorizontalAlignment(JLabel.CENTER);
		JPanel inner4 = new JPanel();
		k.setText( mGameData.getCatStringName().get(3) );
		inner4.add(k);
		headers.add(inner4);
		
		JLabel l = new JLabel(){
			public void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		l.setOpaque(false);
		l.setForeground(Color.white);
		l.setText(mGameData.getCatStringName().get(0));
		l.setPreferredSize(new Dimension(200,75));
		l.setHorizontalAlignment(JLabel.CENTER);
		JPanel inner5 = new JPanel();
		l.setText(mGameData.getCatStringName().get(4) );
		inner5.add(l);
		headers.add(inner5);
		
		qScreen.add(headers, BorderLayout.NORTH);
		//qScreen.add(questB.getTableHeader(), BorderLayout.NORTH);
		//questionBoxes.setBackground(Color.green);
		
		//ANSWER SCREEN
		JPanel aScreen = new JPanel(new BorderLayout());//screen for user to answer question
		JPanel submitPanel = new JPanel(new GridLayout(2,1));
		submitPanel.setBackground(Color.gray);
		JPanel questionDisplay = new JPanel(new BorderLayout());
		JPanel info = new JPanel(new GridLayout(1,3)); //panel with team category and question score
		
		info.setBackground(Color.blue);
		team = new JLabel();
		team.setFont(team.getFont().deriveFont(21.0f));
		team.setForeground(Color.white);
		cat = new JLabel();
		cat.setFont(team.getFont().deriveFont(21.0f));
		cat.setForeground(Color.white);
		points = new JLabel();
		points.setFont(team.getFont().deriveFont(21.0f));
		points.setForeground(Color.white);
		
		info.add(team);
		info.add(cat);
		info.add(points);
		//info.setSize(new Dimension(300, 24));
		
		
		ans = new JTextField();
		ans.setPreferredSize( new Dimension( 200, 24 ) );
		submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(200, 75));
		ans.setPreferredSize(new Dimension(400,75));
		JPanel hi = new JPanel(); //to hold textField and submit button
		hi.add(ans);
		hi.add(submit);
		submitPanel.add(hi);
		hi = new JPanel();
		hi.add(pass);
		submitPanel.add(hi);
		
		question = new JTextArea();
		JPanel temp = new JPanel(); //JPANEL THAT ALLOWS TEXTAREA SIZE TO BE ADJUSTED
		temp.setBackground(new Color(102,178,255));
		question.setEditable(false);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setPreferredSize(new Dimension(600, 275));
		temp.add(question);
		//question.setFont(upFirst.getFont().deriveFont(28.0f));
		updateLabel.setText("");
		questionDisplay.add(updateLabel, BorderLayout.NORTH);
		questionDisplay.add(temp, BorderLayout.CENTER);
		questionDisplay.setSize( new Dimension(100,100));
		questionDisplay.setBackground(new Color(102,178,255));
		questionDisplay.setPreferredSize(new Dimension(500,500)); 
		
		aScreen.add(info, BorderLayout.NORTH);
		aScreen.add(questionDisplay, BorderLayout.CENTER);
		aScreen.add(submitPanel, BorderLayout.PAGE_END);
		
		aScreen.setBorder(BorderFactory.createLineBorder(Color.black));

		
		//Final Jeopardy SCreen
		JPanel fjScreen = new JPanel(new BorderLayout());
		JLabel fjLabel = new JLabel("Final Jeopardy!");
		fjLabel.setFont(fjLabel.getFont().deriveFont(45.0f));
		fjScreen.add(fjLabel, BorderLayout.PAGE_START);
	
		JPanel fjMidPanel = new JPanel(new GridLayout(2,1));
		JPanel fjSliderPanel = new JPanel(new GridLayout(4,4));
		
		fjSliderPanel.add(fjName1);
		fjSliderPanel.add(team1Slider);
		fjSliderPanel.add(fjBet1);
		fjSliderPanel.add(fjT1); 
		
		fjSliderPanel.add(fjName2);
		fjSliderPanel.add(team2Slider);
		fjSliderPanel.add(fjBet2);
		fjSliderPanel.add(fjT2);
		
		fjSliderPanel.add(fjName3);
		fjSliderPanel.add(team3Slider);
		fjSliderPanel.add(fjBet3);
		fjSliderPanel.add(fjT3);
		
		fjSliderPanel.add(fjName4);
		fjSliderPanel.add(team4Slider);
		fjSliderPanel.add(fjBet4);
		fjSliderPanel.add(fjT4);
		
		
		fjMidPanel.add(fjSliderPanel);
		
		
		
		
		JPanel fjBottomHalf = new JPanel(new GridLayout(2,1));//half of center screen that has question and user responses
		
		qLabel = new JTextArea();
		qLabel.setText("And the question is...");
		qLabel.setFont(qLabel.getFont().deriveFont(28.0f));
		qLabel.setWrapStyleWord(true);
		qLabel.setLineWrap(true);
		qLabel.setEditable(false);
		
		fjBottomHalf.add(qLabel);
		
		
		JPanel fjResponses = new JPanel(new GridLayout(2,2));
		
		JPanel insideLayout1 = new JPanel(new GridLayout(1,2));//to arrange team response/button w/in each cell
		JPanel insideLayout2 = new JPanel(new GridLayout(1,2));
		JPanel insideLayout3 = new JPanel(new GridLayout(1,2));
		JPanel insideLayout4 = new JPanel(new GridLayout(1,2));
		
		
		insideLayout1.add(fj1A);
		insideLayout1.add(fj1Submit);
		fjResponses.add(insideLayout1);
		
		
		
		insideLayout2.add(fj2A);
		insideLayout2.add(fj2Submit);
		fjResponses.add(insideLayout2);
		
		 
		insideLayout3.add(fj3A);
		insideLayout3.add(fj3Submit);
		fjResponses.add(insideLayout3);
		
		insideLayout4.add(fj4A);
		insideLayout4.add(fj4Submit);
		fjResponses.add(insideLayout4);
		
		fjBottomHalf.add(fjResponses);
		
		fjMidPanel.add(fjBottomHalf);
		
		fjScreen.add(fjMidPanel, BorderLayout.CENTER);
		
		questionBoxes.add(qScreen, "1");
		questionBoxes.add(aScreen, "2");
		questionBoxes.add(fjScreen,"3");
		cardLayout.show(questionBoxes, "1");
		
		
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String a = ans.getText();
				String currScore = currWorth;//(String) data[questB.getSelectedRow()][questB.getSelectedColumn()];
				String getCat = currCat;
				boolean rightAnswer = mGameData.isCorrect(a, mGameData.getCategory(getCat).getQuestion(currScore).getAnswer());
				
				//addToPane = new JLabel(game.getTeamHolder().get(current_team).getName()+" got the answer correct. $" + currScore + " will be added to your score.");
				
				if(rightAnswer){
					//add points to team
					//redisplay 
					originTeam = current_team; 
					mGameData.getTeamHolder().get(current_team).changeScore(Integer.parseInt(currScore));
					text.append(mGameData.getTeamHolder().get(current_team).getName()+" got the answer correct. $" + currScore + " will be added to your score. Please choose again." + "\n");
					
					updateLabel.setText("");
					cardLayout.show(questionBoxes, "1");
				}
				else{
					mGameData.getTeamHolder().get(current_team).changeScore((-1)*Integer.parseInt(currScore));
					text.append(mGameData.getTeamHolder().get(current_team).getName()+" got the answer incorrect. $" + currScore + " will be deducted from to your score." + "\n");
					//RULE CHANGE: SHOULD GO TO THE NEXT PLAYER AND LET THEM TRY
					nextTeam();
					if( current_team != originTeam){
						
						updateLabel.setText("It's " + teams.get(current_team)+ "'s turn to try to answer the question.");
						
						ans.setText("");
						pass.setEnabled(true);
					}
					else{
						
						text.append(teams.get(current_team)+ "'s turn." + "\n");
						cardLayout.show(questionBoxes, "1");
						pass.setEnabled(false);
						updateLabel.setText("");
					}
					//cardLayout.show(questionBoxes, "1");
				}
				
				tScoreUpdate();
				
				
				
				
				if( allTeamsPassed() && ( ( _gameType.equals("Quick Play" ) && mGameData.quickPlayFinished() ) || ( _gameType.equals("Standard") &&mGameData.allQuestionsFinished() ) ) ){ //if nec. questions have been asked
					text.append("All questions have been finished. Time for final Jeopardy!" + "\n");
					
					fjCompAddition();
					cardLayout.show(questionBoxes, "3");
								
				}
				
				else{ //keep playing
					text.append("It is " + teams.get(current_team) + "'s turn." );
					
				}
				 
				
			}
			
		});
		
		DocumentListener listener = new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				change();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				change();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				change();
			}
			
			private void change(){
				if(!ans.getText().isEmpty()){
					pass.setEnabled(false);
				}
				
			}
			
			
		};
		
		ans.getDocument().addDocumentListener(listener);
		
	/*	questB.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//should display question
				//should switch to other cardPanel
				
				//System.out.println("row: " +questB.getSelectedRow());
				//System.out.println("col: " +questB.getSelectedColumn());
				ans.setText("");// clears textField
				currWorth = (String) data[questB.getSelectedRow()][questB.getSelectedColumn()];
				currCat = game.getCatStringName().get(questB.getSelectedColumn() );
				
				
				if(game.getCategory(currCat).getQuestion(currWorth).questionUsed()){ //
					//DO NOTHING
				}
				//System.out.println(currScore);
				//System.out.println(getCat);
				else{
					text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
					originTeam = current_team;
					updateQuestionScreen();
					cardLayout.show(questionBoxes, "2");
					question.setText(game.getCategory(currCat).getQuestion(currWorth).getQuestionName());
				
					game.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
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
			
		});*/
		
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
		
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		
	}
	
	public void fjCompAddition(){
		for(int i = 0; i<numberOfTeams; i++){ //sets approp. sliders to visible
			int max = Math.abs(mGameData.getTeamHolder().get(i).getScore());
			
			sliderHolder.get(i).setMaximum(max);
			
			sliderHolder.get(i).setMinimum(0);
		
			fjSliderSetup(sliderHolder.get(i), max);
			
			sliderHolder.get(i).setVisible(true);
		}
		
		if(numberOfTeams == 1){
			fjT1.setVisible(true); //
			fjBet1.setVisible(true);
			fjName1.setText(teams.get(0));
			fjT1.setEnabled(true);
			
		
			fj1A.setVisible(true);
			fj1Submit.setVisible(true);
			fj1Submit.setEnabled(true);
			
			team1Slider.setEnabled(true); // makes sure that when the game is restarted that its re-enabled
		}
		
		if(numberOfTeams == 2){
			fjT1.setVisible(true);
			fjName1.setText(teams.get(0));
			fjBet1.setVisible(true);
			
			fjT2.setVisible(true);
			fjName2.setText(teams.get(1));
			fjBet2.setVisible(true);
			
			fjT1.setEnabled(true);
			fjT2.setEnabled(true);
			
			fj1A.setVisible(true);
			fj1Submit.setVisible(true);
			fj1Submit.setEnabled(true);
			
			fj2A.setVisible(true);
			fj2Submit.setVisible(true);
			fj2Submit.setEnabled(true);
			
			team1Slider.setEnabled(true); 
			team2Slider.setEnabled(true); 
		}
		

		if(numberOfTeams == 3){
			fjT1.setVisible(true);
			fjName1.setText(teams.get(0));
			fjBet1.setVisible(true);
			
			fjT2.setVisible(true);
			fjName2.setText(teams.get(1));
			fjBet2.setVisible(true);
			
			fjT3.setVisible(true);
			fjName3.setText(teams.get(2));
			fjBet3.setVisible(true);
			
			fjT1.setEnabled(true);
			fjT2.setEnabled(true);
			fjT3.setEnabled(true);
			
			fj1A.setVisible(true);
			fj1Submit.setVisible(true);
			fj1Submit.setEnabled(true);
			
			fj2A.setVisible(true);
			fj2Submit.setVisible(true);
			fj2Submit.setEnabled(true);
			
			fj3A.setVisible(true);
			fj3Submit.setVisible(true);
			fj3Submit.setEnabled(true);
			
			team1Slider.setEnabled(true); 
			team2Slider.setEnabled(true); 
			team3Slider.setEnabled(true); 
		}
		
		if(numberOfTeams == 4){
			fjT1.setVisible(true);
			fjName1.setText(teams.get(0));
			fjBet1.setVisible(true);
			
			fjT2.setVisible(true);
			fjName2.setText(teams.get(1));
			fjBet2.setVisible(true);
			
			fjT3.setVisible(true);
			fjName3.setText(teams.get(2));
			fjBet3.setVisible(true);
			
			fjT4.setVisible(true);
			fjName4.setText(teams.get(3));
			fjBet4.setVisible(true);
			
			
			fjT1.setEnabled(true);
			fjT2.setEnabled(true);
			fjT3.setEnabled(true);
			fjT4.setEnabled(true);
			
			fj1A.setVisible(true);
			fj1Submit.setVisible(true);
			fj1Submit.setEnabled(true);
			
			fj2A.setVisible(true);
			fj2Submit.setVisible(true);
			fj2Submit.setEnabled(true);
			
			fj3A.setVisible(true);
			fj3Submit.setVisible(true);
			fj3Submit.setEnabled(true);
			
			fj4A.setVisible(true);
			fj4Submit.setVisible(true);
			fj4Submit.setEnabled(true);
			
			team1Slider.setEnabled(true); 
			team2Slider.setEnabled(true); 
			team3Slider.setEnabled(true); 
			team4Slider.setEnabled(true); 
		}
		
		
		
	}
	
	public void addEvents(){
		team1Slider.addChangeListener( new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				String newBet = Integer.toString(team1Slider.getValue());
				fjBet1.setText("$"+newBet);
			}
			
		});
		team2Slider.addChangeListener( new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				String newBet = Integer.toString(team2Slider.getValue());
				fjBet2.setText("$"+newBet);
			}
			
		});
		
		team3Slider.addChangeListener( new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				String newBet = Integer.toString(team3Slider.getValue());
				fjBet3.setText("$"+newBet);
			}
			
		});
		
		team4Slider.addChangeListener( new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				String newBet = Integer.toString(team4Slider.getValue());
				fjBet4.setText("$"+newBet);
			}
			
		});
		
		
		
		fjT1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				team1Slider.setEnabled(false);
				fjT1.setEnabled(false);
				text.append( mGameData.getTeamHolder().get(0).getName() + " bet $" + team1Slider.getValue() +"." + "\n");
				t1BetAmt = team1Slider.getValue();
						
				if( !fjT1.isEnabled() && !fjT2.isEnabled() && !fjT3.isEnabled() && !fjT4.isEnabled()){
					qLabel.setText(mGameData.getFJQuestion());
				}
			}
			
		});
		fjT2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				team2Slider.setEnabled(false);
				fjT2.setEnabled(false);
				text.append(mGameData.getTeamHolder().get(1).getName() + " bet $" + team2Slider.getValue() +".");
				t2BetAmt = team2Slider.getValue();
				if( !fjT1.isEnabled() && !fjT2.isEnabled() && !fjT3.isEnabled() && !fjT4.isEnabled()){
					qLabel.setText(mGameData.getFJQuestion());
				}
			}
			
		});
		fjT3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				team3Slider.setEnabled(false);
				fjT3.setEnabled(false);
				text.append(mGameData.getTeamHolder().get(2).getName() + " bet $" + team3Slider.getValue() +".");
				t3BetAmt = team3Slider.getValue();
				if( !fjT1.isEnabled() && !fjT2.isEnabled() && !fjT3.isEnabled() && !fjT4.isEnabled()){
					qLabel.setText(mGameData.getFJQuestion());
				}
			}
			
		});
		fjT4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				team4Slider.setEnabled(false);
				fjT4.setEnabled(false);
				text.append(mGameData.getTeamHolder().get(3).getName() + " bet $" + team4Slider.getValue() +".");
				t4BetAmt = team4Slider.getValue();
				if( !fjT1.isEnabled() && !fjT2.isEnabled() && !fjT3.isEnabled() && !fjT4.isEnabled()){
					qLabel.setText(mGameData.getFJQuestion());
				}
			}
			
		});
		
		fj1Submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mGameData.isFJCorrect(fj1A.getText())){
					mGameData.getTeamHolder().get(0).changeScore(t1BetAmt);
					

				}
				else{
					mGameData.getTeamHolder().get(0).changeScore((-1)*t1BetAmt);
				}
				
				tScoreUpdate();
				fj1Submit.setEnabled(false);
				fj1A.setEnabled(false);
				
				allFJQuestions();
			}
			
		});
		
		fj2Submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mGameData.isFJCorrect(fj2A.getText())){
					mGameData.getTeamHolder().get(1).changeScore(t2BetAmt);
					

				}
				else{
					mGameData.getTeamHolder().get(1).changeScore((-1)*t2BetAmt);
				}
				
				tScoreUpdate();
				fj2A.setEnabled(false);
				fj2Submit.setEnabled(false);
				
				allFJQuestions();
			}
			
		});
		
		fj3Submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mGameData.isFJCorrect(fj3A.getText())){
					mGameData.getTeamHolder().get(2).changeScore(t3BetAmt);
					

				}
				else{
					mGameData.getTeamHolder().get(2).changeScore((-1)*t3BetAmt);
				}
				
				tScoreUpdate();
				fj3A.setEnabled(false);
				fj3Submit.setEnabled(false);
				
				allFJQuestions();
			}
			
		});
		
		fj4Submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mGameData.isFJCorrect(fj4A.getText())){
					mGameData.getTeamHolder().get(3).changeScore(t4BetAmt);
					

				}
				else{
					mGameData.getTeamHolder().get(3).changeScore((-1)*t4BetAmt);
				}
				
				tScoreUpdate();
				fj4A.setEnabled(false);
				fj4Submit.setEnabled(false);
				
				allFJQuestions();
			}
			
		});
		
		pass.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nextTeam();
				
				if(current_team != originTeam){
					
					updateLabel.setText("It's " + teams.get(current_team)+ "'s turn to try to answer the question.");
					text.append("It's " + teams.get(current_team)+ "'s turn to try to answer the question.");
					
					//append game progress panel
				}
				else{
					cardLayout.show(questionBoxes, "1");
					current_team = originTeam; 
					text.append("It's " + teams.get(current_team)+ "'s turn.");
					updateLabel.setText("");
				}
			}
			
		});
		
	}
	
	public void allFJQuestions(){
		if( !fj1Submit.isEnabled() && !fj2Submit.isEnabled() && !fj3Submit.isEnabled() && !fj4Submit.isEnabled()){
			
			String winner = mGameData.winner();
			
			rate = new RateScreen(winner, mGameData);
			rate.setVisible(true);
		//	JOptionPane.showMessageDialog(gameWindow, winner+ " is the winner!");
			
			
			
		}
	}
	
	public void newGame(){
		//change gameboard to original settings
		gameWindow.setVisible(false);
		for(int i = 0; i < numberOfTeams; i++){ // adds team names to pane
			tScoreLabels.get(i).setText("");
			
		}
		updateLabel.setText("");
		pass.setEnabled(false);
		tScoreLabels.clear();
		teams.clear();
		sliderHolder.clear();
		h.removeAll();
		mGameData.clear();
		cardLayout.show(questionBoxes, "1");
		
		
		//createGUI();
	}
	
	public void updateQuestionScreen(){//CHANGES CURRENT NAME/CATEGORY/WORTH OF QUESTION
		team.setText(teams.get(current_team));
		cat.setText(currCat);
		points.setText("$"+currWorth);
	}
	
	public void nextTeam(){//sets current_team to the next team
		current_team++;
		if( current_team == teams.size()){
			current_team = 0;
		}
	}
	
	public boolean allTeamsPassed(){
		return (originTeam == current_team);
	}
	
	
	
	public void gameBoardButtons(){
		buttnVectR1 = new Vector<JButton>();
		buttnVectR2 = new Vector<JButton>();
		buttnVectR3 = new Vector<JButton>();
		buttnVectR4 = new Vector<JButton>();
		buttnVectR5 = new Vector<JButton>();
		
		JButton r1c1,r1c2,r1c3,r1c4,r1c5;
		JButton r2c1,r2c2,r2c3,r2c4,r2c5;
		JButton r3c1,r3c2,r3c3,r3c4,r3c5;
		JButton r4c1,r4c2,r4c3,r4c4,r4c5;
		JButton r5c1,r5c2,r5c3,r5c4,r5c5;
		
		JPanel buttonHolder = new JPanel(new GridLayout(5,5));
		
		r1c1 = new JButton(mGameData.getPointVals().get(0));
		r1c2 = new JButton(mGameData.getPointVals().get(0));
		r1c3 = new JButton(mGameData.getPointVals().get(0));
		r1c4 = new JButton(mGameData.getPointVals().get(0));
		r1c5 = new JButton(mGameData.getPointVals().get(0));
		buttonHolder.add(r1c1);// adds to panel
		buttonHolder.add(r1c2);
		buttonHolder.add(r1c3);
		buttonHolder.add(r1c4);
		buttonHolder.add(r1c5);
		buttnVectR1.add(r1c1); // adds to vector
		buttnVectR1.add(r1c2);
		buttnVectR1.add(r1c3);
		buttnVectR1.add(r1c4);
		buttnVectR1.add(r1c5);
		
		
		r2c1 = new JButton(mGameData.getPointVals().get(1));
		r2c2 = new JButton(mGameData.getPointVals().get(1));
		r2c3 = new JButton(mGameData.getPointVals().get(1));
		r2c4 = new JButton(mGameData.getPointVals().get(1));
		r2c5 = new JButton(mGameData.getPointVals().get(1));
		buttonHolder.add(r2c1);
		buttonHolder.add(r2c2);
		buttonHolder.add(r2c3);
		buttonHolder.add(r2c4);
		buttonHolder.add(r2c5);
		buttnVectR2.add(r2c1); // adds to vector
		buttnVectR2.add(r2c2);
		buttnVectR2.add(r2c3);
		buttnVectR2.add(r2c4);
		buttnVectR2.add(r2c5);
		
		r3c1 = new JButton(mGameData.getPointVals().get(2));
		r3c2 = new JButton(mGameData.getPointVals().get(2));
		r3c3 = new JButton(mGameData.getPointVals().get(2));
		r3c4 = new JButton(mGameData.getPointVals().get(2));
		r3c5 = new JButton(mGameData.getPointVals().get(2));
		buttonHolder.add(r3c1);
		buttonHolder.add(r3c2);
		buttonHolder.add(r3c3);
		buttonHolder.add(r3c4);
		buttonHolder.add(r3c5);
		buttnVectR3.add(r3c1); // adds to vector
		buttnVectR3.add(r3c2);
		buttnVectR3.add(r3c3);
		buttnVectR3.add(r3c4);
		buttnVectR3.add(r3c5);
		
		r4c1 = new JButton(mGameData.getPointVals().get(3));
		r4c2 = new JButton(mGameData.getPointVals().get(3));
		r4c3 = new JButton(mGameData.getPointVals().get(3));
		r4c4 = new JButton(mGameData.getPointVals().get(3));
		r4c5 = new JButton(mGameData.getPointVals().get(3));
		buttonHolder.add(r4c1);
		buttonHolder.add(r4c2);
		buttonHolder.add(r4c3);
		buttonHolder.add(r4c4);
		buttonHolder.add(r4c5);
		buttnVectR4.add(r4c1); // adds to vector
		buttnVectR4.add(r4c2);
		buttnVectR4.add(r4c3);
		buttnVectR4.add(r4c4);
		buttnVectR4.add(r4c5);
		
		r5c1 = new JButton(mGameData.getPointVals().get(4));
		r5c2 = new JButton(mGameData.getPointVals().get(4));
		r5c3 = new JButton(mGameData.getPointVals().get(4));
		r5c4 = new JButton(mGameData.getPointVals().get(4));
		r5c5 = new JButton(mGameData.getPointVals().get(4));
		buttonHolder.add(r5c1);
		buttonHolder.add(r5c2);
		buttonHolder.add(r5c3);
		buttonHolder.add(r5c4);
		buttonHolder.add(r5c5);
		buttnVectR5.add(r5c1); // adds to vector
		buttnVectR5.add(r5c2);
		buttnVectR5.add(r5c3);
		buttnVectR5.add(r5c4);
		buttnVectR5.add(r5c5);
		
		ImageIcon enabledIcon = new ImageIcon("src/" + new ImageIcon(mGameData.getImages().get(1)) );
		r5c5.setIcon(enabledIcon);
		qScreen.add(buttonHolder, BorderLayout.CENTER);
		
		ImageIcon icon3 = new ImageIcon("src/"+ new ImageIcon(mGameData.getImages().get(2)) );//disabled image
		//ADD ACTIONLISTENERS FOR BUTTONS
		
			
			r1c1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(0);
					currCat = mGameData.getCatStringName().get(0);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r1c1.setDisabledIcon(icon3);
						r1c1.setEnabled(false);

					
					
				}
				
			});
			
			r1c2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(0);
					currCat = mGameData.getCatStringName().get(1);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
				
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r1c2.setDisabledIcon(icon3);
						r1c2.setEnabled(false);

					
					
				}
				
			});
			r1c3.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(0);
					currCat = mGameData.getCatStringName().get(2);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r1c3.setDisabledIcon(icon3);
						r1c3.setEnabled(false);

					
					
				}
				
			});
			r1c4.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(0);
					currCat = mGameData.getCatStringName().get(3);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r1c4.setDisabledIcon(icon3);
						r1c4.setEnabled(false);

					
					
				}
				
			});
			r1c5.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(0);
					currCat = mGameData.getCatStringName().get(4);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r1c5.setDisabledIcon(icon3);
						r1c5.setEnabled(false);
					
					
				}
				
			});
			r2c1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(1);
					currCat = mGameData.getCatStringName().get(0);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r2c1.setDisabledIcon(icon3);
						r2c1.setEnabled(false);
					
					
				}
				
			});
			r2c2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(1);
					currCat = mGameData.getCatStringName().get(1);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r2c2.setDisabledIcon(icon3);
						r2c2.setEnabled(false);
					
					
				}
				
			});
			r2c3.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(1);
					currCat = mGameData.getCatStringName().get(2);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r2c3.setDisabledIcon(icon3);
						r2c3.setEnabled(false);
					
					
				}
				
			});
			r2c4.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(1);
					currCat = mGameData.getCatStringName().get(3);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r2c4.setDisabledIcon(icon3);
						r2c4.setEnabled(false);
					
					
				}
				
			});
			r2c5.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(1);
					currCat = mGameData.getCatStringName().get(4);
					
					
				
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r2c5.setDisabledIcon(icon3);
						r2c5.setEnabled(false);

					
					
				}
				
			});
			r3c1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(2);
					currCat = mGameData.getCatStringName().get(0);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r3c1.setDisabledIcon(icon3);
						r3c1.setEnabled(false);

					
					
				}
				
			});
			r3c2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(2);
					currCat = mGameData.getCatStringName().get(1);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r3c2.setDisabledIcon(icon3);
						r3c2.setEnabled(false);

					
					
				}
				
			});

			r3c3.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(2);
					currCat = mGameData.getCatStringName().get(2);
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r3c3.setDisabledIcon(icon3);
						r3c3.setEnabled(false);

					
					
				}
				
			});
			
			r3c4.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(2);
					currCat = mGameData.getCatStringName().get(3);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r3c4.setDisabledIcon(icon3);
						r3c4.setEnabled(false);

				
					
				}
				
			});
			r3c5.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(2);
					currCat = mGameData.getCatStringName().get(4);
					
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r3c5.setDisabledIcon(icon3);
						r3c5.setEnabled(false);
					
					
				}
				
			});
			r4c1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(3);
					currCat = mGameData.getCatStringName().get(0);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r4c1.setDisabledIcon(icon3);
						r4c1.setEnabled(false);
					
					
				}
				
			});
			r4c2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(3);
					currCat = mGameData.getCatStringName().get(1);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r4c2.setDisabledIcon(icon3);
						r4c2.setEnabled(false);
					
					
				}
				
			});
			r4c3.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(3);
					currCat = mGameData.getCatStringName().get(2);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r4c3.setDisabledIcon(icon3);
						r4c3.setEnabled(false);
					
					
				}
				
			});
			r4c4.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(3);
					currCat = mGameData.getCatStringName().get(3);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r4c4.setDisabledIcon(icon3);
						r4c4.setEnabled(false);
					
					
				}
				
			});
			r4c5.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(3);
					currCat = mGameData.getCatStringName().get(4);
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r4c5.setDisabledIcon(icon3);
						r4c5.setEnabled(false);
					
					
				}
				
			});
			r5c1.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(4);
					currCat = mGameData.getCatStringName().get(0);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r5c1.setDisabledIcon(icon3);
						r5c1.setEnabled(false);
					
					
				}
				
			});
			r5c2.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(4);
					currCat = mGameData.getCatStringName().get(1);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r5c2.setDisabledIcon(icon3);
						r5c2.setEnabled(false);
				
					
				}
				
			});
			r5c3.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(4);
					currCat = mGameData.getCatStringName().get(2);
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
				
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r5c3.setDisabledIcon(icon3);
						r5c3.setEnabled(false);
					
					
				}
				
			});
			r5c4.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(4);
					currCat = mGameData.getCatStringName().get(3);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r5c4.setDisabledIcon(icon3);
						r5c4.setEnabled(false);
					
					
				}
				
			});
			r5c5.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ans.setText("");// clears textField
					currWorth = mGameData.getPointVals().get(4);
					currCat = mGameData.getCatStringName().get(4);
					
					
					
					//System.out.println(currScore);
					//System.out.println(getCat);
					
						text.append(teams.get(current_team) + " has selected " + currCat + " for $"+ currWorth + "." + "\n");
						originTeam = current_team;
						updateQuestionScreen();
						cardLayout.show(questionBoxes, "2");
						question.setText(mGameData.getCategory(currCat).getQuestion(currWorth).getQuestionName());
					
						mGameData.getCategory(currCat).getQuestion(currWorth).asked();//says marks a question as asked
						mGameData.incrementQuestionsAsked();
						r5c5.setDisabledIcon(icon3);
						r5c5.setEnabled(false);
						
					
					
				}
				
			});
			
			ImageIcon icon2 = new ImageIcon("src/" + mGameData.getImages().get(1));
			for( int i = 0; i < buttnVectR1.size(); i++){
				buttnVectR1.get(i).setIcon(icon2);
				buttnVectR1.get(i).setHorizontalTextPosition(JButton.CENTER);
				buttnVectR1.get(i).setBorder(new LineBorder(Color.gray));
				
				buttnVectR2.get(i).setIcon(icon2);
				buttnVectR2.get(i).setHorizontalTextPosition(JButton.CENTER);
				buttnVectR2.get(i).setBorder(new LineBorder(Color.gray));
				
				buttnVectR3.get(i).setIcon(icon2);
				buttnVectR3.get(i).setHorizontalTextPosition(JButton.CENTER);
				buttnVectR3.get(i).setBorder(new LineBorder(Color.gray));
				
				buttnVectR4.get(i).setIcon(icon2);
				buttnVectR4.get(i).setHorizontalTextPosition(JButton.CENTER);
				buttnVectR4.get(i).setBorder(new LineBorder(Color.gray));
				
				buttnVectR5.get(i).setIcon(icon2);
				buttnVectR5.get(i).setHorizontalTextPosition(JButton.CENTER);
				buttnVectR5.get(i).setBorder(new LineBorder(Color.gray));
				
			}

	}
	
	
	
}
