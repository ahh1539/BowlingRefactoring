package State;

import main.Lane;

public class ThirdFrameThirdNoThird extends ScoreState {
    public ThirdFrameThirdNoThird(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        //do nothing, the bowler did not get a third ball in the 10th frame
        return calculatedScores;
    }
}
