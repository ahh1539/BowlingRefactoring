package State;

import main.Lane;

/**
 * This is the FIRST ROLL of the frame
 * the last ball rolled was NOT a strike or spare
 * two rolls ago was NOT a strike
 * @author Alex Hurley & michael dolan
 */

public class FirstNormalState extends ScoreState {


    public FirstNormalState(Lane lane){
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[index/2] += currentBowlerScores[index];
        // adds the current bowls score to the frames total score

        if (currentBowlerScores[index] != 10){
            lane.setCurrentState(new SecondNormalState(lane));
        }
        else {
            lane.setCurrentState(new FirstStrikeState(lane));
        }

        return calculatedScores;
    }

}
