package State;

import main.Lane;

public class ThirdFrameThirdTwoStrikeState extends ScoreState {
    /**
     * First ball of the frame was a strike
     * Second ball of the frame was a strike
     * @param lane
     */
    public ThirdFrameThirdTwoStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {
        //add 3x the score to the current frame. 2 for the 2 strikes, and 1 for the bowl
        calculatedScores[10] += currentBowlerScores[index] * 3;

        //no need for a state change, the game is over.
        return calculatedScores;
    }
}
