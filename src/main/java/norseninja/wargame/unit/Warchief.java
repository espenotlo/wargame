package norseninja.wargame.unit;

import norseninja.wargame.Battlefield;
import norseninja.wargame.Location;
import norseninja.wargame.tempeffect.TempEffect;
import norseninja.wargame.tempeffect.TempEffectFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Warchief extends CommanderUnit {
    private int mana;
    private final int maxMana;

    public Warchief(String name, int health, Battlefield battlefield, Location location) {
        super(name, health, 25, 10, battlefield, location);
        this.mana = 5;
        this.maxMana = 5;
    }

    public boolean spendMana(int mana) {
        if (this.mana - mana >= 0) {
            this.mana -= mana;
            return true;
        }
        return false;
    }

    public void regenMana() {
        if (mana < maxMana) {
            mana++;
        }
    }

    @Override
    public void enableAura() {
        List<TempEffect> thisAura = getField().getTempEffectsBySource(this)
                .stream()
                .filter(effect -> effect.getName().equals("Bloodlust"))
                .collect(Collectors.toList());
        if (thisAura.isEmpty() && spendMana(5)) {
            TempEffect t = TempEffectFactory.getTempEffect(
                    TempEffectFactory
                            .TempEffectType.BLOODLUST, this);
            getField().addTempEffect(t);
        }
    }

    @Override
    public void act() {
        super.act();
        regenMana();
    }
}
