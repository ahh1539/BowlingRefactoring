package State;

import main.Bowler;
import main.Lane;

import java.util.HashMap;

/**
 * This is the FIRST ROLL of the frame
 * the last ball rolled was NOT a strike or spare
 * two rolls ago was NOT a strike
 * @author Alex Hurley
 */

public class FirstNormalState implements ScoreState {
    @Override
    public int calculateScore(Lane lane) {

        int ScoreThisRoll = lane.getCurrentBowlsScore();

        int CurrentRollNum = lane.getCurrentThrower().getNumBowls();

        HashMap<Bowler, int[]> BowlerScores = lane.getScores();

        int[] scores = BowlerScores.get(lane.getCurrentThrower());

        if (CurrentRollNum % 2 != 0){
            // switches the state to a second roll state
            lane.setCurrentState(new SecondNormalState());
        }
        else {
            if (scores[CurrentRollNum - 2] + scores[CurrentRollNum - 1] == 10){
                //switches the state to a first roll state where there is a spare last roll
                lane.setCurrentState(new FirstSpareState());
            }
            else if (scores[CurrentRollNum -1] == 10){
                // switches to a state that includes a strike as its last roll
                lane.setCurrentState(new FirstStrikeState());
            }
            else if (scores[CurrentRollNum - 2] == 10){
                lane.setCurrentState(new FirstTwoStrikesState());
            }

            else {
                // TODO change gui scores accordingly, this is the functionality that this class is supposed to reach
            }

            //TODO flesh this out more
        }

        return 0;
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int current) {
        return new int[0];
    }

}
