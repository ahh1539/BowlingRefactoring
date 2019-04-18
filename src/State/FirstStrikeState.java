package State;

import main.Lane;

/**
 * this is the FIRST ROLL of the frame
 * last roll NOT a spare
 * last ball WAS a strike
 * 2 rolls ago was NOT a strike
 * @author alex hurley & Michael Dolan
 */
public class FirstStrikeState extends ScoreState {

    public FirstStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int calculateScore(Lane lane) {
        return 0;
    }

    @Override
    int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        return new int[0];
    }
}
