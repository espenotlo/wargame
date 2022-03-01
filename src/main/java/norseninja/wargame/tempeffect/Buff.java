package norseninja.wargame.tempeffect;

import norseninja.wargame.unit.Unit;

import java.util.List;
import java.util.Map;

public class Buff extends TempEffect {
    private int range;
    private buffType buffType;

    public enum buffType {
        BUFF, DEBUFF
    }

    public Buff(String name, int duration, Unit source, List<Unit> targets, Map<Effect, Integer> effects, int range, buffType buffType) {
        super(name, duration, source, targets, effects);
        this.range = range;
        this.buffType = buffType;
    }

    public int getRange() {
        return range;
    }

    public buffType getBuffType() {
        return buffType;
    }
}
