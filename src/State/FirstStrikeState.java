package State;

import main.Lane;

/**
 * this is the FIRST ROLL of the frame
 * last roll NOT a spare
 * last ball WAS a strike
 * 2 rolls ago was NOT a strike
 * @author alex hurley
 */
public class FirstStrikeState implements ScoreState {

    @Override
    public int calculateScore(Lane lane) {
        return 0;
    }
}
