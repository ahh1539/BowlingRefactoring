package State;

import main.Lane;

public class ThirdFrameFirstTwoStrikeState extends ScoreState {
    /**
     * The 8th frame had a strike
     * The 9th frame had a strike
     * @param lane
     */
    public ThirdFrameFirstTwoStrikeState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {
        calculatedScores[8] += 1 * currentBowlerScores[index];
        //add to 2 frames ago

        calculatedScores[9] +=  2 * currentBowlerScores[index];
        //add to 1 frame ago

        calculatedScores[10] +=  currentBowlerScores[index] + calculatedScores[9];
        // add to current frame

        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new ThirdFrameSecondTwoStrikeState(lane));
        }else{
            lane.setCurrentState(new ThirdFrameSecondPreviousFrameStrikeState(lane));
        }

        return calculatedScores;
    }
}
