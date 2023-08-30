package Professor.powers;

import Professor.MainModfile;
import Professor.cutStuff.cards.DragonBlood;
import Professor.powers.interfaces.InfusionBoostingPower;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DragonBloodPower extends AbstractPower implements InfusionBoostingPower {
    public static final String POWER_ID = MainModfile.makeID(DragonBloodPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DragonBloodPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, DragonBlood.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        /*if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }*/
    }

    /*@Override
    public int increaseTimes(AbstractCard c, AbstractCardModifier mod) {
        return amount;
    }*/

    @Override
    public int damageBoost(AbstractCard card) {
        return amount;
    }

    @Override
    public int blockBoost(AbstractCard card) {
        return amount;
    }

    @Override
    public int magicBoost(AbstractCard card) {
        return 0;
    }
}