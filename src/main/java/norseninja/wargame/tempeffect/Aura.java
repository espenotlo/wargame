package norseninja.wargame.tempeffect;

import norseninja.wargame.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class representing an aura effect, traversing the battlefield with its unit source of origin, affecting the units within range.
 */
public class Aura extends TempEffect {
    private final int range;
    private final BuffType buffType;

    /**
     * The type of effect; positive (buff) or negative (debuff).
     * In effect; whether it affects hostile or friendly units within range.
     */
    public enum BuffType {
        BUFF, DEBUFF
    }

    public Aura(String name, Unit source, Map<EffectType, Integer> effects, int range, BuffType buffType) {
        super(name, 999, source, new ArrayList<>(), effects, true);
        this.range = range;
        this.buffType = buffType;
        updateTargets();
    }


    @Override
    public boolean tick() {
        if (super.tick()) {
            updateTargets();
        }
        return isActive();
    }

    /**
     * @return The type of aura (buff or debuff).
     */
    public BuffType getBuffType() {
        return buffType;
    }

    /**
     * Updates the list of targets of the aura.
     * This method is called as the unit source of origin moves around the battlefield.
     */
    private void updateTargets() {
        List<Unit> targets = getSource()
                .getField()
                .getUnitsWithinRange(getSource().getLocation(), range);

        if (getBuffType() == BuffType.BUFF) {
            targets = targets
                    .stream()
                    .filter(u -> u.getArmy() == getSource().getArmy())
                    .collect(Collectors.toList());
            //Include caster in the aura
            targets.add(getSource());
        } else {
            targets = targets
                    .stream()
                    .filter(u -> u.getArmy() != getSource().getArmy())
                    .collect(Collectors.toList());
        }

        //Remove current targets
        while (!getTargets().isEmpty()) {
            removeTarget(getTargets().get(0));
        }

        //Add every unit in the potential target list to the target list.
        targets.forEach(this::addTarget);
    }
}
