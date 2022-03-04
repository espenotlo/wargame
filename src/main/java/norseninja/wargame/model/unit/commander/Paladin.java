package norseninja.wargame.model.unit.commander;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;
import norseninja.wargame.model.tempeffect.TempEffect;
import norseninja.wargame.model.tempeffect.TempEffectFactory;

public class Paladin extends CommanderUnit {

    public Paladin(String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 15, 15, battlefield, location);
    }

    @Override
    protected void setAbility(TempEffect ability) {
        super.setAbility(TempEffectFactory
                .getTempEffect(
                        TempEffectFactory.TempEffectType.DEVOTION_AURA, this));
    }
}
