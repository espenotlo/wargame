package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;
import norseninja.wargame.tempeffect.TempEffect;
import norseninja.wargame.tempeffect.TempEffectFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DeathKnight extends CommanderUnit {

    public DeathKnight(String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 20, 12, battlefield, location);
    }

    @Override
    public void enableAura() {
        List<TempEffect> thisAura = getField().getTempEffectsBySource(this)
                .stream()
                .filter(effect -> effect.getName().equals("Weakness Aura"))
                .collect(Collectors.toList());
        if (thisAura.isEmpty()) {
            TempEffect t = TempEffectFactory.getTempEffect(
                    TempEffectFactory
                            .TempEffectType.WEAKNESS_AURA, this);
            getField().addTempEffect(t);
        }
    }
}
