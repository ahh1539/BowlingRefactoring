package State;

import main.Lane;

/**
 * this is the FIRST ROLL of the frame
 * last roll NOT a spare
 * last ball WAS a strike
 * 2 rolls ago was NOT a strike
 * @author alex hurley & Michael Dolan
 */
public class FirstStrikeState extends ScoreState {

    private int haveAllBallsBeenAdded = 1;

    public FirstStrikeState(Lane lane) {
        super(lane);
    }


    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[(index/2)] += currentBowlerScores[index];
        calculatedScores[(index/2) - 1] += currentBowlerScores[index];
        // adds current bowl to the previous frame
        if (haveAllBallsBeenAdded != 2){
            // checks to make sure that both of the rolls after the strike were added to the
            lane.setCurrentState(new FirstStrikeState(lane));
        } else{
            lane.setCurrentState(new FirstNormalState(lane));
        }

        return new int[0];
    }
}
