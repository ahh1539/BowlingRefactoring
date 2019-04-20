package State;

import main.Lane;

public class ThirdFrameSecondNormalState extends ScoreState {
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
