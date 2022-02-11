package norseninja.wargame;

public abstract class Unit {
    protected String name;
    protected int health;
    protected int attack;
    protected int armor;

    public Unit (String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    abstract int getAttackBonus();

    abstract int getResistBonus();

    public int attack(Unit opponent) {
        int postAttackHealth = Math.min(
                opponent.getHealth()
                        - attack
                        - this.getAttackBonus()
                        + opponent.getArmor()
                        + opponent.getResistBonus()
                , opponent.getHealth());
        opponent.setHealth(postAttackHealth);
        return postAttackHealth;
    }
}
