package State;

import main.Lane;

public abstract class ScoreState {

    Lane lane;

    public ScoreState(Lane lane) {
        this.lane = lane;
    }

    abstract int calculateScore(Lane lane);

    abstract int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current);
}
