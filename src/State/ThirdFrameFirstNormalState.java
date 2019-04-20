package State;

import main.Lane;

public class ThirdFrameFirstNormalState extends ScoreState {
    /**
     * The 9th frame had neither a strike or a spare
     * @param lane
     */
    public ThirdFrameFirstNormalState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {
        calculatedScores[10] = currentBowlerScores[index] + calculatedScores[9];
        //just add to the running total

        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new ThirdFrameSecondStrikeState(lane));
        }else {
            lane.setCurrentState(new ThirdFrameSecondNormalState(lane));
        }

        return calculatedScores;
    }
}
