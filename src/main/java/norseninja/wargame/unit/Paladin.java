package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;
import norseninja.wargame.tempeffect.TempEffect;
import norseninja.wargame.tempeffect.TempEffectFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Paladin extends CommanderUnit {

    public Paladin(String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 15, 15, battlefield, location);
    }

    @Override
    public void enableAura() {
        List<TempEffect> thisAura = getField().getTempEffectsBySource(this)
                .stream()
                .filter(effect -> effect.getName().equals("Devotion Aura"))
                .collect(Collectors.toList());
        if (thisAura.isEmpty()) {
            TempEffect t = TempEffectFactory.getTempEffect(
                    TempEffectFactory
                            .TempEffectType.DEVOTION_AURA, this);
            getField().addTempEffect(t);
        }
    }
}
