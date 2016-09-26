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
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;



	
	
	
	
	public class JeopardyGUI extends JFrame{
		
		
		
		private JLabel teamSelection; //prompts user to select number of teams
		private JTextField team1, team2, team3, team4;
		private Vector<JTextField> textFieldHolder; //holds textFields so that they can be added properly
		private JLabel selectedFile; //file that user selects to play with.
		private JSlider teams; 
		private Hashtable<Integer, JLabel> table;
		private JButton chooseFile, start, clear, exit; 
		private JPanel teamNames; // JPanel that has certain number of textfields depending on slider input
		private int numberOfTeams = 1;
		private String fileName; 
		private Vector<String> teamsFromUser; // vector that holds all the team names entered.
		 //team that is current answering a question
		private JCheckBox checkBox; //QuickPlay
		private JPanel team1Panel, team2Panel, team3Panel, team4Panel;
		private JFrame fileChooserWindow; 
		
		private String gameType;
		private GameBoard gameBoard; 
		
		
		private static final long serialVersionUID = 1L;

		JeopardyGUI() throws FileNotFoundException{
			super("Welcome to Jeopardy!");
			
			initializeComps();
			createGUI();
			addEvents();
			
			
			
		}
		
		
		
		
		
		private void addEvents(){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			clear.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for( int i = 0; i < textFieldHolder.size(); i++){
						textFieldHolder.get(i).setText("");
						selectedFile.setText("");
					}
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
					System.out.println(textFieldHolder.size());
					int counter = 0;
					
					for(int i = 0; i < textFieldHolder.size(); i++){//if field isn't empty
						if(!textFieldHolder.get(i).getText().equals("")){
							//game.addTeamToGame(textFieldHolder.get(i).getText()); // adds team to game
							counter++;
						}
						
						//System.out.println("teams: "+ textFieldHolder.get(i).getText());
					}
					
					displayGB();
					if((counter == textFieldHolder.size()) && !selectedFile.getText().isEmpty()    ){
						
						
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
					//FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
					//chooser.setFileFilter(filter);
					//fileChooserWindow.add(chooser);
					
					int result = chooser.showOpenDialog(fileChooserWindow);
					
					
					if(result == JFileChooser.APPROVE_OPTION){
						//System.out.println(chooser.getSelectedFile().getName());
						selectedFile.setText(chooser.getSelectedFile().getName());
						fileName = chooser.getSelectedFile().getAbsolutePath();

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
					System.out.println("count" + counter);
					System.out.println("num" + numberOfTeams);
					System.out.println(selectedFile.getText().isEmpty());
					System.out.println(selectedFile.getText());
					if((counter == numberOfTeams) && !selectedFile.getText().isEmpty()){
						System.out.println("listen");

						start.setEnabled(true);
					}
				}
				
			};
			
			
			for(int i = 0; i < textFieldHolder.size(); i++){
				textFieldHolder.get(i).getDocument().addDocumentListener(listener); 
			}
			 
			//setNumOfTeams(teams.getValue()) ;
			teams.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent ae){
					numberOfTeams = ((JSlider)ae.getSource()).getValue();
					/*System.out.println("current slider val: " + teams.getValue());
					setNumOfTeams(teams.getValue()) ;
					//System.out.println("momma: " + teams.getValue()); 
					if( numberOfTeams <  teams.getValue()){
						for(int i = numberOfTeams; i < textFieldHolder.size(); i++){
							textFieldHolder.remove(i);
						}
					}
					
					setNumOfTeams(teams.getValue()) ;
					System.out.println("change made");
					teamNames.removeAll();
				    teamNames.revalidate();
					teamNames.repaint();
					textFieldHolder.clear();*/
					
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
			
		}
		
		public void initializeComps(){
			gameType = "Standard";
			
			teamSelection = new JLabel("Please choose the number of teams that will be playing on the slider below.");
			
			
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
		}
		
		public void createGUI(){
			setSize(1000,600);
			setLocation(500,500);
			
			setLayout(new GridLayout(4,1,1,1)); 
			
			JPanel jp = new JPanel( new GridLayout(2,1,0,0));
			//jp.setLayout(new GridLayout(2,1));
			JLabel welcome = new JLabel("WELCOME TO JEOPARDY!");
			welcome.setFont(welcome.getFont().deriveFont(64.0f));
			welcome.setHorizontalAlignment(JLabel.CENTER);
			JLabel file = new JLabel("Choose the game file, number of teams, and team names before starting the game.");
			file.setHorizontalAlignment(JLabel.CENTER);
			file.setFont(file.getFont().deriveFont(20.0f));
			jp.add(welcome);
			jp.add(file);
			
			jp.setBackground(new Color(111,218,250));
			add(jp);
			
			
					
			
			JPanel chooseContainer = new JPanel(new GridLayout(2,1));
			
			JPanel fileContainer = new JPanel();
			JLabel choose = new JLabel("Please choose a game file.");
			choose.setHorizontalAlignment(JLabel.CENTER);
			fileContainer.add(choose);
			fileContainer.add(chooseFile);
			fileContainer.add(selectedFile); //when initialized will say nothing. CHANGES in addEvent()
			chooseContainer.add(fileContainer);
			
			JPanel sliderContainer = new JPanel(new GridLayout(2,1));
			sliderContainer.add(teamSelection);
			teamSelection.setHorizontalAlignment(JLabel.CENTER);
			sliderContainer.add(teams);
			chooseContainer.add(sliderContainer);
			
			add(chooseContainer);
			
			
			
			
			add(teamNames);
			
			
			JPanel bottomButtons = new JPanel();
			bottomButtons.add(start);
			start.setEnabled(false); ///CHANGE TO FALSE
			bottomButtons.add(clear);
			bottomButtons.add(exit);
			bottomButtons.add(checkBox);
			
			
			add(bottomButtons, BorderLayout.SOUTH);
			
			//jp3.setBackground(Color.blue);
			
		}
		public void displayGB(){
			for(int i = 0; i < numberOfTeams; i++){
				teamsFromUser.add(textFieldHolder.get(i).getText() );
				
				
			}
			gameBoard = new GameBoard(fileName, teamsFromUser, gameType );
			
		}
}
	
	
	
	

