package norseninja.wargame.model.unit.commander;

import norseninja.wargame.model.Battlefield;
import norseninja.wargame.model.Location;
import norseninja.wargame.model.tempeffect.TempEffect;
import norseninja.wargame.model.tempeffect.TempEffectFactory;

public class DeathKnight extends CommanderUnit {

    public DeathKnight(String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 20, 13, battlefield, location);
    }

    @Override
    protected void setAbility(TempEffect ability) {
        super.setAbility(TempEffectFactory
                .getTempEffect(
                        TempEffectFactory.TempEffectType.WEAKNESS_AURA, this));
    }
}