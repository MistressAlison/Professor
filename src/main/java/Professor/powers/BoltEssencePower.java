package Professor.powers;

import Professor.MainModfile;
import Professor.cards.LightningEssence;
import Professor.powers.interfaces.InfusionBoostingPower;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BoltEssencePower extends AbstractPower implements InfusionBoostingPower {
    public static final String POWER_ID = MainModfile.makeID(BoltEssencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BoltEssencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, LightningEssence.class.getSimpleName());
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard c) {
        if (type == DamageInfo.DamageType.NORMAL && c.type == AbstractCard.CardType.ATTACK) {
            if (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1)) {
                damage += amount;
            }
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int damageBoost(AbstractCard card) {
        if (card.costForTurn == 0 || (card.freeToPlayOnce && card.cost != -1)) {
            return amount;
        }
        return 0;
    }

    @Override
    public int blockBoost(AbstractCard card) {
        return 0;
    }

    @Override
    public int magicBoost(AbstractCard card) {
        return 0;
    }
}