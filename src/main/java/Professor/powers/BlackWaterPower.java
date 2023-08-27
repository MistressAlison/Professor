package Professor.powers;

import Professor.MainModfile;
import Professor.actions.DoIfPowerAppliedAction;
import Professor.cards.BlackWater;
import Professor.util.PowerIconMaker;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class BlackWaterPower extends AbstractPower implements BetterOnApplyPowerPower{
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

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (source == owner && target != owner && (power instanceof WeakPower || power instanceof VulnerablePower)) {
            flash();
            addToBot(new DoIfPowerAppliedAction(power, new ApplyPowerAction(target, owner, new UnstablePower(target, amount))));
        }
        return true;
    }
}