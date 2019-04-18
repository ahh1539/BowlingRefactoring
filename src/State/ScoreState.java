package State;

import main.Lane;

public interface ScoreState {

    int calculateScore(Lane lane);

    int[] calculateScore(int i, int[] curScore, int[][] cumulScores, int current);
}
