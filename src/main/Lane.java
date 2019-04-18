package main;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Date;
import State.*;

public class Lane extends Thread implements PinsetterObserver {	
	private Party party;
	private Pinsetter setter;
	private HashMap<Bowler, int[]> scores;
	private Vector subscribers;

	private boolean gameIsHalted;

	private boolean partyAssigned;
	private boolean gameFinished;
	private Iterator bowlerIterator;
	private int ball;
	private int bowlIndex;
	private int frameNumber;
	private boolean tenthFrameStrike;

	private int[] curScores;
	private int[][] cumulScores;
	private boolean canThrowAgain;
	
	private int[][] finalScores;
	private int gameNumber;
	
	private Bowler currentThrower;			// = the thrower who just took a throw
	private int currentBowlsScore = 0;

	private ScoreState currentState;

	/** Lane()
	 * 
	 * Constructs a new lane and starts its thread
	 * 
	 * @pre none
	 * @post a new lane has been created and its thered is executing
	 */
	public Lane() { 
		setter = new Pinsetter();
		scores = new HashMap<Bowler, int[]>();
		subscribers = new Vector();

		gameIsHalted = false;
		partyAssigned = false;

		gameNumber = 0;

		setter.subscribe( this );
		
		this.start();
	}

	/** run()
	 * 
	 * entry point for execution of this lane 
	 */
	public void run() {
		
		while (true) {
			if (partyAssigned && !gameFinished) {	// we have a party on this lane, 
								// so next bower can take a throw
			
				while (gameIsHalted) {
					try {
						sleep(10);
					} catch (Exception e) {}
				}


				if (bowlerIterator.hasNext()) {
					currentThrower = (Bowler)bowlerIterator.next();

					canThrowAgain = true;
					tenthFrameStrike = false;
					ball = 0;
					while (canThrowAgain) {
						setter.ballThrown();		// simulate the thrower's ball hiting
						ball++;
						currentThrower.setNumBowls();
					}
					
					if (frameNumber == 9){
						finalScores[bowlIndex][gameNumber] = cumulScores[bowlIndex][9];
						try{
						Date date = new Date();
						String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
						ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, new Integer(cumulScores[bowlIndex][9]).toString());
						} catch (Exception e) {System.err.println("Exception in addScore. "+ e );} 
					}

					
					setter.reset();
					bowlIndex++;
					
				} else {
					frameNumber++;
					resetBowlerIterator();
					bowlIndex = 0;
					if (frameNumber > 9) {
						gameFinished = true;
						gameNumber++;
					}
				}
			} else if (partyAssigned && gameFinished) {
				EndGamePrompt egp = new EndGamePrompt( ((Bowler) party.getMembers().get(0)).getNickName() + "'s Party" );
				int result = egp.getResult();
				egp.distroy();
				egp = null;
				
				
				System.out.println("result was: " + result);
				
				// TODO: send record of scores to control desk
				if (result == 1) {					// yes, want to play again
					resetScores();
					resetBowlerIterator();
					
				} else if (result == 2) {// no, dont want to play another game
					Vector printVector;	
					EndGameReport egr = new EndGameReport( ((Bowler)party.getMembers().get(0)).getNickName() + "'s Party", party);
					printVector = egr.getResult();
					partyAssigned = false;
					Iterator scoreIt = party.getMembers().iterator();
					party = null;
					partyAssigned = false;
					
					publish(lanePublish());
					
					int myIndex = 0;
					while (scoreIt.hasNext()){
						Bowler thisBowler = (Bowler)scoreIt.next();
						ScoreReport sr = new ScoreReport( thisBowler, finalScores[myIndex++], gameNumber );
						sr.sendEmail(thisBowler.getEmail());
						Iterator printIt = printVector.iterator();
						while (printIt.hasNext()){
							if (thisBowler.getNick().equals((String) printIt.next())){
								System.out.println("Printing " + thisBowler.getNick());
								sr.sendPrintout();
							}
						}

					}
				}
			}

			try {
				sleep(10);
			} catch (Exception e) {}
		}
	}
	
	/** recievePinsetterEvent()
	 * 
	 * recieves the thrown event from the pinsetter
	 *
	 * @pre none
	 * @post the event has been acted upon if desiered
	 * 
	 * @param pe The pinsetter event that has been received.
	 */
	public void receivePinsetterEvent(PinsetterEvent pe) {
		
			if (pe.pinsDownOnThisThrow() >=  0) {			// this is a real throw
				markScore(currentThrower, frameNumber + 1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());
	
				// next logic handles the ?: what conditions dont allow them another throw?
				// handle the case of 10th frame first
				if (frameNumber == 9) {
					if (pe.totalPinsDown() == 10) {
						setter.resetPins();
						if(pe.getThrowNumber() == 1) {
							tenthFrameStrike = true;
						}
					}
				
					if ((pe.totalPinsDown() != 10) && (pe.getThrowNumber() == 2 && tenthFrameStrike == false)) {
						canThrowAgain = false;
						//publish( lanePublish() );
					}
				
					if (pe.getThrowNumber() == 3) {
						canThrowAgain = false;
						//publish( lanePublish() );
					}
				} else { // its not the 10th frame
			
					if (pe.pinsDownOnThisThrow() == 10) {		// threw a strike
						canThrowAgain = false;
						//publish( lanePublish() );
					} else if (pe.getThrowNumber() == 2) {
						canThrowAgain = false;
						//publish( lanePublish() );
					} else if (pe.getThrowNumber() == 3)  
						System.out.println("I'm here...");
				}
			} else {								//  this is not a real throw, probably a reset
			}
	}
	
	/** resetBowlerIterator()
	 * 
	 * sets the current bower iterator back to the first bowler
	 * 
	 * @pre the party as been assigned
	 * @post the iterator points to the first bowler in the party
	 */
	private void resetBowlerIterator() {
		bowlerIterator = (party.getMembers()).iterator();
	}

	/** resetScores()
	 * 
	 * resets the scoring mechanism, must be called before scoring starts
	 * 
	 * @pre the party has been assigned
	 * @post scoring system is initialized
	 */
	private void resetScores() {
		Iterator bowlIt = (party.getMembers()).iterator();

		while ( bowlIt.hasNext() ) {
			int[] toPut = new int[25];
			for ( int i = 0; i != 25; i++){
				toPut[i] = -1;
			}
			Bowler b = (Bowler) bowlIt.next();
			scores.put( b, toPut );
		}
		
		
		
		gameFinished = false;
		frameNumber = 0;
	}
		
	/** assignParty()
	 * 
	 * assigns a party to this lane
	 * 
	 * @pre none
	 * @post the party has been assigned to the lane
	 * 
	 * @param theParty		Party to be assigned
	 */
	public void assignParty( Party theParty ) {
		party = theParty;
		resetBowlerIterator();
		partyAssigned = true;
		
		curScores = new int[party.getMembers().size()];
		cumulScores = new int[party.getMembers().size()][10];
		finalScores = new int[party.getMembers().size()][128]; //Hardcoding a max of 128 games, bite me.
		gameNumber = 0;
		
		resetScores();
	}

	/** markScore()
	 *
	 * Method that marks a bowlers score on the board.
	 * 
	 * @param Cur		The current bowler
	 * @param frame	The frame that bowler is on
	 * @param ball		The ball the bowler is on
	 * @param score	The bowler's score 
	 */
	private void markScore( Bowler Cur, int frame, int ball, int score ){
		currentBowlsScore = score;

		int[] curScore;
		int index =  ( (frame - 1) * 2 + ball);

		curScore = scores.get(Cur);

		curScore[ index - 1] = score;

		scores.put(Cur, curScore);
		getScore( Cur, frame );
		publish( lanePublish() );
	}

	/** lanePublish()
	 *
	 * Method that creates and returns a newly created laneEvent
	 * 
	 * @return		The new lane event
	 */
	private LaneEvent lanePublish(  ) {
		LaneEvent laneEvent = new LaneEvent(party, bowlIndex, currentThrower, cumulScores, scores, frameNumber+1, curScores, ball, gameIsHalted);
		return laneEvent;
	}


	/** getScore()
	 *
	 * Method that calculates a bowlers score
	 * 
	 * @param Cur		The bowler that is currently up
	 * @param frame	The frame the current bowler is on
	 * 
	 * @return			The bowlers total score
	 */
	private void getScore( Bowler Cur, int frame) {
        int[] currentBowlerScores;
        int strikeballs = 0;
        int totalScore = 0;
        currentBowlerScores = (int[]) scores.get(Cur);
        for (int i = 0; i != 10; i++) {
            cumulScores[bowlIndex][i] = 0;
        }
        //the current ball
        int current = 2 * (frame - 1) + ball - 1;

        //Iterate through each ball until the current one.
        for (int index = 0; index != current + 2; index++) {
            cumulScores[bowlIndex] = currentState.calculateScore(index, currentBowlerScores, cumulScores[bowlIndex], current);
        }



        /*

			//Spare:
			if( index%2 == 1 && currentBowlerScores[index - 1] + currentBowlerScores[index] == 10 && index < current - 1 && index < 19){
				// second roll
				// first roll + second roll == 10 pins knocked down
				// cant roll more than 19 balls in a single game
				//This ball was a the second of a spare.
				//Also, we're not on the current ball.
				//Add the next ball to the ith one in cumul.
				cumulScores[bowlIndex][(index/2)] += currentBowlerScores[index+1] + currentBowlerScores[index];
				if (index > 1) {
					//cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 -1];
				}
			} else if( index < current && index%2 == 0 && currentBowlerScores[index] == 10  && index < 18){
				strikeballs = 0;
				// first roll
				// knocked down all pins
				// ball rolled isnt greater than the 18th
				//This ball is the first ball, and was a strike.
				//If we can get 2 balls after it, good add them to cumul.
				if (currentBowlerScores[index+2] != -1) {
					strikeballs = 1;
					if(currentBowlerScores[index+3] != -1) {
						//Still got em.
						strikeballs = 2;
					} else if(currentBowlerScores[index+4] != -1) {
						//Ok, got it.
						strikeballs = 2;
					}
				}
				if (strikeballs == 2){
					//Add up the strike.
					//Add the next two balls to the current cumulscore.
					cumulScores[bowlIndex][index/2] += 10;
					if(currentBowlerScores[index+1] != -1) {
						cumulScores[bowlIndex][index/2] += currentBowlerScores[index+1] + cumulScores[bowlIndex][(index/2)-1];
						if (currentBowlerScores[index+2] != -1){
							if( currentBowlerScores[index+2] != -2){
								cumulScores[bowlIndex][(index/2)] += currentBowlerScores[index+2];
							}
						} else {
							if( currentBowlerScores[index+3] != -2){
								cumulScores[bowlIndex][(index/2)] += currentBowlerScores[index+3];
							}
						}
					} else {
						if ( index/2 > 0 ){
							cumulScores[bowlIndex][index/2] += currentBowlerScores[index+2] + cumulScores[bowlIndex][(index/2)-1];
						} else {
							cumulScores[bowlIndex][index/2] += currentBowlerScores[index+2];
						}
						if (currentBowlerScores[index+3] != -1){
							if( currentBowlerScores[index+3] != -2){
								cumulScores[bowlIndex][(index/2)] += currentBowlerScores[index+3];
							}
						} else {
							cumulScores[bowlIndex][(index/2)] += currentBowlerScores[index+4];
						}
					}
				} else {
					break;
				}
			}else {
				//We're dealing with a normal throw, add it and be on our way.
				if( index%2 == 0 && index < 18){
					if ( index/2 == 0 ) {
						//First frame, first ball.  Set his cumul score to the first ball
						if(currentBowlerScores[index] != -2){
							cumulScores[bowlIndex][index/2] += currentBowlerScores[index];
						}
					} else if (index/2 != 9){
						//add his last frame's cumul to this ball, make it this frame's cumul.
						if(currentBowlerScores[index] != -2){
							cumulScores[bowlIndex][index/2] += cumulScores[bowlIndex][index/2 - 1] + currentBowlerScores[index];
						} else {
							cumulScores[bowlIndex][index/2] += cumulScores[bowlIndex][index/2 - 1];
						}
					}
				} else if (index < 18){
					if(currentBowlerScores[index] != -1 && index > 2){
						if(currentBowlerScores[index] != -2){
							cumulScores[bowlIndex][index/2] += currentBowlerScores[index];
						}
					}
				}
				if (index/2 == 9){
					if (index == 18){
						cumulScores[bowlIndex][9] += cumulScores[bowlIndex][8];
					}
					if(currentBowlerScores[index] != -2){
						cumulScores[bowlIndex][9] += currentBowlerScores[index];
					}
				} else if (index/2 == 10) {
					if(currentBowlerScores[index] != -2){
						cumulScores[bowlIndex][9] += currentBowlerScores[index];
					}
				}
			}
		}
		*/
        }

	/** isPartyAssigned()
	 * 
	 * checks if a party is assigned to this lane
	 * 
	 * @return true if party assigned, false otherwise
	 */
	public boolean isPartyAssigned() {
		return partyAssigned;
	}
	
	/** isGameFinished
	 * 
	 * @return true if the game is done, false otherwise
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}

	/** subscribe
	 * 
	 * Method that will add a subscriber
	 * 
	 * @param adding Observer that is to be added
	 */

	public void subscribe( LaneObserver adding ) {
		subscribers.add( adding );
	}

	/** unsubscribe
	 * 
	 * Method that unsubscribes an observer from this object
	 * 
	 * @param removing	The observer to be removed
	 */
	
	public void unsubscribe( LaneObserver removing ) {
		subscribers.remove( removing );
	}

	/** publish
	 *
	 * Method that publishes an event to subscribers
	 * 
	 * @param event	Event that is to be published
	 */

	public void publish( LaneEvent event ) {
		if( subscribers.size() > 0 ) {
			Iterator eventIterator = subscribers.iterator();
			
			while ( eventIterator.hasNext() ) {
				( (LaneObserver) eventIterator.next()).receiveLaneEvent( event );
			}
		}
	}

	/**
	 * Accessor to get this Lane's pinsetter
	 * 
	 * @return		A reference to this lane's pinsetter
	 */

	public Pinsetter getPinsetter() {
		return setter;	
	}

	/**
	 * Pause the execution of this game
	 */
	public void pauseGame() {
		gameIsHalted = true;
		publish(lanePublish());
	}
	
	/**
	 * Resume the execution of this game
	 */
	public void unPauseGame() {
		gameIsHalted = false;
		publish(lanePublish());
	}

	public HashMap getScores() {
		return scores;
	}

	public int getBall() {
		return ball;
	}

	public Bowler getCurrentThrower() {
		return currentThrower;
	}

	public int getCurrentBowlsScore() {
		return currentBowlsScore;
	}


	public void setCurrentState(ScoreState currentState) {
		this.currentState = currentState;
	}

	public int[][] getCumulScores() {
		return cumulScores;
	}

	public void setCumulScores(int[][] cumulScores) {
		this.cumulScores = cumulScores;
	}
}
