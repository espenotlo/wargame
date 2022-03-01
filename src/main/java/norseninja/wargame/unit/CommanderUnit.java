package norseninja.wargame.unit;

import norseninja.wargame.Field;
import norseninja.wargame.Location;
import norseninja.wargame.tempeffect.Aura;
import norseninja.wargame.tempeffect.Buff;
import norseninja.wargame.tempeffect.TempEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommanderUnit extends CavalryUnit {

    public CommanderUnit(String name, int health, int attack, int armor, Field field, Location location) {
        super(name, health, attack, armor, field, location);
    }

    CommanderUnit(String name, int health, Field field, Location location) {
        super(name, health, 25, 15, field, location);
    }

    private void devotionAura() {
        List<TempEffect> dAura = getField().getTempEffectsBySource(this).stream().filter(effect -> effect.getName().equals("Devotion Aura")).collect(Collectors.toList());
        if (!dAura.isEmpty()) {
            dAura.forEach(effect -> getField().removeTempEffect(effect));
        }
        Map<TempEffect.Effect,Integer> effects = new HashMap<>();
        effects.put(TempEffect.Effect.RESISTBONUS, 5);
        getField().addTempEffect(new Aura("Devotion Aura",this,effects, 5,Buff.buffType.BUFF));
    }
}
