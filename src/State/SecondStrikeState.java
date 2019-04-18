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
 * @author Alex Hurley & Michael Dolan
 */
public class SecondStrikeState implements ScoreState {

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        return new int[0];
    }
}
