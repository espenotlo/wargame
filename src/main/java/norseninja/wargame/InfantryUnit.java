package norseninja.wargame;

public class InfantryUnit extends Unit {

    public InfantryUnit (String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    public InfantryUnit (String name, int health) {
        super(name, health, 15, 10);
    }

    @Override
    public int getAttackBonus() {
        return 2;
    }

    @Override
    public int getResistBonus() {
        return 1;
    }
}
