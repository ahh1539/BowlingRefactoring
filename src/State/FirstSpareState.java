package State;

import main.Lane;

/**
 * This is the FIRST ROLL of the frame
 * the last ball rolled WAS a spare and NOT a strike
 * two rolls ago was NOT a strike
 * @author Alex Hurley
 */

public class FirstSpareState implements ScoreState {

    private Lane lane;

    public FirstSpareState(Lane lane) {
        this.lane = lane;
    }

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
     *      When: if this ball is a strike (greater than 10)
     *          To: FirstStrike
     *      When: if this ball is less than 10
     *          To: normal Second
     * @return
     */
    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[index/2] += currentBowlerScores[index];
        calculatedScores[(index/2) - 1] += currentBowlerScores[index];

        if (currentBowlerScores[index] == 10){
            lane.setCurrentState(new FirstStrikeState());
        }else{
            lane.setCurrentState(new SecondNormalState());
        }

        return calculatedScores;
    }
}
