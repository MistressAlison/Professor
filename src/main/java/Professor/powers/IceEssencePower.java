package Professor.powers;

import Professor.MainModfile;
import Professor.cardmods.AbstractInfusion;
import Professor.cardmods.GainBlockMod;
import Professor.cards.IceEssence;
import Professor.powers.interfaces.InfusionTriggerPower;
import Professor.util.PowerIconMaker;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCreateBlockInstancePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import java.util.HashSet;

public class IceEssencePower extends AbstractPower implements OnCreateBlockInstancePower, InfusionTriggerPower {

    public static final String POWER_ID = MainModfile.makeID(IceEssencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IceEssencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, IceEssence.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onCreateBlockInstance(HashSet<AbstractBlockModifier> hashSet, Object o) {
        if (o instanceof AbstractCard) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new NextTurnBlockPower(owner, amount), amount, true));
        }
    }

    @Override
    public void infusionTrigger(AbstractInfusion i, int infusionAmount) {
        if (i instanceof GainBlockMod) {
            addToBot(new ApplyPowerAction(owner, owner, new NextTurnBlockPower(owner, amount), amount, true));
        }
    }

    /*@Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
            this.flash();
        }
    }*/
}