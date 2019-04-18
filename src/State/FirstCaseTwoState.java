package State;

import main.Lane;

/**
 * This is the FIRST ROLL of the frame
 * the last ball rolled WAS a spare and NOT a strike
 * two rolls ago was NOT a strike
 * @author Alex Hurley
 */

public class FirstCaseTwoState implements ScoreState {
    @Override
    public int calculateScore(Lane lane) {
        return 0;
    }
}
