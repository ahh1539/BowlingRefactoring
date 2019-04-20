package State;

import main.Lane;

public class ThirdFrameSecondNormalState extends ScoreState {
    /**
     * The 9th frame did not have a strike
     * the first ball fo the 10th frame did not have a strike
     * @param lane
     */
    public ThirdFrameSecondNormalState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[10] += currentBowlerScores[index];

        if (currentBowlerScores[index] + currentBowlerScores[index-1] == 10){
            lane.setCurrentState(new ThirdFrameThirdSpare(lane));
        }else{
            lane.setCurrentState(new ThirdFrameThirdNoThird(lane));
        }

        return calculatedScores;
    }
}
