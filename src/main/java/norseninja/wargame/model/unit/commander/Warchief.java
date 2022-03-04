package norseninja.wargame.model.unit.commander;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;
import norseninja.wargame.model.tempeffect.TempEffect;
import norseninja.wargame.model.tempeffect.TempEffectFactory;

public class Warchief extends CommanderUnit {
    private int mana;
    private final int maxMana;

    public Warchief(String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 25, 10, battlefield, location);
        this.mana = 5;
        this.maxMana = 5;
    }

    public boolean spendMana(int mana) {
        if (this.mana - mana >= 0) {
            this.mana -= mana;
            return true;
        }
        return false;
    }

    public void regenMana() {
        if (mana < maxMana) {
            mana++;
        }
    }

    @Override
    protected void setAbility(TempEffect ability) {
        super.setAbility(TempEffectFactory
                .getTempEffect(
                        TempEffectFactory.TempEffectType.BLOODLUST, this));
    }

    @Override
    public boolean enableAura() {
        return super.enableAura() && spendMana(5);
    }

    @Override
    public void act() {
        super.act();
        regenMana();
    }
}
