package State;

import main.Lane;

/**
 * This is the FIRST ROLL of the frame
 * the last ball rolled WAS a spare and NOT a strike
 * two rolls ago was NOT a strike
 * @author Alex Hurley
 */

public class FirstSpareState implements ScoreState {


    @Override
    public int calculateScore(Lane lane) {
        return 0;
    }

    /**
     *
     * @param index the current index of balls
     * @param currentBowlerScores the scores of each ball by this bowler
     * @param calculatedScores the frame scores of the current bowler
     * @param current
     *
     * Changes State:
     *      When: this ball was a strike (greater than 10)
     *          To: FirstStrike
     *      When: this ball was less than 10
     *          To: normal Second
     * @return
     */
    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        // index/2 is the current frame
        calculatedScores[index/2] += currentBowlerScores[index];
        //current frame total adds the score received

        calculatedScores[(index/2) - 1] += currentBowlerScores[index];
        // previous frame adds the current bowls score because the previous bowl was a spare

        if (currentBowlerScores[index] == 10){
            //strike scored
            lane.setCurrentState(new FirstStrikeState());
        }else{
            lane.setCurrentState(new SecondNormalState());
        }

        return calculatedScores;
    }
}
