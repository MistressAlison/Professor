package Professor.powers;

import Professor.MainModfile;
import Professor.cards.AmberOil;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AmberOilPower extends AbstractPower {

    public static final String POWER_ID = MainModfile.makeID(AmberOilPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AmberOilPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, AmberOil.class.getSimpleName());
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
            addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(AmberOilPower.this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));

        }
    }

    /*@Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.selfRetain || c.retain) {
                        addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(AmberOilPower.this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
                    }
                }
                this.isDone = true;
            }
        });
    }*/
}