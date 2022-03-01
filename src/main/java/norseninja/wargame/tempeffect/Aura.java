package norseninja.wargame.tempeffect;

import norseninja.wargame.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aura extends Buff {

    public Aura(String name, Unit source, Map<Effect, Integer> effects, int range, Buff.buffType buffType) {
        super(name, 999, source, new ArrayList<>(), effects, range, buffType);
        updateTargets();
    }


    @Override
    public boolean tick() {
        if (isActive()) {
            updateTargets();
        }

        return isActive();
    }

    private void updateTargets() {
        List<Unit> targets = getSource()
                .getField()
                .getUnitsWithinRange(getSource().getLocation(), getRange());

        if (getBuffType() == Buff.buffType.BUFF) {
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

        //Add every unit in the potential target list to the target list.
        targets.forEach(target -> {
            if (!getTargets().contains(target)) {
                addTarget(target);
            }
        });
        List<Unit> finalTargets = targets;

        //Remove every unit not in the potential target list from the target list.
        getTargets().forEach(target -> {
            if (!finalTargets.contains(target)) {
                removeTarget(target);
            }
        });
    }
}
