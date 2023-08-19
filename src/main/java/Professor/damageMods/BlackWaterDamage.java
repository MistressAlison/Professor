package Professor.damageMods;

import Professor.powers.UnstablePower;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BlackWaterDamage extends AbstractDamageModifier {
    int amount;
    public BlackWaterDamage(int amount) {
        this.amount = amount;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target != null && target.hasPower(UnstablePower.POWER_ID)) {
            damage *= (amount)/100f;
        }
        return damage;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BlackWaterDamage(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
