package State;

import main.Lane;

public interface ScoreState {

    int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current);
}
