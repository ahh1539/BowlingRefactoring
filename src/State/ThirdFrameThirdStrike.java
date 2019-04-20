package State;

import main.Lane;

public class ThirdFrameThirdStrike extends ScoreState {
    /**
     * first ball of 10th frame was a strike
     * second ball was not a strike
     * @param lane
     */
    public ThirdFrameThirdStrike(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {
        //one score for the strike, one for the current bowl
        calculatedScores[10] =+ 2* currentBowlerScores[index];

        //no need for a state change the game is over
        return calculatedScores;
    }
}
