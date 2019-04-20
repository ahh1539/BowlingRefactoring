package State;

import main.Lane;

public class ThirdFrameThirdNoThird extends ScoreState {

    /**
     * neither a strike or a spare was earned in the 10th frame
     * @param lane
     */
    public ThirdFrameThirdNoThird(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {
        //do nothing, the bowler did not get a third ball in the 10th frame
        return calculatedScores;
    }
}
