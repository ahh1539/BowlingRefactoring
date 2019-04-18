import javax.swing.plaf.nimbus.State;

import java.util.HashMap;

public class RegularState implements ScoreState {

    ScoreState[] prevState;

    @Override
    public int calculateScore(Lane lane) {

        int total = 0;

        int currScore = lane.getCurrentBowlsScore();
        int ball = lane.getBall();
        Bowler b = lane.getCurrentThrower();
        HashMap<Bowler,int[]> scores = lane.getScores();

        //this is the second ball thrown of the frame, takes the last rolls score and adds it with the current one
        if (ball == 1){
            total = scores.get(b)[b.getNumBowls()-1] + currScore;
        }

        return total;
    }

}
