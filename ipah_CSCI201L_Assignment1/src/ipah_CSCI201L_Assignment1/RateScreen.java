package ipah_CSCI201L_Assignment1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RateScreen extends JFrame{
	private JButton okButtn;
	private JSlider rateSlider;
	private JPanel rateScreen;
	private String _winner;
	private JLabel resultsLabel;
	private JLabel sliderLabel; 
	private JLabel currRating;
	
	private Jeopardy_Game _game;
	RateScreen(String winner, Jeopardy_Game game){
		_winner = winner;
		_game=game;
		initialize();
		createGUI();
		addEvents();
		
	}
	
	public void initialize(){
		rateScreen = new JPanel(new GridLayout(6,1));
		okButtn = new JButton("Ok");
		okButtn.setEnabled(false);
		rateSlider = new JSlider(JSlider.HORIZONTAL,0,5,0);
		rateSlider.setSize(500, 30);
		rateSlider.setMajorTickSpacing(1);
		rateSlider.setMinorTickSpacing(1);
		rateSlider.setPaintTicks(true);
		rateSlider.setPaintLabels(true);
		
		resultsLabel = new JLabel();
		resultsLabel.setHorizontalAlignment(JLabel.CENTER);
		resultsLabel.setFont(resultsLabel.getFont().deriveFont(28.0f));
		resultsLabel.setForeground(Color.white);
		if(!_winner.equals("no winner")){//if there is a winner
			resultsLabel.setText("Congratulations " + _winner+ "!");
			
		}
		
		sliderLabel = new JLabel();
		
		currRating = new JLabel();
		currRating.setFont(currRating.getFont().deriveFont(25.0f));
		currRating.setHorizontalAlignment(JLabel.CENTER);
	}
	
	public void createGUI(){
		setSize(450,450);
		
		rateScreen.add(new JPanel()); //empty for spacing purposes  (1)
		JPanel hold = new JPanel();
		hold.setBackground(Color.darkGray);
		hold.add(resultsLabel);//(2)
		rateScreen.add(hold);
		
		JPanel instr = new JPanel(new GridLayout(2,1)); //r1: prompt r2: slider & label
		JLabel prompt = new JLabel("Please rate this game file on a scale of 1 to 5.");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		prompt.setFont(prompt.getFont().deriveFont(20.0f));
		instr.add(prompt);
		
		JPanel sliderComps = new JPanel(new GridLayout(1,2));
		
		JPanel slider = new JPanel();
		slider.add(rateSlider); //puts slider in panel
		sliderComps.add(slider);
		sliderComps.add(sliderLabel);
		
		instr.add(sliderComps);
		
		rateScreen.add(instr);//(3)
		
		//NEED RATING OF PARTICULAR FILE
		currRating.setText("Current average rating: " + (_game.getTotRate()/_game.getNumOfRate())+"/"+ 5);
		rateScreen.add(currRating); //(4)
		
		rateScreen.add(new JPanel()); //empty panel for spacing (5)
		
		JPanel bttnHolder = new JPanel();
		bttnHolder.add(okButtn);
		
		rateScreen.add(bttnHolder);
		
		
		add(rateScreen);
	}
	
	public void addEvents(){
		okButtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_game.addRating(rateSlider.getValue());
				_game.incToNumOfRates();
				try {
					_game.updateFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				setVisible(false);
			}
			
		});
		
		rateSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				String s = Integer.toString(rateSlider.getValue()); 
				sliderLabel.setText(s);
				okButtn.setEnabled(true);
			}
			
		});
	}
}
