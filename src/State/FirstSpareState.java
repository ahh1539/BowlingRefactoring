package State;

import main.Lane;

/**
 * This is the FIRST ROLL of the frame
 * the last ball rolled WAS a spare and NOT a strike
 * two rolls ago was NOT a strike
 * @author Alex Hurley & michael dolan
 */

public class FirstSpareState extends ScoreState {

    public FirstSpareState(Lane lane) {
        super(lane);
    }


    /**
     *
     * @param index the current index of balls
     * @param currentBowlerScores the scores of each ball by this bowler
     * @param calculatedScores the frame scores of the current bowler
     * @param current
     *
     * Changes State:
     *      When: if this ball is a strike (greater than 10)
     *          To: FirstStrike
     *      When: if this ball is less than 10
     *          To: normal Second
     * @return the current bowler's scores per frame
     */
    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {

        calculatedScores[(index/2) - 1] += currentBowlerScores[index];
        // previous frame adds the current bowls score because the previous bowl was a spare

        // index/2 is the current frame
        calculatedScores[index/2] += currentBowlerScores[index] + calculatedScores[(index/2) - 1];
        //current frame total adds the score received and the score of the previous frame



        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new SecondAfterStrikeDoNothing(lane, new FirstStrikeState(lane)));
            //strike scored
        }else{
            lane.setCurrentState(new SecondNormalState(lane));
        }

        return calculatedScores;
    }
}
