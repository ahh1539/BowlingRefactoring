package State;

import main.Lane;

public class ThirdFrameFirstStrikeState extends ScoreState {
    /**
     * The 8th frame did not have a strike
     * The 9th frame had a strike
     * @param lane
     */
    public ThirdFrameFirstStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {
        calculatedScores[9] += currentBowlerScores[index];
        calculatedScores[10] = calculatedScores[9] + currentBowlerScores[index];


        return calculatedScores;
    }
}
