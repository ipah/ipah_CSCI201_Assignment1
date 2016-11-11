
package ipah_CSCI201L_Assignment1;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import Client.GameClient;
import Server.GameServer;



	
	
	
	
	public class JeopardyGUI extends JFrame{
		
		
		
		private JLabel teamSelection; //prompts user to select number of teams
		private JTextField team1, team2, team3, team4;
		private Vector<JTextField> textFieldHolder; //holds textFields so that they can be added properly
		private JLabel selectedFile; //file that user selects to play with.
		private JSlider teams; 
		private Hashtable<Integer, JLabel> table;
		private JButton chooseFile, start, clear, exit, logOut; 
		private JPanel teamNames; // JPanel that has certain number of textfields depending on slider input
		private int numberOfTeams = 1;
		private static String fileName; 
		private static Vector<String> teamsFromUser; // vector that holds all the team names entered.
		 //team that is current answering a question
		private JCheckBox checkBox; //QuickPlay
		private JPanel team1Panel, team2Panel, team3Panel, team4Panel;
		private JFrame fileChooserWindow; 
		
		private static String gameType;
		private static GameBoard gameBoard; 
		private LoginGUI loginPage;
		private JLabel ratingLabel;
		
		private Jeopardy_Game gameData; //only used to read in th file and display the average
		
		private JRadioButton notNetworked; 
		private JRadioButton host; 
		private JRadioButton joinGame;
		private CardLayout middleCardLayout; 
		private JPanel centerPanel; //panel for cardLayout
		private JPanel fileContainer;
		private JLabel choose;
		private JPanel sliderContainer;
		
		private JPanel hostPanel; //container for host screen
		private JButton hostFileButton;
		private JLabel hostSelectedFile;
		private JLabel hostRatingLabel;
		private JTextField hostUN; 
		
		private JTextField portText;
		private ServerSocket ss;
		private Lock portLock;
		private Condition portCondition;
		
		private JTextField joinPort;
		private JTextField ip;
		private JTextField joinName; 
		private JSlider hostSlider; 
		private static final long serialVersionUID = 1L;

		public JeopardyGUI() throws FileNotFoundException{
			super("Welcome to Jeopardy!");
			
			initializeComps();
			createGUI();
			addEvents();
			
			
			
			
			
		}
		
		public void resetHomeScreen(){
			for( int i = 0; i < textFieldHolder.size(); i++){
				textFieldHolder.get(i).setText("");
				selectedFile.setText("");
				
			}
			
			start.setEnabled(false);
			checkBox.setSelected(false);
		}
		
		
		
		
		private void addEvents(){
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			clear.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					resetHomeScreen();
				}
				
			});
			
			
			
			checkBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					gameType = "Quick Play"; 
					
				}
				
			});
			
			start.addActionListener(new ActionListener(){ //should open a new window

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					int counter = 0;
					
					for(int i = 0; i < textFieldHolder.size(); i++){//if field isn't empty
						if(!textFieldHolder.get(i).getText().equals("")){
							//game.addTeamToGame(textFieldHolder.get(i).getText()); // adds team to game
							counter++;
						}
						
						//System.out.println("teams: "+ textFieldHolder.get(i).getText());
					}
					
					if(host.isSelected()){
						String portStr = portText.getText();
						System.out.println("port # : " + portStr);
						
						int portInt = Integer.parseInt(portStr);
						
						int sliderValue = hostSlider.getValue();
						try {
							gameData = new Jeopardy_Game(fileName, teamsFromUser, gameType);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						new GameServer(portInt, sliderValue, gameData);
						
						new GameClient(portInt, hostUN.getText());
						
						
					}
					
					else if(joinGame.isSelected()){
						String portStr = joinPort.getText();
						System.out.println("port # : " + portStr);
						String ipStr = ip.getText();
						int portInt = Integer.parseInt(portStr);
						new GameClient(portInt, joinName.getText());
					}
					
					else{ //if "not networked" is selected
						displayGB();
					}
			
					
				}
				
			});
			
			
			chooseFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					fileChooserWindow = new JFrame(); 
					
					
					//fileChooserWindow.setLayout(new GridLayout(4,1));
					
					JFileChooser chooser = new JFileChooser(); 
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
					chooser.setFileFilter(filter);
					//fileChooserWindow.add(chooser);
					
					int result = chooser.showOpenDialog(fileChooserWindow);
					
					
					if(result == JFileChooser.APPROVE_OPTION){
						//System.out.println(chooser.getSelectedFile().getName());
						selectedFile.setText(chooser.getSelectedFile().getName());
						fileName = chooser.getSelectedFile().getAbsolutePath();
						
						try {
							gameData = new Jeopardy_Game(fileName);
							ratingLabel.setText("This game has a rating of: "+gameData.getAvg());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//ratingLabel.setText();

					}
					//System.out.println(chooser.getSelectedFile().getName());
					
				}
				
			});
			
			exit.addActionListener(new ActionListener() { // listener for EXIT button

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
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
					int counter = 0;
					for(int i = 0; i < numberOfTeams; i++){//if field isn't empty
						if(!textFieldHolder.get(i).getText().equals("")){
							counter++;
						}
						
						//System.out.println("asd teams: "+ textFieldHolder.get(i).getText());
					}
					
					if( ( (counter == numberOfTeams) && !selectedFile.getText().isEmpty() )  || (!hostSelectedFile.getText().isEmpty() && !hostUN.getText().isEmpty() ) ){
						

						start.setEnabled(true);
					}
					
					else if(!joinPort.getText().isEmpty() && !ip.getText().isEmpty()){
						start.setEnabled(true);
					}
					
					
				}
				
			};
			
			
			for(int i = 0; i < textFieldHolder.size(); i++){
				textFieldHolder.get(i).getDocument().addDocumentListener(listener); 
			}
			portText.getDocument().addDocumentListener(listener);
			hostUN.getDocument().addDocumentListener(listener);
			joinPort.getDocument().addDocumentListener(listener);
			ip.getDocument().addDocumentListener(listener);
			
			//setNumOfTeams(teams.getValue()) ;
			teams.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent ae){
					numberOfTeams = ((JSlider)ae.getSource()).getValue();
					
					
					int save = ((JSlider)ae.getSource()).getValue();
					//game.setNumOfTeams(save);
					if(save == 1){
						team1Panel.setVisible(true);
						team2Panel.setVisible(false);
						team3Panel.setVisible(false);
						team4Panel.setVisible(false);
					}
					if(save == 2 ){
						team1Panel.setVisible(true);
						team2Panel.setVisible(true);
						team3Panel.setVisible(false);
						team4Panel.setVisible(false);
					}
					if(save == 3){
						team1Panel.setVisible(true);
						team2Panel.setVisible(true);
						team3Panel.setVisible(true);
						team4Panel.setVisible(false);
					}
					if(save == 4){
						team1Panel.setVisible(true);
						team2Panel.setVisible(true);
						team3Panel.setVisible(true);
						team4Panel.setVisible(true);
					}
					/*
					for(int i = 0; i < numberOfTeams; i++){
						System.out.println("HELP");
						
						//teamName = new JTextField();
						//textFieldHolder.add(teamName);
						//inputTeamName = new JTextField();
						enterTeams.add(textFieldHolder.get(i));
						enterTeams.setBackground(Color.gray);
						
						teamNames.add(enterTeams);
						teamNames.getInsets().set(50, 150, 50, 150); 
					}
					
					System.out.println("size: " +textFieldHolder.size());*/
				}
			});
			
			
			logOut.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						loginPage = new LoginGUI();
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					loginPage.setVisible(true);
					
					dispose();
				}
				
			});
			
			
			notNetworked.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					middleCardLayout.show(centerPanel, "1");
				}
				
			});
			
			host.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					middleCardLayout.show(centerPanel, "2");
				}
				
			});
			
			joinGame.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					middleCardLayout.show(centerPanel, "3");
					
					
					
					
					
				}
				
			});
			
		}
		
		public void initializeComps(){
			gameType = "Standard";
			
			teamSelection = new JLabel("Please choose the number of teams that will be playing on the slider below.");
			
			portLock = new ReentrantLock();
			portCondition = portLock.newCondition();
			portText = new JTextField("port");
			hostUN = new JTextField();
			
			
			teams= new JSlider(JSlider.HORIZONTAL,1,4,1);
			//teams.setValue(1);
			teams.setSize(1000, 30);
			teams.setMajorTickSpacing(1);
			teams.setMinorTickSpacing(1);
			teams.setPaintTicks(true);
			teams.setPaintLabels(true);
			//JSlider slider= new JSlider(JSlider.HORIZONTAL,Constant.);
			
			table = new Hashtable<Integer, JLabel>();
			table.put(1, new JLabel("1"));
			table.put(2, new JLabel("2"));
			table.put(3, new JLabel("3"));
			table.put(4, new JLabel("4"));
			teams.setLabelTable(table);
			
			//button
			
			chooseFile = new JButton("Choose File");
			start = new JButton("Start Jeopardy");
			clear = new JButton("Clear Choices");
			exit = new JButton("Exit");
			
			teamNames = new JPanel( new GridLayout(2,2));
			
			selectedFile = new JLabel();
			
			//initialize textfields for team entry
			//textFieldHolder = new Vector<JTextField>();
		
			/*
			enterPrompt.setBackground(Color.gray);
			enterPrompt.setHorizontalAlignment(JLabel.CENTER);
			enterTeams.add(enterPrompt);*/
			
			textFieldHolder = new Vector<JTextField>();
			
			team1Panel= new JPanel(new GridLayout(2,1));
			JLabel teamLabel1 = new JLabel("Please name Team 1");
			team1 = new JTextField();
			team1Panel.add(teamLabel1);
			team1Panel.add(team1);
			team1Panel.setVisible(true);
			teamNames.add(team1Panel);
			
			textFieldHolder.add(team1);
			
			team2Panel= new JPanel(new GridLayout(2,1));
			JLabel teamLabel2 = new JLabel("Please name Team 2");
			team2 = new JTextField();
			team2Panel.add(teamLabel2);
			team2Panel.add(team2);
			team2Panel.setVisible(false);
			teamNames.add(team2Panel);
			textFieldHolder.add(team2);
			
			team3Panel= new JPanel(new GridLayout(2,1));
			JLabel teamLabel3 = new JLabel("Please name Team 3");
			team3 = new JTextField();
			team3Panel.add(teamLabel3);
			team3Panel.add(team3);
			team3Panel.setVisible(false);
			teamNames.add(team3Panel);
			textFieldHolder.add(team3);
			
			team4Panel= new JPanel(new GridLayout(2,1));
			JLabel teamLabel4 = new JLabel("Please name Team 4");
			team3 = new JTextField();
			team4 = new JTextField();
			team4Panel.add(teamLabel4);
			team4Panel.add(team4);
			team4Panel.setVisible(false);
			teamNames.add(team4Panel);
			
			textFieldHolder.add(team4);
			
			
			
			teamsFromUser = new Vector<String>();
			
			checkBox = new JCheckBox("Quick Play");
			
			logOut = new JButton("Logout");
			
			ratingLabel = new JLabel();
			
			notNetworked = new JRadioButton("Not Networked");
			notNetworked.setSelected(true);
			host = new JRadioButton("Host Game");
			joinGame = new JRadioButton("Join Game");
			
			middleCardLayout = new CardLayout();
			
			
		}
		
		public void createGUI(){
			setSize(1000,600);
			setLocation(500,500);
			
			setLayout(new BorderLayout()); 
			
			
			
			JPanel jp = new JPanel( new GridLayout(3,1,0,0));
			//jp.setLayout(new GridLayout(2,1));
			JLabel welcome = new JLabel("WELCOME TO JEOPARDY!");
			welcome.setFont(welcome.getFont().deriveFont(50.0f));
			welcome.setHorizontalAlignment(JLabel.CENTER);
			JLabel file = new JLabel("Choose the game file, number of teams, and team names before starting the game.");
			file.setHorizontalAlignment(JLabel.CENTER);
			file.setFont(file.getFont().deriveFont(20.0f));
			
			ButtonGroup networkOptions = new ButtonGroup();
			networkOptions.add(notNetworked);
			networkOptions.add(host);
			networkOptions.add(joinGame);
			jp.add(welcome);
			jp.add(file);
			
			JPanel radioHolder = new JPanel();
			
			radioHolder.add(notNetworked);
			radioHolder.add(host);
			radioHolder.add(joinGame);
			radioHolder.setBackground(new Color(111,218,250));
			
			jp.add(radioHolder);
			
			jp.setBackground(new Color(111,218,250));
			add(jp, BorderLayout.NORTH);
			
			centerPanel = new JPanel();
			centerPanel.setLayout(middleCardLayout);
			JPanel midContainer = new JPanel (new GridLayout(2,1,0,5)); //add specifications		
			
			JPanel chooseContainer = new JPanel(new GridLayout(2,1));
			
			fileContainer = new JPanel();
			choose = new JLabel("Please choose a game file.");
			choose.setHorizontalAlignment(JLabel.CENTER);
			fileContainer.add(choose);
			fileContainer.add(chooseFile);
			fileContainer.add(selectedFile); //when initialized will say nothing. CHANGES in addEvent()
			fileContainer.add(ratingLabel);
			chooseContainer.add(fileContainer);
			
			sliderContainer = new JPanel(new GridLayout(2,1));
			sliderContainer.add(teamSelection);
			teamSelection.setHorizontalAlignment(JLabel.CENTER);
			sliderContainer.add(teams);
			chooseContainer.add(sliderContainer);
			
			midContainer.add(chooseContainer);
			midContainer.add(teamNames);
			
			centerPanel.add(midContainer, "1");
			
			
			add(centerPanel, BorderLayout.CENTER);
			
			
			
			JPanel bottomButtons = new JPanel();
			bottomButtons.add(start);
			start.setEnabled(false); ///CHANGE TO FALSE
			bottomButtons.add(clear);
			bottomButtons.add(exit);
			bottomButtons.add(logOut);
			bottomButtons.add(checkBox);
			
			add(bottomButtons, BorderLayout.SOUTH);
			
			middleCardLayout.show(centerPanel, "1");
			joinGUI();
			hostGUI();
			//add(bottomButtons, BorderLayout.SOUTH);
			
			//jp3.setBackground(Color.blue);
			
			
			
		}
		
		public static GameBoard getGame(){
			//gameBoard = new GameBoard(fileName, teamsFromUser, gameType );
			return gameBoard;
		}
		public void displayGB(){
			for(int i = 0; i < numberOfTeams; i++){
				teamsFromUser.add(textFieldHolder.get(i).getText() );
				
				
			}
			//what if Jeopard_Game(fileName, numOfTeams, gameType)
			//then just pass in the Jeopardy_Game type into GameBoard. 
			try {
				gameData = new Jeopardy_Game(fileName, teamsFromUser, gameType);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameBoard = new GameBoard(gameData);
			
			resetHomeScreen();
			
		}
		
		public void hostInit(){
			hostFileButton = new JButton("Choose File");
			hostSelectedFile = new JLabel();
			hostRatingLabel = new JLabel();
		}
		
		
		public void hostGUI(){
			hostPanel = new JPanel(new GridLayout(2,1));
			
			hostSlider = new JSlider(2,4,2); //slider that goes from 2-4
			hostSlider.setSize(1000, 30);
			hostSlider.setMajorTickSpacing(1);
			hostSlider.setMinorTickSpacing(1);
			hostSlider.setPaintTicks(true);
			hostSlider.setPaintLabels(true);
			
			
			
			JPanel textHolder = new JPanel(); //holder for textfield for sizing purposes
			portText.setPreferredSize(new Dimension(300, 50));
			textHolder.add(portText);
			
			
			JPanel file_Slider = new JPanel(new GridLayout(4,1));
			JPanel hostFileContainer = new JPanel();
			choose = new JLabel("Please choose a game file.");
			choose.setHorizontalAlignment(JLabel.CENTER);
			
			hostInit();
			
			hostFileContainer.add(choose);
			//chooseFile = new JButton("Choose File");
			hostFileContainer.add(hostFileButton);
			//selectedFile = new JLabel();
			hostFileContainer.add(hostSelectedFile); //when initialized will say nothing. CHANGES in addEvent()
			//ratingLabel = new JLabel();
			hostFileContainer.add(hostRatingLabel);
			
			
			sliderContainer = new JPanel();
			JLabel hostNumPlayers = new JLabel("Please select the number of teams that will be playing.");
			hostNumPlayers.setHorizontalAlignment(JLabel.CENTER);
			//sliderContainer.add(hostNumPlayers);
			
			JPanel sliderCont = new JPanel();
			hostSlider.setBackground(Color.gray);
			sliderCont.add(hostSlider);
			hostSlider.setPreferredSize(new Dimension(500,40));
			//sliderContainer.add(sliderCont);
			
			file_Slider.add(textHolder);
			file_Slider.add(hostFileContainer);
			file_Slider.add(hostNumPlayers);
			file_Slider.add(sliderCont);
			hostPanel.add(file_Slider);
			
			JPanel setTeamName = new JPanel(new GridLayout(2,2));
			//setTeamName.setBackground(Color.yellow);
			JPanel usernameBox = new JPanel(new GridLayout(2,1));
			JLabel prompt = new JLabel("Please Choose Team Name");
			
			usernameBox.add(prompt);
			
			usernameBox.add(hostUN);
			setTeamName.add(usernameBox);
			setTeamName.add(new JPanel()); //for spacing 
			setTeamName.add(new JPanel());
			setTeamName.add(new JPanel());
			
			hostPanel.add(setTeamName);
			centerPanel.add(hostPanel, "2");
			
			hostFileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					fileChooserWindow = new JFrame(); 
					
					
					//fileChooserWindow.setLayout(new GridLayout(4,1));
					
					JFileChooser chooser = new JFileChooser(); 
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
					chooser.setFileFilter(filter);
					//fileChooserWindow.add(chooser);
					
					int result = chooser.showOpenDialog(fileChooserWindow);
					
					
					if(result == JFileChooser.APPROVE_OPTION){
						//System.out.println(chooser.getSelectedFile().getName());
						hostSelectedFile.setText(chooser.getSelectedFile().getName());
						fileName = chooser.getSelectedFile().getAbsolutePath();
						
						try {
							gameData = new Jeopardy_Game(fileName);
							hostRatingLabel.setText("This game has a rating of: "+gameData.getAvg());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//ratingLabel.setText();

					}
					//System.out.println(chooser.getSelectedFile().getName());
					
				}
				
			});
			
		}
		
		public void joinGUI(){
			JPanel joinGui = new JPanel( new GridLayout(2,1));
			
			JPanel portIPFieldContainer = new JPanel(new GridLayout(1,2)); //2x1 panel
			JPanel portField = new JPanel(); //holder for port textField
			JPanel ipField = new JPanel(); //holder for ip textField
			
			joinPort = new JTextField("Port");
			ip = new JTextField("IP Address");
			
			joinPort.setPreferredSize(new Dimension(200, 50));
			ip.setPreferredSize(new Dimension(200, 50));
			
			portField.add(joinPort);
			ipField.add(ip);
			portIPFieldContainer.add(portField);
			portIPFieldContainer.add(ipField);
			
			joinGui.add(portIPFieldContainer);
			
			
			
			
			JPanel setTeamNames = new JPanel(new GridLayout(2,2));
			JPanel promptName = new JPanel(new GridLayout(2,1));
			promptName.add(new JLabel("Please Enter Your Team Name"));
			//JPanel nameHold = new JPanel();//JPanel so that I can size the textfield
			joinName = new JTextField();
			promptName.add(joinName);
			
			//promptName.add(nameHold);
			setTeamNames.add(promptName);
			
			setTeamNames.add(new JPanel());
			setTeamNames.add(new JPanel());
			setTeamNames.add(new JPanel());
			
			joinGui.add(setTeamNames);
			
			
			centerPanel.add(joinGui, "3");
		}
		
		
		public ServerSocket getServerSocket() throws IOException{
			while(ss == null){
				portLock.lock();
				try{
					System.out.println("765");
					portCondition.await();
					System.out.println("767");
				}catch(InterruptedException ie){
					ie.printStackTrace();
				}
				portLock.unlock();
			}
			
			return ss; 
		}
		
		
}
	
	
	
	

