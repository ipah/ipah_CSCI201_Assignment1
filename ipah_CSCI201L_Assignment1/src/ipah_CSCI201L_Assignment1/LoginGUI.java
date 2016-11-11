package ipah_CSCI201L_Assignment1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Server.GameServer;
import Server.JPSQLDriver;

public class LoginGUI extends JFrame {
	private JTextField usernameText;
	private JTextField passwordText;
	private JButton loginBttn, createBttn;
	private JPanel loginScreen;
	private JeopardyGUI gui;
	private User userInfo;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Vector<User> userVect = new Vector<User>();
	private static JLabel warningLabel; 
	
	LoginGUI() throws FileNotFoundException, IOException, ClassNotFoundException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ois = new ObjectInputStream(new FileInputStream("src/users.txt"));
		//userVect = new  Vector<User>();
		
		try {
			userVect = (Vector<User>)ois.readObject();
			ois.close();
			
			
		} catch (EOFException eof){
			//proceeds

				
			System.out.println(eof.getMessage());
		}catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*
		try {
			gui = new JeopardyGUI();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		initialize();
		createGUI();
		addEvents();
		setVisible(true);
	}
	
	
	
	public void initialize(){

		setSize(600,600);
		
		loginScreen = new JPanel( new GridLayout(6,1));
		
		loginScreen.setBackground(new Color(111,218,250));
		
		
		
		
		usernameText = new JTextField(); //row3
		usernameText.setPreferredSize(new Dimension(450,75));
		passwordText = new JTextField();//row4
		passwordText.setPreferredSize(new Dimension(450,75));
		
		
	
		loginBttn = new JButton("Login");
		loginBttn.setPreferredSize(new Dimension(200,75));
		
		loginBttn.setOpaque(true);
	    loginBttn.setBackground(new Color(192,192,192));
	    loginBttn.setBorderPainted(false);
		/*
		loginBttn.setOpaque(true);
		loginBttn.setBackground(Color.gray);
		loginBttn.setBorderPainted(false);
		*/
		
		createBttn = new JButton("Create Account");
		createBttn.setPreferredSize(new Dimension(200,75));
		createBttn.setBackground(Color.gray);
		createBttn.setOpaque(true);
		createBttn.setBackground(new Color(192,192,192));
		createBttn.setBorderPainted(false);
		
		loginBttn.setEnabled(false);
		createBttn.setEnabled(false);
		
		
		
		
		
		warningLabel = new JLabel();
		warningLabel.setHorizontalAlignment(JLabel.CENTER);
		
		try {
			gui = new JeopardyGUI();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createGUI(){
		JLabel loginInstr = new JLabel("Login or create an account to play!"); // row1
		loginInstr.setHorizontalAlignment(JLabel.CENTER);
		loginInstr.setFont(loginInstr.getFont().deriveFont(28.0f));
		loginScreen.add(loginInstr);
		
		JLabel gameTitle = new JLabel("Jeopardy!");//row 2
		gameTitle.setHorizontalAlignment(JLabel.CENTER);
		gameTitle.setFont(gameTitle.getFont().deriveFont(34.0f));
		loginScreen.add(gameTitle);
		
		loginScreen.add(warningLabel); //warning 
		
		JPanel holder = new JPanel();
		holder.setBackground(new Color(111,218,250));
		holder.add(usernameText);
		loginScreen.add(holder);
		holder = new JPanel();
		holder.setBackground(new Color(111,218,250));
		holder.add(passwordText);
		loginScreen.add(holder);
		
		JPanel loginBttnsPanel = new JPanel(new GridLayout(1,2));
		
		holder = new JPanel();
		holder.add(loginBttn);
		holder.setBackground(Color.darkGray);
		loginBttnsPanel.add(holder);
		
		holder = new JPanel();
		holder.add(createBttn);
		
		holder.setBackground(Color.darkGray);
		loginBttnsPanel.add(holder);
		
		
		
		
		loginScreen.add(loginBttnsPanel);
		
		add(loginScreen);
		
	}
	
	public void addEvents(){
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
				
				if(!usernameText.getText().isEmpty() && !passwordText.getText().isEmpty()){
					
					createBttn.setEnabled(true);
					loginBttn.setEnabled(true);
				}
			}
			
		};
		
		usernameText.getDocument().addDocumentListener(listener); 
		passwordText.getDocument().addDocumentListener(listener); 
		
		createBttn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JPSQLDriver sql = new JPSQLDriver();
				sql.connect();
				
				if(sql.doesExist(usernameText.getText())){
					warningLabel.setText("Username already exists");
				}
				else{
					userInfo = new User(usernameText.getText(), passwordText.getText()); //creates new user
					userVect.add(userInfo);
					sql.add(usernameText.getText(), passwordText.getText());
					
			
					
					gui.setVisible(true);
					
					dispose();
					//setVisible(false);
				}
				sql.stop();
			}
			
			
		});
		
		loginBttn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JPSQLDriver sql = new JPSQLDriver();
				sql.connect();
				if(sql.correctCredentials(usernameText.getText(), passwordText.getText())){
					
					
					gui.setVisible(true);
					
					dispose();
				}
				
				sql.stop();
				/*	if(validLogin(usernameText.getText(), passwordText.getText())){
					gui.setVisible(true);
					setVisible(false);
				}
				
				warningLabel.setText("Invalid Credentials");
				*/
			}
		
			
		});
	
	}
	public void outputSetup() throws FileNotFoundException, IOException{
		//oos = new ObjectOutputStream(new FileOutputStream(outFile.getAbsolutePath()));
		oos.writeObject(userVect);
		oos.flush();
		oos.close();
	}
	
	public boolean alreadyExists(String name){
		if(!userVect.isEmpty()){
			for(int i = 0; i < userVect.size(); i++){
				if(userVect.get(i).getUsername().equals(name)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean validLogin(String username, String password){
		for(int i = 0; i < userVect.size(); i++){
			if(userVect.get(i).getUsername().equals(username) && userVect.get(i).correctPassword().equals(password)){
				return true;
			}
		}
		
		return false; 
	}
	
	public static void sendWarning(){
		warningLabel.setText("Invalid Credentials");
	}
	
	
	public static void main( String[]args){
		try {
			new LoginGUI();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
