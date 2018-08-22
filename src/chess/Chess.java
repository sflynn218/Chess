package chess;

import java.util.*;
import java.util.Scanner;
import helper.*;
import pieces.*;

/**
 * Main class with the main method
 * This contains the main loop and uses the GameManager class to complete all of it's tasks
 * @author Steve Flynn
 *
 */

public class Chess{
	/**
	 * Scanner object used for keyboard input from user
	 */
	static Scanner keyboard = new Scanner(System.in);
	
	/**
	 * Shows the prompt the user uses to enter the entry
	 * @param turn- the current players turn
	 * @return- string with the user input
	 */
	public static String showPrompt(boolean turn) {
		
		String input;
		if(turn) {
			System.out.print("White's Turn: ");
			input= keyboard.nextLine();
		}
		else {
			System.out.print("Black's Turn: ");
			input= keyboard.nextLine();
		}
		System.out.println("");
		return input;
	}
	
	/**
	 * If the user enters a bad command this shows an error message
	 */
	public static void showError() {
		System.out.println("Illegal move, try again");
		System.out.println("");
	}
	
	/**
	 * Shows who won the game
	 * @param turn
	 */
	public static void showWin(boolean turn) {
		if(turn) {
			System.out.println("White wins");
		}
		else {
			System.out.println("Black wins");
		}
	}
	
	/**
	 * Displays a draw
	 */
	public static void showDraw() {
		System.out.println("draw");
	}
	

	/**
	 * Main method
	 * @param args- input arguments. Not used
	 */
	public static void main(String[] args) {
		//local variables
		String input;
		String value;
		
		//create GameManager object
		GameManager manager=new GameManager();
		
		
		//display board
		System.out.println(manager.printBoard());
		
		while(true) {
			//show input
			input=showPrompt(manager.turn());
			
			//parse input and make changes
			input=manager.input(input);
			if(input.equals("resign")) {
				showWin(!manager.turn());
				break;
			}
			else if(input.equals("draw accepeted")) {
				showDraw();
				break;
			}
		
			else if(input.equals("check")) {
				System.out.println(manager.printBoard());
				System.out.println("Check");
				System.out.println("");
				manager.toggleTurn(!manager.turn());
				continue;
			}
			else if(input.equals("success")) {
				
			}
			else if(input.equals("checkmate")) {
				System.out.println(manager.printBoard());
				System.out.println("Checkmate");
				System.out.println("");
				showWin(manager.turn());
				break;
			}
			else if(input.equals("stalemate")) {
				System.out.println("stalemate");
				showDraw();
				break;
			}
			
			else{
				showError();
				continue;
			}
			//print board
			System.out.println(manager.printBoard());
			
			//toggle turn
			manager.toggleTurn(!manager.turn());
		}
				
	}
}