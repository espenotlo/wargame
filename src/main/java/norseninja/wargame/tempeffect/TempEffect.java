package norseninja.wargame.tempeffect;

import norseninja.wargame.unit.Unit;

import java.util.List;
import java.util.Map;

/**
 * Abstract class representing a temporary effect on the battlefield.
 */
public abstract class TempEffect {
    private final String name;
    private int remainingDuration;
    private boolean active;
    private final List<Unit> targets;
    private final Unit source;
    private final Map<EffectType,Integer> effects;
    private final boolean concentration;

    /**
     * What the temporary effect affects.
     */
    public enum EffectType {
        HEALTH, ATTACK, ARMOR, ATTACKBONUS, RESISTBONUS, INITIATIVEBONUS
    }

    /**
     * Creates a new instance of the class.
     * @param name The name of the effect.
     * @param duration The duration (number of turns) the effect lasts.
     * @param source The unit from which the effect originates.
     * @param targets the targets affected by the effect.
     * @param effects the effects of the temporary effect.
     */
    protected TempEffect(String name, int duration, Unit source, List<Unit> targets, Map<EffectType,Integer> effects) {
        this.name = name;
        this.remainingDuration = duration;
        this.source = source;
        this.targets = targets;
        this.effects = effects;
        concentration = false;
        setActive();
    }

    /**
     * Creates a new instance of the class.
     * @param name The name of the effect.
     * @param duration The duration (number of turns) the effect lasts.
     * @param source The unit from which the effect originates.
     * @param targets the targets affected by the effect.
     * @param effects the effects of the temporary effect.
     * @param concentration whether the effect should dissipate when the unit it originated from dies.
     */
    protected TempEffect(String name, int duration, Unit source, List<Unit> targets, Map<EffectType,Integer> effects, boolean concentration) {
        this.name = name;
        this.remainingDuration = duration;
        this.source = source;
        this.targets = targets;
        this.effects = effects;
        this.concentration = concentration;
        setActive();
    }

    /**
     * @return {@code String} name of the effect.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The {@code Unit} from which the effect originated.
     */
    public Unit getSource() {
        return source;
    }

    /**
     * @return A {@code List<Unit>} containing the targets of the effect.
     */
    public List<Unit> getTargets() {
        return targets;
    }

    /**
     * @return {@code true} if the effect is active, or {@code false} if it has expired.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return {@code true} if the effect should dissipate when the source unit dies; {@code false} if not.
     */
    public boolean isConcentration() {
        return concentration;
    }

    /**
     * Sets the effect as active if it wasn't already, and applies its effects to all of its targets.
     */
    public void setActive() {
        if (!active && getSource().getHealth() > 0) {
            active = true;
            effects.forEach(this::activateEffect);
        }
    }

    /**
     * Sets the effect as inactive, if it was active; and removes its effects from all of its targets.
     */
    public void setInactive() {
        if (active) {
            active = false;
            effects.forEach(this::deActivateEffect);
        }
    }

    /**
     * Adds a unit to the temporary effect's targets, and applies all of this temporary effect's effects to the new target.
     * @param unit the target unit to be added.
     */
    public void addTarget(Unit unit) {
        if (active) {
            effects.forEach((k,v) -> activateEffect(k,v,unit));
        }
        targets.add(unit);
    }

    /**
     * Removes a unit from the temporary effect's targets, and removes all of this temporary effect's effects from it.
     * @param unit the target unit to be added.
     */
    public void removeTarget(Unit unit) {
        if (active) {
            effects.forEach((k,v) -> deActivateEffect(k,v,unit));
        }
        targets.remove(unit);
    }

    /**
     * Applies given effect on all of the units in the list of targets.
     * @param effectType which attribute the effect affects.
     * @param value the degree of the effect.
     */
    private void activateEffect(EffectType effectType, int value) {
        switch (effectType) {
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

    /**
     * Applies given effect on given unit.
     * @param effectType which attribute the effect affects.
     * @param value the degree of the effect.
     * @param unit the unit to be affected.
     */
    private void activateEffect(EffectType effectType, int value, Unit unit) {
        switch (effectType) {
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

    /**
     * Removes given effect from all units in the list of targets.
     * @param effectType which attribute the effect affects.
     * @param value the degree of the effect.
     */
    private void deActivateEffect(EffectType effectType, int value) {
        switch (effectType) {
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
    /**
     * Removes given effect from given unit.
     * @param effectType which attribute the effect affects.
     * @param value the degree of the effect.
     * @param unit the unit to be restored.
     */
    private void deActivateEffect(EffectType effectType, int value, Unit unit) {
        switch (effectType) {
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

    /**
     * This method is called to signify that a round in the battle has passed.
     * If the temporary effect has reached the end of its duration, it is disabled.
     * @return {@code true} if the effect still has remaining duration, or {@code false} if the effect has expired.
     */
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
