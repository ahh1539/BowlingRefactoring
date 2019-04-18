package State;

import main.Bowler;
import main.Lane;

import java.util.HashMap;

/**
 * this is the SECOND ROLL of the frame
 * the last roll was NOT spare
 * the last roll was NOT strike
 * 2 rolls ago WAS a Strike
 * @author Alex Hurley
 */
public class SecondStrikeState implements ScoreState {

    @Override
    public int calculateScore(Lane lane) {
        int ScoreThisRoll = lane.getCurrentBowlsScore();

        int CurrentRollNum = lane.getCurrentThrower().getNumBowls();

        HashMap<Bowler, int[]> BowlerScores = lane.getScores();

        int[] scores = BowlerScores.get(lane.getCurrentThrower());

        // TODO change state to something else when done

        // TODO go and add current and previous roll score to the strike score 2 rolls ago for this class functionality
        return 0;
    }
}
