package chess;

import java.util.ArrayList;
import java.util.Iterator;

import helper.*;
import pieces.*;


/**
 * This class manages the game and makes sure all of the rules are followed.
 * Everything is done through this class, think of it as the moderator.
 * It also accepts input and parses it.
 * @author Steve Flynn
 *
 */
public class GameManager {
	/**
	 * Board object to keep track of board
	 */
	private Board board;
	
	/**
	 * PieceManager object to keep track of pieces
	 */
	private PieceManager pm;
	
	/**
	 * Turn tracker to keep track of turns
	 */
	private TurnTracker tk=new TurnTracker();
	
	/**
	 * Parser object to parse input
	 */
	private Parser parser=new Parser();
	
	/**
	 * White king
	 */
	Piece whiteKing;
	
	/**
	 * Black kings
	 */
	Piece blackKing;
	
	/**
	 * Constructor for GameManger class
	 * Initializes objects
	 */
	public GameManager() {
		//initialize board and piece manager
		pm=new PieceManager();
		board=new Board(pm.getList());
	}
	
	/** Helper function that moves a pieces back to it's original state
	 * @param destroyed- the piece that was destroyed in the original move. null if no piece was destroyed
	 * @param moved- the piece that was moved
	 * @param oldLocation- the previous location
	 * @param hasMoved- the moved state of the piece that was moved
	 * @param newLocation- the newLocation the piece tried to move to
	 */
	private void revertMove(Piece destroyed, Piece moved, Tuple oldLocation, boolean hasMoved, Tuple newLocation) {
		//revert move
		if(destroyed != null) {
			destroyed.setDead(false);
			destroyed.setCurrentLocation(newLocation);
			board.getBoard()[newLocation.getRow()][newLocation.getCol()]=destroyed;
		}
		board.move(oldLocation, moved);
		moved.setMoved(hasMoved);
		pm.resetAllLocations(board.getBoard());
	}
	
	private boolean staleMate(boolean turn) {
		Tuple oldLocation;
		Tuple newLocation;
		boolean hasMoved;
		ArrayList<Tuple> locations;
		ArrayList<Piece> pieces=pm.getList();
		King king=null;
		Piece destroyed=null;
		
		if(turn) {
			king=pm.getWhiteKing();
		}
		else {
			king=pm.getBlackKing();
		}
		
		outer:for(int i=0; i< pieces.size(); i++) {
			Piece temp=pieces.get(i);
			if(temp.getColor() != turn) {
				continue outer;
			}
			if(temp.getType().equals("ghostPawn")) {
				continue outer;
			}
			
			if(temp.isDead()) {
				continue outer;
			}
			if(!temp.getLocations().isEmpty()) {
				locations=temp.getLocations();
				 Iterator<Tuple> itr = locations.iterator();
				 while(itr.hasNext()) {
					 oldLocation=temp.getCurrentLocation();
					 newLocation=(Tuple)itr.next();
					 hasMoved=temp.hasMoved();
					 destroyed=board.move(newLocation, temp);
					 pm. resetAllLocations(board.getBoard());
					 if(pm.isLocationAttacked(king.getCurrentLocation(), !turn)) {
						 itr.remove();
					 }
					//revert move
					if(destroyed != null) {
						board.getBoard()[newLocation.getRow()][newLocation.getCol()]=destroyed;
						destroyed.setDead(false);
						destroyed.setCurrentLocation(newLocation);
					}
					else {
						board.getBoard()[newLocation.getRow()][newLocation.getCol()]=null;
					}
					board.getBoard()[oldLocation.getRow()][oldLocation.getCol()]=temp;
					temp.setCurrentLocation(new Tuple(oldLocation.getRow(), oldLocation.getCol()));
					temp.setMoved(hasMoved);
					pm.resetAllLocations(board.getBoard());
				}
				if(!locations.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Helper function to determine a checkmate
	 * @return- true if a king is in checkmate
	 */
	private boolean checkMate() {
		boolean hasMoved=false;
		Tuple oldLocation;
		Piece destroyed=null;
		boolean turn=!tk.isCurrentTurn();
		ArrayList<Piece> attacking=pm.getAttackingPieces(board.getBoard(), turn);
		ArrayList<Piece> allPieces=pm.getList();
		ArrayList<Tuple> kingMoves;
		
		King king=null;
		
		if(turn) {
			king= pm.getWhiteKing();
			kingMoves=king.getLocations();
		}
		else {
			king= pm.getBlackKing();
			kingMoves=king.getLocations();
		}
		
		//king can not move
		//spot king moves into must be clear as well
		 Iterator<Tuple> itr = kingMoves.iterator();
		 while(itr.hasNext()) {
			 Tuple temp=(Tuple)itr.next();
			 if(pm.isLocationAttacked(temp, tk.isCurrentTurn())) {
				 itr.remove();
			 }
		 }
		 oldLocation=king.getCurrentLocation();
		 itr = kingMoves.iterator();
		 if(!kingMoves.isEmpty()) {
			itr = kingMoves.iterator();
			while(itr.hasNext()) {
				Tuple temp=(Tuple)itr.next();
				hasMoved=king.hasMoved();
				destroyed=board.move(temp, king);
				pm.resetAllLocations(board.getBoard());
				if(pm.isLocationAttacked(temp, tk.isCurrentTurn())) {
					itr.remove();
				}
				//revert move
				if(destroyed != null) {
					board.getBoard()[temp.getRow()][temp.getCol()]=destroyed;
					destroyed.setDead(false);
					destroyed.setCurrentLocation(temp);
				}
				else {
					board.getBoard()[temp.getRow()][temp.getCol()]=null;
				}
				board.getBoard()[oldLocation.getRow()][oldLocation.getCol()]=king;
				king.setCurrentLocation(new Tuple(oldLocation.getRow(), oldLocation.getCol()));
				king.setMoved(hasMoved);
			 }
		}
		
		//check if kingMoves is empty again
		if(!kingMoves.isEmpty()) {
			return false;
		}
		
		//if the king cant move and there is more than one attacker, checkmate
		if(attacking.size() > 1) {
			return true;
		}
		if(attacking.isEmpty()) {
			return false;
		}
		Piece attacker=attacking.get(0);
		
		//piece putting king in check can be captured
		for(int i=0; i < allPieces.size(); i++) {
			Piece temp=allPieces.get(i);
			if(temp.getType().equals("ghostPawn")) {
				continue;
			}
			if(temp.getColor() != king.getColor()) {
				continue;
			}
			if(temp.getLocations().contains(attacker.getCurrentLocation())) {
				return false;
			}
		}
		
		//get a list of attackers squars
		ArrayList<Tuple> locations=board.findPath(attacker.getCurrentLocation(), king.getCurrentLocation());
		
		if(attacker.getType().equals("rook") || attacker.getType().equals("queen") || attacker.getType().equals("bishop")) {
			//ally can intercept
			for(int i=0; i<allPieces.size(); i++) {
				Piece temp=allPieces.get(i);
				if(temp.getType().equals("ghostPawn")) {
					continue;
				}
				if(temp.getColor() != king.getColor()) {
					continue;
				}
				for(int l=0; l<locations.size(); l++) {
					if(temp.getLocations().contains(locations.get(l))) {
						return false;
					}
				}
	
			}
		}
		
		return true;
	}
	
	/**
	 * Method to deal with a successfully parsed input
	 * Does most of the work --> checks the piece the user selected, checks the number of checks, moves the piece
	 * deals with castling, checkmate and check
	 * @param input- input from the user
	 * @param type- used for pawn promotion. Null if left unspecified
	 * @return- int value representing result of the function. -1 for bad input, 10 for checkmate, 2 for check and 0 for success
	 */
	private int success(String input, String type) {
		boolean hasMoved=false;
		Tuple oldLocation;
		Piece destroyed=null;
		int numberOfChecks=0;
		boolean castleFlag=false;
		boolean promotionFlag=false;
		
		
		//first reset locations
		pm.resetAllLocations(board.getBoard());
		
		//call tuple function to get locations
		Tuple[] locations=parser.inputToTuple(input, board.getBoard());
		if(locations == null) {
			return -1;
		}
		
		//get piece and new location
		Piece current=board.getPieceByLocaiton(locations[0]);
		Tuple newLocation=locations[1];
		
		if(current == null || newLocation==null) {
			return -1;
		}
		oldLocation=current.getCurrentLocation();
		
		
		//check castling, passant
		if(current.getType().equals("king")) {
			castleFlag=pm.castleHandeler((King)current, board.getBoard(), newLocation, tk.isCurrentTurn());
		}
		

		//checks to see if move is valid
		//check piece color
		if(tk.isCurrentTurn() != current.getColor()) {
			return -1;
		}
		//check and store number of checks
		numberOfChecks=pm.getNumberOfChecks(board.getBoard(), tk.isCurrentTurn());
		
		//System.out.println("number of checks before move= " + numberOfChecks);
		//check piece can make that move
		if(!current.getLocations().contains(newLocation)) {
			return -1;
		}
		
		//make the move
		if(!castleFlag && !promotionFlag) {
			hasMoved=current.hasMoved();
			destroyed=board.move(newLocation, current);
		}
		
		//reset locations again
		pm.resetAllLocations(board.getBoard());
		
		numberOfChecks=pm.getNumberOfChecks(board.getBoard(), tk.isCurrentTurn());
		if(numberOfChecks != 0) {
			
			//revert move
			this.revertMove(destroyed, current, oldLocation, hasMoved, newLocation);
			
			if(this.checkMate()) {
				return 10;
			}
			else {
				return -1;
			}
		}

		//check promotion and enpassant
		if(current.getType().equals("pawn")) {
			current.resetLocations(board.getBoard());
			if(((Pawn)current).canPromote()) {
				pm.promotion(((Pawn)current), type, newLocation, board.getBoard());
			}
			else {
				if(type != null) {
					return -1;
				}
			}
			if(pm.checkPawn(board.getBoard(), (Pawn)current, newLocation)) {
				tk.setEnPassant(true);
				tk.setEnPassantColor(tk.isCurrentTurn());
			}
			if(destroyed != null && destroyed instanceof GhostPawn) {
				Pawn pawn=((GhostPawn)destroyed).getRealPawnCounterPart();
				pawn.setDead(true);
				board.getBoard()[pawn.getCurrentLocation().getRow()][pawn.getCurrentLocation().getCol()]=null;
				pawn.setCurrentLocation(new Tuple(-1,-1));
			}
			current.setMoved(true);		}

		//check if enpassant needs to be cleared
		pm.removeGhost(board.getBoard(), tk.isCurrentTurn());
		
		pm.resetAllLocations(board.getBoard());
		
		
		//check for check/checkmate/stalemate
		if(pm.isEnemyChecked(board.getBoard(), tk.isCurrentTurn())) {
			pm.resetAllLocations(board.getBoard());
			if(this.checkMate()) {
				return 10;
			}
			else {
				return 2;
			}
		}
		else {
			if(this.staleMate(!tk.isCurrentTurn())) {
				return 5;
			}
		}
		return 0;
	}
	
	/**
	 * Parses the user input and returns a value to main based on the result
	 * @param input- string of exactly what the user entered
	 * @return- string representing the result. 
	 */
	public String input(String input) {
		Return value=parser.parse(input, tk.isDrawOffered());
		int x=-1;
		if(value == Return.SUCCESS) {
			x=this.success(input, null);
			if(x == 2) {
				tk.setDrawOffered(false);
				return "check";
			}
			else if(x == 0) {
				tk.setDrawOffered(false);
				return "success";
			}
			else if(x == 10) {
				return "checkmate";
			}
			else if(x == 5) {
				return "stalemate";
			}
			else{
				tk.setDrawOffered(false);
				return "error";
			}
		}
		else if(value == Return.DRAW1) {
			x=this.success(input, null);
			tk.setDrawOffered(true);
			if(x == 0) {
				return "success";
			}
			else if(x == 2) {
				return "check";
			}
			else if(x == 10) {
				return "checkmate";
			}
			else if(x == 5) {
				return "stalemate";
			}
			else {
				return "error";
			}
		}
		else if(value == Return.DRAW2) {
			if(tk.isDrawOffered()) {
				this.toggleTurn(this.turn());
				return "draw accepeted";
			}
			else
				tk.setDrawOffered(false);
				return "error";
		}
		else if(value == Return.PROMO) {
			String type=input.split(" ")[2];
			if(type.equals("N") || type.equals("Q") || type.equals("B") || type.equals("R")) {
				x=this.success(input, type);
				tk.setDrawOffered(false);
				if(x == 0) {
					return "success";
				}
				else if(x == 2) {
					return "check";
				}
				else if(x == 10) {
					return "checkmate";
				}
				else if(x == 5) {
					return "stalemate";
				}
				else {
					return "error";
				}
			}
			else {
				return "error";
			}
		}
		else if(value == Return.RESIGN) {
			tk.setDrawOffered(false);
			return "resign";
		}
		else {
			tk.setDrawOffered(false);
			return "error";
		}
	}
	
	/**
	 * Function main calls to print the board
	 * @return- string representation of the board
	 */
	public String printBoard(){
		return board.printBoard();
	}
	
	/**
	 * Used to get the 2D array of the board
	 * @return- 2D array of the board
	 */
	public Piece[][] getBoard(){
		return board.getBoard();
	}
	
	/**
	 * Accesses the current turn from the turn tracker class
	 * @return- current turn. True for white, false for black
	 */
	public boolean turn() {
		return tk.isCurrentTurn();
	}
	
	/**
	 * Uses the turn tracker object to toggle the current turn
	 * @param x- new value for turn
	 */
	public void toggleTurn(boolean x) {
		tk.setCurrentTurn(x);
	}
}