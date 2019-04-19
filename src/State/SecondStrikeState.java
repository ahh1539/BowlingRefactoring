package State;

import main.Bowler;
import main.Lane;

import java.util.HashMap;

/**
 * this is the SECOND ROLL of the frame
 * the last roll was NOT spare
 * the last roll was NOT strike
 * 2 rolls ago WAS a Strike
 * second bowl after a strike in the frame after the strike
 * @author Alex Hurley && Michael Dolan
 */
public class SecondStrikeState extends ScoreState {

    public SecondStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[index/2] += calculatedScores[index];
        // adds the score to the current frame

        calculatedScores[(index/2) - 2] += calculatedScores[index];
        // adds the current score to the strikes frame

        if (calculatedScores[index] + calculatedScores[index-1] == 10){
            // if it is a spare it sends it into first roll spare state
            lane.setCurrentState(new FirstSpareState(lane));
        } else {
            lane.setCurrentState(new FirstNormalState(lane));
        }
        return new int[0];
    }


}
