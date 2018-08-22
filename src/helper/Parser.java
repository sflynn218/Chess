package helper;

import pieces.Piece;
import helper.Tuple;

/**
 * This class will parse the input so that we can get the coordinates
 * for the pieces to be moved and the coordinates where the pieces 
 * should end up.
 * @author Tavel Findlater
 *
 */


public class Parser {
	
	/**
	 * Constructor for Parser class
	 */
	public Parser() {
		
	}
	
	/**
	 * Parses the user input establishes what the user wanted to do
	 * i.e. draw, resign, normal move or error
	 * @param input- the input the user entered
	 * @param turn- current players turn. True for white, false for black
	 * @return- the enumerated types from Return.java
	 */
	public Return parse(String input, boolean turn) {
		//split input
		String[] splitInput=input.split(" ");
		
		if(splitInput.length == 1) {
			if(splitInput[0].equals("resign")) {
				return Return.RESIGN;
			}
			else if(splitInput[0].equals("draw")) {
				if(turn) {
					return Return.DRAW2;
				}
				else {
					return Return.ERROR;
				}
			}
			else 
				return Return.ERROR;
		}
		else if(splitInput.length == 3 || splitInput.length == 2) {
			String starting= splitInput[0];
			String ending= splitInput[1];
			
			if(starting.length() != 2 || ending.length() != 2) {
				return Return.ERROR;
			}
			if(splitInput[0].isEmpty() || splitInput[1].isEmpty()) {
				return Return.ERROR;
			}
			if(!Character.isLetter(starting.charAt(0)) || 
					   !Character.isDigit(starting.charAt(1)) ||
					   !Character.isLetter(ending.charAt(0)) ||
					   !Character.isDigit(ending.charAt(1))) {
				return Return.ERROR;
			}
			if(splitInput[1].length() != 2 || splitInput[0].length() != 2) {
				return Return.ERROR;
			}
			
			if(splitInput.length == 2) {
				return Return.SUCCESS;
			}
			else {
				if(splitInput[2].equals("draw?")) {
					return Return.DRAW1;
				}
				else if(splitInput[2].equals("N") || splitInput[2].equals("B") ||
						splitInput[2].equals("Q") || splitInput[2].equals("R")) {
					return Return.PROMO;
				}
				else {
					return Return.ERROR;
				}
			}
		}
		else {
			return Return.ERROR;
		}
	}
	
	/**
	 * Converts the input to a array of tuples
	 * The fist element is the starting location the user selected
	 * The second element is the location the user wants to move to
	 * @param input- value user entered 
	 * @param board- 2D array representing board
	 * @return- array of Tuples
	 */
	public Tuple[] inputToTuple(String input, Piece[][] board) {
		//split input
		String[] temp=input.split(" ");
		if(temp.length > 3 || temp.length <= 1) {
			return null;
		}
		
		int col1= Tuple.charToCol(temp[0].charAt(0));
		if(col1 == -1) {
			return null;
		}
		int row1=Character.getNumericValue(temp[0].charAt(1)) - 1;
		
		int col2= Tuple.charToCol(temp[1].charAt(0));
		if(col2== -1) {
			return null;
		}
		int row2=Character.getNumericValue(temp[1].charAt(1)) - 1;
		
		try {
			board[row1][col1]=board[row1][col1];
			board[row2][col2]=board[row2][col2];
			
			Tuple[] ret=new Tuple[2];
			ret[0]=new Tuple(row1, col1);
			ret[1]=new Tuple(row2, col2);
			return ret;
		}
		catch(ArrayIndexOutOfBoundsException exception) {
			return null;
		}	
	}
}

