package norseninja.wargame.model.tempeffect;

import norseninja.wargame.model.unit.Unit;

import java.util.*;


public class TempEffectFactory {
    public enum TempEffectType {
        DEVOTION_AURA, WEAKNESS_AURA, FRENZY, BLOODLUST
    }

    public static TempEffect getTempEffect(TempEffectType type, Unit source) {
        EnumMap<TempEffect.EffectType, Integer> effects = new EnumMap<>(TempEffect.EffectType.class);
        switch (type) {
            case DEVOTION_AURA -> {
                effects.put(TempEffect.EffectType.RESISTBONUS, 3);
                return new Aura("Devotion Aura", source, effects, 3, Aura.BuffType.BUFF);
            }
            case WEAKNESS_AURA -> {
                effects.put(TempEffect.EffectType.ATTACKBONUS, -6);
                return new Aura("Weakness Aura", source, effects, 3, Aura.BuffType.DEBUFF);
            }
            case FRENZY -> {
                effects.put(TempEffect.EffectType.ATTACKBONUS, 12);
                List<Unit> target = new ArrayList<>();
                target.add(source.getField().getFriendlyUnitsWithinRange(source, 5).get(0));
                return new Buff("Frenzy", 5, source, target, effects);
            }
            case BLOODLUST -> {
                effects.put(TempEffect.EffectType.ATTACKBONUS, 9);
                List<Unit> targets = source.getField()
                        .getFriendlyUnitsWithinRange(source, 5);
                return new Buff("Bloodlust", 3, source, targets, effects);
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
