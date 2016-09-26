package ipah_CSCI201L_Assignment1;


import java.util.Vector;

public class Categories {
	
	//list of questions in the category
	private Vector<Questions> vec = new Vector<Questions>(); 
	String categoryName; 
	
	public Vector<Questions> getQVec(){
		return vec;
	}
	public Questions getQuestionwIndex(int i){
		return vec.get(i);
	}
	public Questions getQuestion(String sc){
		for(int i = 0; i < vec.size(); i++){
			if(vec.get(i).getScore().equals(sc)){
				return vec.get(i);
			}
		}
		return null;
	}
	public void askQ(String key){
		for(int i = 0; i < vec.size(); i++){
			if(vec.get(i).getScore().equals(key)){
				System.out.println(vec.get(i).getQuestionName());
				
			}
		}
		
		System.out.println();
	}
	public void addQuestions(Questions q){
		vec.addElement(q);
	}
	
	public  Categories(String name){ //constructor that creates categ. with spec. name
		categoryName = name;
	}
	public String getCategoryName(){
		return categoryName;
	}
	
	public boolean isFinished(){
		boolean t_f = false;
		for(int i =0; i < vec.size(); i++){
			if(vec.get(i).questionUsed()){ //if a question has been asked
				t_f = true;
			}
			else{
				return false;
			}
		}
		return t_f;
	}
	public int getVecSize(){
		return vec.size();
	}
		
}
