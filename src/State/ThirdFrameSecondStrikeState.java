package State;

import main.Lane;

public class ThirdFrameSecondStrikeState extends ScoreState {
    public ThirdFrameSecondStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        return new int[0];
    }
}
