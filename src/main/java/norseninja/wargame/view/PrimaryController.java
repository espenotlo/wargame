package norseninja.wargame.view;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import norseninja.wargame.model.Army;
import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;
import norseninja.wargame.model.Randomizer;

import java.util.Random;

public class PrimaryController {
    private ObservableListWrapper unitWrapper;
    private ObservableListWrapper targetWrapper;
    private ObservableListWrapper fieldWrapper;

    private Battlefield field;
    private Army playerArmy;
    private Army aiArmy;
    private Random r;

    public PrimaryController() {
        r = Randomizer.getRandom();
        field = new Battlefield(30,30);
        playerArmy = new Army("Player", r, field, new Location(0,0));
        aiArmy = new Army("AI", r, field, new Location(29,29));

        playerArmy.muster(10);
        aiArmy.muster(10);
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void updateListWrappers() {

    }

    @FXML
    private void setListWrappers() {

    }
}
