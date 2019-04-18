package State;

import main.Lane;

/**
 * this is the FIRST ROLL this frame
 * the last roll was NOT a spare
 * last roll WAS a strike
 * two rolls ago WAS a strike
 * @author Alex Hurley
 */
public class FirstCaseFourState implements ScoreState {

    @Override
    public int calculateScore(Lane lane) {
        return 0;
    }
}
