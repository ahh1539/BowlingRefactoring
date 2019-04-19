package State;

import main.Lane;

public class SecondAfterStrike extends ScoreState {

    ScoreState state;

    public SecondAfterStrike(Lane lane, ScoreState state) {
        super(lane);
        this.state = state;
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        //don't add anything because there is no ball thrown

        //change state to firstStrikeState
        lane.setCurrentState(state);

        //return scores as they are
        return calculatedScores;
    }
}
