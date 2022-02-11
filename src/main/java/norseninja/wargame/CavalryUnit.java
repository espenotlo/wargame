package norseninja.wargame;

public class CavalryUnit extends Unit {
    private boolean firstStrike = true;

    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    public CavalryUnit (String name, int health) {
        super(name, health, 20, 12);
    }

    @Override
    public int getAttackBonus() {
        if (firstStrike) {
            firstStrike = false;
            return 6;
        } else {
            return 2;
        }
    }

    @Override
    public int getResistBonus() {
        return 1;
    }
}
