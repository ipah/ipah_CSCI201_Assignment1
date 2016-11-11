package ipah_CSCI201L_Assignment1;

import java.io.Serializable;

public class FinalJeopardyQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String question; 
	private String answer;
	
	
	public void setFJQuestion(String q){
		question = q;
	}
	public void setFJAnswer(String a){
		answer = a;
	}
	public String getFJQuestion(){
		return question;
	}
	
	public String getFJAnswer(){
		return answer;
	}
	
}
