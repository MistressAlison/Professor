package Professor.powers;

import Professor.MainModfile;
import Professor.cards.WindEssence;
import Professor.patches.CardCounterPatches;
import Professor.powers.interfaces.DrawManipPower;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WindEssencePower extends AbstractPower implements DrawManipPower {

    public static final String POWER_ID = MainModfile.makeID(WindEssencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean activated;

    public WindEssencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, WindEssence.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        activated = false;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    /*@Override
    public void onCardDraw(AbstractCard card) {
        if (CardCounterPatches.cardsDrawnThisTurn.size() == 6) {
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }*/

    @Override
    public int changeAmount(int currentDraw) {
        if (!activated) {
            flash();
            activated = true;
            currentDraw += amount;
        }
        return currentDraw;
    }
}