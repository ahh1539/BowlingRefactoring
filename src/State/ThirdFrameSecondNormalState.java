package State;

import main.Lane;

public class ThirdFrameSecondNormalState extends ScoreState {
    public ThirdFrameSecondNormalState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        return new int[0];
    }
}
