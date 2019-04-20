package State;

import main.Lane;

public class ThirdFrameSecondTwoStrikeState extends ScoreState {
    /**
     * The 9th frame had a strike
     * the first ball fo the 10th frame had a strike
     * @param lane
     */
    public ThirdFrameSecondTwoStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        //add bowl to previous frame with strike
        calculatedScores[9] += currentBowlerScores[index];

        //add current bowl to current frame twice + once for previous frame
        //add to current frame twice because the current frame has a strike too.
        calculatedScores[10] += currentBowlerScores[index] * 3;

        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new ThirdFrameThirdTwoStrikeState(lane));
        }else{
            lane.setCurrentState(new ThirdFrameThirdStrike(lane));
        }

        return calculatedScores;
    }
}
