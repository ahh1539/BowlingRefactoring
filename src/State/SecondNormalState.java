package State;

import main.Bowler;
import main.Lane;

import java.util.HashMap;

/**
 * SECOND ROLL in a frame
 * The last ball Rolled was NOT a Spare or Strike
 * Two rolls ago was NOT a Strike
 * @author Alex Hurley & Michael Dolan
 */
public class SecondNormalState extends ScoreState {

    public SecondNormalState(Lane lane) {
        super(lane);
    }


    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        if (index == 1) { //If this is the first frame of the game
            calculatedScores[index / 2] = currentBowlerScores[index] + currentBowlerScores[index-1];
            //adds the current rolls score to the frame total
        }else{
            calculatedScores[index / 2] = currentBowlerScores[index] + currentBowlerScores[index-1] + calculatedScores[(index / 2) - 1];
            //adds the current rolls score to the frame total. and adds the running total
        }
        if (currentBowlerScores[index] + currentBowlerScores[index -1] == 10){
            // if the bowl was a spare then it sends the
            lane.setCurrentState(new FirstSpareState(lane));
        } else {
            // if the bowl was not a spare it changes state back into first regular
            lane.setCurrentState(new FirstNormalState(lane));
        }
        return calculatedScores;
    }
}
