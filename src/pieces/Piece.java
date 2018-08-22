package pieces;


import java.util.ArrayList;
import helper.Tuple;

/**
 * This class is an abstract class that is a parent for every piece
 * @author Steven Flynn
 *
 */



public abstract class Piece {
	/**
	 * The current piece's location on the board stored as a Tuple
	 */
	private Tuple currentLocation; 	
	
	
	/**
	 * Stores the captured state of this piece
	 */
	private boolean dead=false;
	
	/**
	 * Stores the moved state for this piece, if the piece has never moved before it is false
	 */
	private boolean moved=false;
	
	/**
	 * Stores the color of the piece, true for white false for black
	 */
	private boolean color;
	
	/**
	 * This is a unique id given to each piece
	 */
	private String id;
	
	/**
	 * stores the type of the piece, for example a piece that is a rook stores that here
	 */
	private String type;
	
	/**
	 * Stores a list of all of the locations that this piece can get to
	 * Some exceptions and limitations are listed in the individual piece class
	 */
	private ArrayList<Tuple> locations;
	
	/**
	 * Constructor for Piece class
	 * @param location- the starting location of the piece
	 * @param newColor- the color of the piece
	 * @param newId- the id of the piece
	 */
	public Piece(Tuple location, boolean newColor, String newId, String newType) {
		this.currentLocation=location;
		this.color=newColor;
		this.id=newId;
		type=newType;
	}
	
	/**
	 * Abstract method to be implemented in each specific piece class
	 * resets the locations field with the current available locations
	 * @param board- the current board represented as a 2D arrat of Pieces
	 * @return- return value is not used
	 */
	public abstract int resetLocations(Piece[][] board);
	
	
	/**
	 * getter for current location
	 * @return the location of the piece as a Tuple
	 */
	public Tuple getCurrentLocation() {
		return this.currentLocation;
	}
	
	
	/**
	 * setter for current location
	 * @param x- the Tuple the current location will be set to
	 */
	public void setCurrentLocation(Tuple x) {
		this.currentLocation=x;
	}
	
	
	/**
	 * getter for color
	 * white is true, black is false
	 * @return the color of this piece
	 */
	public boolean getColor() {
		return this.color;
	}
	
	/**
	 * Same as getMoved, returns true if piece has moved
	 * @return- value of moved
	 */
	public boolean hasMoved() {
		return this.moved;
	}
	
	/**
	 * sets the value of moved
	 * @param x- new value of moved
	 */
	public void setMoved(boolean x) {
		this.moved=x;
	}
	
	/**
	 * Gets the list of locations this piece can access
	 * @return- list of locations
	 */
	public ArrayList<Tuple> getLocations(){
		return this.locations;
	}
	
	/**
	 * Sets the locations the piece can access
	 * This is usually called in the abstract reset locations method
	 * @param x- the new list of locations
	 */
	public  void setLocations(ArrayList<Tuple> x){
		this.locations=x;
	}

	/**
	 * returns the type of this piece
	 * @return- string representing type
	 */
	public String getType() {
		return type;
	}
	

	/**
	 * Gets the string representing the ID
	 * @return- ID of this piece
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Gets boolean representing if this piece is dead
	 * @return- boolean isDead
	 */
	public boolean isDead() {
		return this.dead;
	}

	/**
	 * Sets the dead state of this piece
	 * @param dead- new boolean to set isDead to
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
