package State;

import main.Lane;

public class ThirdFrameThirdSpare extends ScoreState {
    /**
     * First ball of the frame was not a strike
     * Second ball of the frame was a spare
     * @param lane
     */
    public ThirdFrameThirdSpare(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {

        calculatedScores[10] = currentBowlerScores[10] + ( 2 * currentBowlerScores[index]);

        return calculatedScores;
    }
}
