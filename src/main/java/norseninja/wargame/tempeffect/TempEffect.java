package norseninja.wargame.tempeffect;

import norseninja.wargame.unit.Unit;

import java.util.List;
import java.util.Map;

public abstract class TempEffect {
    private final String name;
    private final int duration;
    private int remainingDuration;
    private boolean active;
    private final List<Unit> targets;
    private final Unit source;
    private final Map<Effect,Integer> effects;

    protected TempEffect(String name, int duration, Unit source, List<Unit> targets, Map<Effect,Integer> effects) {
        this.name = name;
        this.duration = duration;
        this.remainingDuration = duration;
        this.source = source;
        this.targets = targets;
        this.effects = effects;
        setActive();
    }

    public enum Effect {
        HEALTH, ATTACK, ARMOR, ATTACKBONUS, RESISTBONUS, INITIATIVEBONUS;
    }

    public String getName() {
        return name;
    }

    public Unit getSource() {
        return source;
    }

    public List<Unit> getTargets() {
        return targets;
    }

    public boolean isActive() {
        return active;
    }

    public int getDuration() {
        return duration;
    }

    public int getRemainingDuration() {
        return remainingDuration;
    }

    public void setActive() {
        if (!active) {
            active = true;
            effects.forEach(this::activateEffect);
        }
    }

    public void setInactive() {
        if (active) {
            active = false;
            effects.forEach(this::deActivateEffect);
        }
    }

    public void addTarget(Unit unit) {
        if (active) {
            effects.forEach((k,v) -> activateEffect(k,v,unit));
        }
        targets.add(unit);
    }

    public void removeTarget(Unit unit) {
        if (active) {
            effects.forEach((k,v) -> deActivateEffect(k,v,unit));
        }
        targets.remove(unit);
    }

    private void activateEffect(Effect effect, int value) {
        switch (effect) {
            case HEALTH: targets.forEach(unit -> unit.setHealth(unit.getHealth() + value));
            break;
            case ATTACK: targets.forEach(unit -> unit.setAttack(unit.getAttack() + value));
            break;
            case ARMOR: targets.forEach(unit -> unit.setArmor(unit.getArmor() + value));
            break;
            case ATTACKBONUS: targets.forEach(unit -> unit.setAttackBonus(unit.getAttackBonus() + value));
            break;
            case RESISTBONUS: targets.forEach(unit -> unit.setResistBonus(unit.getResistBonus() + value));
            break;
            case INITIATIVEBONUS: targets.forEach(unit -> unit.setInitiativeBonus(unit.getInitiativeBonus() + value));
            break;
            default: break;
        }
    }

    private void activateEffect(Effect effect, int value, Unit unit) {
        switch (effect) {
            case HEALTH: unit.setHealth(unit.getHealth() + value);
                break;
            case ATTACK: unit.setAttack(unit.getAttack() + value);
                break;
            case ARMOR: unit.setArmor(unit.getArmor() + value);
                break;
            case ATTACKBONUS: unit.setAttackBonus(unit.getAttackBonus() + value);
                break;
            case RESISTBONUS: unit.setResistBonus(unit.getResistBonus() + value);
                break;
            case INITIATIVEBONUS: unit.setInitiativeBonus(unit.getInitiativeBonus() + value);
                break;
            default: break;
        }
    }

    private void deActivateEffect(Effect effect, int value) {
        switch (effect) {
            case HEALTH: targets.forEach(unit -> unit.setHealth(unit.getHealth() - value));
                break;
            case ATTACK: targets.forEach(unit -> unit.setAttack(unit.getAttack() - value));
                break;
            case ARMOR: targets.forEach(unit -> unit.setArmor(unit.getArmor() - value));
                break;
            case ATTACKBONUS: targets.forEach(unit -> unit.setAttackBonus(unit.getAttackBonus() - value));
                break;
            case RESISTBONUS: targets.forEach(unit -> unit.setResistBonus(unit.getResistBonus() - value));
                break;
            case INITIATIVEBONUS: targets.forEach(unit -> unit.setInitiativeBonus(unit.getInitiativeBonus() - value));
                break;
            default: break;
        }
    }

    private void deActivateEffect(Effect effect, int value, Unit unit) {
        switch (effect) {
            case HEALTH: unit.setHealth(unit.getHealth() - value);
                break;
            case ATTACK: unit.setAttack(unit.getAttack() - value);
                break;
            case ARMOR: unit.setArmor(unit.getArmor() - value);
                break;
            case ATTACKBONUS: unit.setAttackBonus(unit.getAttackBonus() - value);
                break;
            case RESISTBONUS: unit.setResistBonus(unit.getResistBonus() - value);
                break;
            case INITIATIVEBONUS: unit.setInitiativeBonus(unit.getInitiativeBonus() - value);
                break;
            default: break;
        }
    }

    public boolean tick() {
        if (active) {
            remainingDuration--;
            if (remainingDuration <= 0) {
                setInactive();
            }
        }
        return active;
    }
}
