package norseninja.wargame.tempeffect;

import norseninja.wargame.unit.Unit;

import java.util.List;
import java.util.Map;

/**
 * A class representing a buff or debuff with a duration (number of rounds), affecting a number of targets.
 */
public class Buff extends TempEffect {

    public Buff(String name, int duration, Unit source, List<Unit> targets, Map<EffectType, Integer> effects) {
        super(name, duration, source, targets, effects);
    }
}
