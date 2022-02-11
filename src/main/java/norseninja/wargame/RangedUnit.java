package norseninja.wargame;

public class RangedUnit extends Unit {
    private int timesAttacked;

    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
        this.timesAttacked = 0;
    }

    public RangedUnit (String name, int health) {
        super(name, health, 15, 8);
        this.timesAttacked = 0;
    }

    @Override
    public int getAttackBonus() {
        return 3;
    }

    @Override
    public int getResistBonus() {
        return Math.max(6-(2*timesAttacked), 2);
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
        timesAttacked ++;
    }
}
