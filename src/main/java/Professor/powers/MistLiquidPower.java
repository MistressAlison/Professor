package Professor.powers;

import Professor.MainModfile;
import Professor.cards.MistLiquid;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    public void onCardDraw(AbstractCard card) {
        if (card.cost == -2 || card.costForTurn == -2) {
            flash();
            //addToBot(new GainBlockAction(owner, amount));
            addToBot(new ApplyPowerAction(owner, owner, new BracedPower(owner, amount)));
            /*Wiz.forAllMonstersLiving(m -> {
                Wiz.applyToEnemy(m, new WeakPower(m, amount, false));
            });*/
        }
    }
}