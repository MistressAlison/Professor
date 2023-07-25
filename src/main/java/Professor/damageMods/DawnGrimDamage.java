package Professor.damageMods;

import Professor.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.MinionPower;

public class DawnGrimDamage extends AbstractDamageModifier {
    int amount;

    public DawnGrimDamage(int amount) {
        this.amount = amount;
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (target.currentHealth > 0 && target.currentHealth - lastDamageTaken <= 0 && !target.halfDead && !target.hasPower(MinionPower.POWER_ID)) {
            Wiz.att(new HealAction(info.owner, info.owner, amount));
        }
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new DawnGrimDamage(amount);
    }
}
