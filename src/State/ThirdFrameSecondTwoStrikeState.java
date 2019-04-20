package State;

import main.Lane;

public class ThirdFrameSecondTwoStrikeState extends ScoreState {
    public ThirdFrameSecondTwoStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        return new int[0];
    }
}
