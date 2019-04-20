package State;

import main.Lane;

public class ThirdFrameSecondStrikeState extends ScoreState {
    /**
     * The 9th frame did not have a strike
     * the first ball fo the 10th frame had a strike
     * @param lane
     */
    public ThirdFrameSecondStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        //add two of the current bowl to the current frame
        calculatedScores[10] += 2 * currentBowlerScores[index];

        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new ThirdFrameThirdTwoStrikeState(lane));
        }else{
            lane.setCurrentState(new ThirdFrameThirdStrike(lane));
        }

        return new int[0];
    }
}
