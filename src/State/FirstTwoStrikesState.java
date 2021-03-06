package State;

import main.Lane;

/**
 * this is the FIRST ROLL this frame
 * the last roll was NOT a spare
 * last roll WAS a strike
 * two rolls ago WAS a strike
 * @author Alex Hurley & Michael Dolan
 */
public class FirstTwoStrikesState extends ScoreState {

    public FirstTwoStrikesState(Lane lane) {
        super(lane);
    }


    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores) {

        calculatedScores[(index/2) - 2] += currentBowlerScores[index];
        //add to 2 frames ago

        calculatedScores[(index/2) - 1] +=  2 * currentBowlerScores[index];
        //add to 1 frame ago

        calculatedScores[index/2] +=  currentBowlerScores[index] + calculatedScores[(index/2) - 1];
        // add to current frame

        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new SecondAfterStrikeDoNothing(lane, new FirstTwoStrikesState(lane)));
        }
        else {
            lane.setCurrentState(new SecondStrikeState(lane));
        }

        return calculatedScores;
    }
}
