package Professor.powers;

import Professor.MainModfile;
import Professor.cards.BlackWater;
import Professor.util.PowerIconMaker;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class BlackWaterPower extends AbstractPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = MainModfile.makeID(BlackWaterPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlackWaterPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, BlackWater.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        }
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (source == owner && target != owner && (power instanceof WeakPower || power instanceof VulnerablePower)) {
            flash();
            addToBot(new ApplyPowerAction(target, owner, new DestabilizedPower(target, owner, amount)));
        }
        return true;
    }

    /*@Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (source == owner && target != owner && power.type == PowerType.DEBUFF && !LoopField.looping.get(power)) {
            flash();
            AbstractPower p =  new DestabilizedPower(target, owner, amount);
            LoopField.looping.set(p, true);
            addToBot(new ApplyPowerAction(target, owner, p));
        }
        return true;
    }

    @SpirePatch2(clz = AbstractPower.class, method = SpirePatch.CLASS)
    public static class LoopField {
        public static SpireField<Boolean> looping = new SpireField<>(() -> false);
    }*/
}