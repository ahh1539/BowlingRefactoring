package State;

import main.Lane;

public class ThirdFrameFirstSpareState extends ScoreState {
    public ThirdFrameFirstSpareState(Lane lane) {
        super(lane);
    }

    @Override
    public int[] calculateScore(int index, int[] currentBowlerScores, int[] calculatedScores, int current) {
        calculatedScores[9] += currentBowlerScores[index];
        //add to the previous total

        calculatedScores[10] = calculatedScores[9] + currentBowlerScores[index];
        //add this score to the running total

        return calculatedScores;
    }
}
