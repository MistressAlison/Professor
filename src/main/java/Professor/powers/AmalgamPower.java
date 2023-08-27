package Professor.powers;

import Professor.MainModfile;
import Professor.actions.SpectrumizeAction;
import Professor.cards.Amalgam;
import Professor.util.PowerIconMaker;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AmalgamPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(AmalgamPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = true;

    public AmalgamPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, Amalgam.class.getSimpleName());
        updateDescription();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (justApplied) {
            justApplied = false;
        } else {
            flash();
            addToBot(new SpectrumizeAction(card));
            addToBot(new ReducePowerAction(owner, owner, this, 1));
            if (card.type != AbstractCard.CardType.POWER) {
                if (!PurgeField.purge.get(card)) {
                    PurgeField.purge.set(card, true);
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            PurgeField.purge.set(card, false);
                            this.isDone = true;
                        }
                    });
                }
            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}