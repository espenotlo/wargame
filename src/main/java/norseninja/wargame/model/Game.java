package norseninja.wargame.model;

import norseninja.wargame.model.unit.Unit;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Game {
    private BufferedReader input;
    private BufferedWriter output;

    private Random r;
    private Battlefield field;
    private Army playerArmy;
    private Army aiArmy;
    private List<Unit> combatants;
    private Player player;

    public Game() {
        input = new BufferedReader(new InputStreamReader(System.in));
        output = new BufferedWriter(new OutputStreamWriter(System.out));

        r = Randomizer.getRandom();
        field = new Battlefield(10,10);
        playerArmy = new Army("Player", r, field, new Location(0,0));
        aiArmy = new Army("AI", r, field, new Location(field.getDepth()-1,field.getWidth()-1));

        playerArmy.muster(3);
        aiArmy.muster(3);

        combatants = new ArrayList<>();
        combatants.addAll(playerArmy.getUnits());
        combatants.addAll(aiArmy.getUnits());

        player = new Player(this);
        try {
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() throws IOException {
        int round = 0;
        while (playerArmy.hasUnits() && aiArmy.hasUnits()) {
            //new round
            round++;
            output.write("-------------------------Round " + round + "-------------------------\n");

            //Tick the duration of all temporary battlefield effects.
            field.tickTempEffects();

            //Roll initiative for all combatants.
            combatants.forEach(Unit::rollInitiative);
            combatants.sort(Comparator.comparing(Unit::getInitiative));

            //Make all combatants act. AI combatants act according to their programming,
            //while the player units are controlled by the player.
            for (Unit actingUnit : combatants) {
                if (actingUnit.getHealth() > 0) {
                    if (actingUnit.getArmy() == playerArmy) {
                        player.takeTurn(actingUnit);
                    } else {
                        actingUnit.act();
                    }
                }
            }
        }
        if (playerArmy.hasUnits()) {
            output.write("You win!");
        } else {
            output.write("You lose..");
        }
        output.flush();
    }



    public Army getPlayerArmy() {
        return playerArmy;
    }

    public Army getAiArmy() {
        return aiArmy;
    }

    public Battlefield getField() {
        return field;
    }

    public BufferedWriter getOutput() {
        return output;
    }

    public BufferedReader getInput() {
        return input;
    }
}
