package helper;

import java.util.ArrayList;

/**
 * Keeps track of the current turn, if a draw was offered and en passsant
 * @author Steve Flynn
 *
 */

public class TurnTracker {
	/**
	 * Current players turn
	 */
	private boolean currentTurn;
	
	/**
	 * True if a player offered a draw, the "draw" input is not valid inless this is true
	 */
	private boolean drawOffered;
	
	/**
	 * True if an enPassant pawn capture is possible
	 */
	private boolean enPassant;
	
	/**
	 * Store the color of the piece that can en passant. True for what false for black.
	 */
	private boolean enPassantColor;
	
	ArrayList<String> turnList=new ArrayList<String>();
	
	/**
	 * Constructor for TurnTracker class. Initializes fields
	 */
	public TurnTracker() {
		this.drawOffered=false;
		this.setEnPassant(false);
		this.currentTurn=true;
	}
	
	
	/**
	 * Checks if draw has been offered
	 * @return- true if draw has been offered, false otherwise
	 */
	public boolean isDrawOffered() {
		return drawOffered;
	}
	
	/**
	 * Sets draw offered
	 * @param drawOffered- new state of drawOffered field
	 */
	public void setDrawOffered(boolean drawOffered) {
		this.drawOffered = drawOffered;
	}

	/**
	 * Checks if enPassant is possible
	 * @return- true of en passant pawn capture is possible
	 */
	public boolean isEnPassant() {
		return enPassant;
	}


	/**
	 * Sets enPassant pawn capture
	 * @param enPassant
	 */
	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}

	/**
	 * Gets the current turn. True for white, false for black
	 * @return- true if it is white's turn, false for black
	 */
	public boolean isCurrentTurn() {
		return currentTurn;
	}


	/**
	 * Sets the current turn
	 * @param currentTurn- new value of current turn to be set
	 */
	public void setCurrentTurn(boolean currentTurn) {
		this.currentTurn = currentTurn;
	}

	/**
	 * Gets the value of the en passant color
	 * @return- true if a white piece can en passant, false for black
	 */
	public boolean isEnPassantColor() {
		return enPassantColor;
	}

	/**
	 * Sets the enPassant field
	 * @param enPassantColor- new value to set field
	 */
	public void setEnPassantColor(boolean enPassantColor) {
		this.enPassantColor = enPassantColor;
	}
	
	
}
