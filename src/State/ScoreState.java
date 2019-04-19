package State;

import main.Lane;

public abstract class ScoreState {

    Lane lane;

    public ScoreState(Lane lane) {
        this.lane = lane;
    }


    public abstract int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current);
}
