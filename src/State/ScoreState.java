package State;

import main.Lane;

public interface ScoreState {

    int calculateScore(Lane lane);

    int[] calculateScore(int index, int[] currentBowlerScores, int current);
}
