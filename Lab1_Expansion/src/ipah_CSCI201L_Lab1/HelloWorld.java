package ipah_CSCI201L_Lab1;

import  java.util.Scanner;

public class HelloWorld {
	public static void main(String []args){
		System.out.println("Please enter your username: ");
		Scanner sc = new Scanner(System.in);
		String un = sc.nextLine();
		System.out.println("hello " + un + " welcome to java!");
		sc.close();
	}
}
