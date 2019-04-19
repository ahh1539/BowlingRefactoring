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

    public FirstStrikeState(Lane lane) {
        super(lane);
    }


    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {

        calculatedScores[(index/2) - 1] += currentBowlerScores[index];
        // adds current bowl score to the previous frame

        calculatedScores[(index/2)] +=  currentBowlerScores[index] + calculatedScores[(index/2) - 1];
        // adds current bowl score to the current frame and adds the running total



        if (currentBowlerScores[index] == 10){
            // checks if the current bowl is a strike if so then state is changed to two strikes in a row state
            // also adds an extra ball to make up for the skipped ball
            lane.setCurrentState(new SecondAfterStrikeDoNothing(lane, new FirstTwoStrikesState(lane)));
        }else {
            // if the current roll is not a strike it sends it to the state where strike 2 rolls ago
            lane.setCurrentState(new SecondStrikeState(lane));
        }
        return calculatedScores;
    }
}
