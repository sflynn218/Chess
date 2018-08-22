package pieces;

import java.util.ArrayList;
import helper.Tuple;

/**
 * This class is a made up piece that is used for the en passant rule
 * It is created whenever a real pawn moves up two spaces
 * The inherited abstract method in this particular class does not need to do anything
 * @author Steve Flynn
 *
 */

public class GhostPawn extends Piece {
	
	/**
	 * Pawn representing the real pawn this piece is associated with
	 */
	private Pawn realPawnCounterPart;
	
	/**
	 * Constructor for GhostPawn Class
	 * @param location- starting location
	 * @param newColor- color of GhostPawn, true for white false for black
	 * @param newId- id of the piece
	 * @param newType- type of the piece
	 * @param pawn
	 */
	public GhostPawn(Tuple location, boolean newColor, String newId, String newType, Pawn pawn) {
		super(location, newColor, newId, newType);
		this.realPawnCounterPart=pawn;
	}
	
	/**
	 * Overridden resetLocations method
	 * GhostPawns never move, as such this function just returns an empty list
	 */
	@Override
	public int resetLocations(pieces.Piece[][] board) {
		ArrayList<Tuple> temp=new ArrayList<Tuple>();
		
		this.setLocations(temp);
		return 0;
	}
	
	/**
	 * Overwritten to string method
	 */
	@Override
	public String toString() {
		return "GP";
	}
	
	/**
	 * Gets the real pawn counter part of this piece
	 * @return- real pawn counter part, the real pawn associated with this piece
	 */
	public Pawn getRealPawnCounterPart() {
		return realPawnCounterPart;
	}


}
