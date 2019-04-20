package State;

import main.Lane;

public class ThirdFrameFirstNormalState extends ScoreState {
    public ThirdFrameFirstNormalState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
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
