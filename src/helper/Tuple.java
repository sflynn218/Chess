package helper;

/**
 * This class creates a tuple used for defining a chess column and chess row
 * It also contains a static method that converts a character to an int
 * @author Steve Flynn
 *
 */
public class Tuple {
	private int row;
	private int col;
	
	/**
	 * Main constructor, takes in two ints to create the tuple
	 * @param x- row of this tuple
	 * @param y- column of this tuple 
	 */
	public Tuple(int x, int y) {
		this.row=x;
		this.col=y;
	}
	
	
	/**
	 * Overloaded constructor.  Takes in a character and an int
	 * @param c- character that gets converted to an int
	 * @param y- int that will become the row
	 */
	public Tuple(int y, char c) {
		this(y, Tuple.charToCol(c));
	}
	
	
	/**
	 * Converts a character to a int so it can be used as a column
	 * @author Tavel Findlater
	 * @param c- character to be converted to int
	 * @return- int converted from character
	 */
	public static int charToCol(char c) {
		switch(c) {
		case 'a': return 0;
		case 'b': return 1;
		case 'c': return 2;
		case 'd': return 3;
		case 'e': return 4;
		case 'f': return 5;
		case 'g': return 6;
		case 'h': return 7;
		default: return -1;
		}
	}

	/**
	 * Gets the column of this Tuple
	 * @return- int representing column
	 */
	public int getCol() {
		return col;
	}


	/**
	 * Gets the row of this Tuple
	 * @return- int reoresenting row
	 */
	public int getRow() {
		return row;
	}

	
	/**
	 * To string method when the tuple needs to be printed, usually used for debugging
	 */
	@Override
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
	
	
	/**
	 * Overwritten equals method needed for checking if an array list of tuples contains a certain tuples
	 * Also used to compare Tuples directly
	 * @param o- any object but should specifically be a Tuple
	 * @return- true if the two Tuples row and columns are equal
	 */
	@Override
	public boolean equals(Object o) {
		Tuple x=null;
		if(o instanceof Tuple) {
			x=(Tuple)o;
		}
		else {
			return false;
		}
		if(this.col == x.col && this.row == x.row)
			return true;
		else
			return false;
	}
}
