package State;

import main.Bowler;
import main.Lane;

import java.util.HashMap;

/**
 * SECOND ROLL in a frame
 * The last ball Rolled was NOT a Spare or Strike
 * Two rolls ago was NOT a Strike
 * @author Alex Hurley & Michael Dolan
 */
public class SecondNormalState extends ScoreState {

    public SecondNormalState(Lane lane) {
        super(lane);
    }

    @Override
    public int calculateScore(Lane lane) {
        int ScoreThisRoll = lane.getCurrentBowlsScore();

        int CurrentRollNum = lane.getCurrentThrower().getNumBowls();

        HashMap<Bowler, int[]> BowlerScores = lane.getScores();

        int[] scores = BowlerScores.get(lane.getCurrentThrower());

        int[][] UIscores = lane.getCumulScores();

        if (CurrentRollNum % 2 == 0){
            //checks to make sure that the roll is the second roll
            // changes state to a first roll state evens are first odds are second
            lane.setCurrentState(new FirstNormalState(lane));

        }
        else {
            if (scores[CurrentRollNum - 1] == 10 || scores[CurrentRollNum - 1] + scores[CurrentRollNum - 2] == 10){
                // makes sure that last rolled ball was not a spare or strike
                // having a spare on the first roll would be impossible
                // TODO change to a previous roll strike or spare state
            }

            else if (scores[CurrentRollNum - 2] == 10){
                // changes the
                lane.setCurrentState(new SecondStrikeState(lane));
            }
            else {
                // TODO change gui scores accordingly, this is the functionality that this class is supposed to reach
            }

            UIscores
        }
        return 0;
    }

    @Override
    int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        return new int[0];
    }
}
