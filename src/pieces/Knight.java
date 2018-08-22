package pieces;

import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;
import java.util.Iterator;

import helper.Board;
import helper.Tuple;


/**
 * This class is used for the Knight, every Knight will be created with this
 * Extends Piece.java
 * @author Steve Flynn
 *
 */
public class Knight extends Piece {
	
	/**
	 * Constructor for the Knight Class
	 * @param location-starting location of the piece
	 * @param newColor- color of the piece. True for white, false for black
	 * @param newId- unique id of the piece
	 * @param newType- type of the piece. Should be "knight"
	 */
	public Knight(Tuple location, boolean newColor, String newId, String newType) {
		super(location, newColor, newId, newType);
	}
	
	/**
	 * Overwritten method, finds all of the valid locations this piece can move to
	 * returns an array list of those locations
	 */
	@Override
	public int resetLocations(Piece[][] board) {
		//local variables
		ArrayList<Tuple> locations=new ArrayList<Tuple>();
		
		//get objects location
		Tuple location=this.getCurrentLocation();
		
		int x=location.getRow();
		int y=location.getCol();
		
		//check if piece is still alive
		if(this.isDead()) {
			this.setLocations(locations);
			return 0;
		}
		
		//find locations
		locations.add(new Tuple(x - 2, y - 1));
        locations.add(new Tuple(x - 1, y - 2));
        locations.add(new Tuple(x + 1, y - 2));
        locations.add(new Tuple(x + 2, y - 1));
        locations.add(new Tuple(x - 2, y + 1));
        locations.add(new Tuple(x - 1, y + 2));
        locations.add(new Tuple(x + 1, y + 2));
        locations.add(new Tuple(x + 2, y + 1));
		
        //remove locations that contain the same colored piece
        Iterator<Tuple> itr = locations.iterator();
        while(itr.hasNext()) {
        	Tuple temp=itr.next();
        	int row=temp.getRow();
        	int col=temp.getCol();
        	try {
	        	if(board[row][col] != null) {
	        		Piece blocking= board[row][col];
	        		if(blocking instanceof GhostPawn) {
						continue;
					}
	        		if(this.getColor() == blocking.getColor()) {
	        			itr.remove();
	        		}
	        	}
        	}
        	catch(ArrayIndexOutOfBoundsException exception) {
	        	itr.remove();
	        	continue;
	        }
			//System.out.println(locations.get(i).toString());
		}
        this.setLocations(locations);
		return 0;
	}
	
	/**
	 * String representation of this piece based on the color
	 */
	@Override
	public String toString() {
		if(this.getColor()) {
			return "wN";
		}
		else {
			return "bN";
		}
	}

}
