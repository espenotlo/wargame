package norseninja.wargame;

public class CommanderUnit extends CavalryUnit {

    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    CommanderUnit(String name, int health) {
        super(name, health, 25, 15);
    }
}
