package Professor.powers;

import Professor.MainModfile;
import Professor.cards.MistLiquid;
import Professor.util.PowerIconMaker;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MistLiquidPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(MistLiquidPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MistLiquidPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, MistLiquid.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (!Wiz.adp().hand.isEmpty()) {
            this.flash();
            addToTop(new GainBlockAction(owner, owner, MistLiquidPower.this.amount * Wiz.adp().hand.size()));
        }
    }
}