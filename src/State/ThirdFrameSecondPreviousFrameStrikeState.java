package State;

import main.Lane;

public class ThirdFrameSecondPreviousFrameStrikeState extends ScoreState {
    public ThirdFrameSecondPreviousFrameStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[9] =+ currentBowlerScores[index];
        //add to the previous frame with a strike

        calculatedScores[10] += 2 * currentBowlerScores[index];
        //add 2 to the current frame. one for the count in last frame and one for hte count in this frame.

        return new int[0];
    }
}
