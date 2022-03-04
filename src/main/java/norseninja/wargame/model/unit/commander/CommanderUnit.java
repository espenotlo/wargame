package norseninja.wargame.model.unit.commander;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;
import norseninja.wargame.model.tempeffect.TempEffect;
import norseninja.wargame.model.unit.CavalryUnit;

public abstract class CommanderUnit extends CavalryUnit {
    private TempEffect ability;

    protected CommanderUnit(String name, int health, int attack, int armor, Battlefield battlefield, Location location) {
        super(name, health, attack, armor, battlefield, location);
    }

    @Override
    public void act() {
        enableAura();
        super.act();
    }

    public TempEffect getAbility() {
        return this.ability;
    }

    protected void setAbility(TempEffect ability) {
        this.ability = ability;
    }

    public boolean enableAura() {
        if (null == getAbility()) {
            setAbility(null);
            getField().addTempEffect(getAbility());
            return true;
        }
        return false;
    }
}
