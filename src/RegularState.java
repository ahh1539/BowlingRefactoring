import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class RegularState implements ScoreState {

    State[] prevState;

    @Override
    public int calculateScore(Bowler currBowler, int frame, int ball) {

        return 0;
    }

}
