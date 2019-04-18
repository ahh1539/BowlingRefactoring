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

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[index/2] += currentBowlerScores[index];


        return new int[0];
    }
}
