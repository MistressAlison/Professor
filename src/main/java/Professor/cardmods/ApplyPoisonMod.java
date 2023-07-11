package Professor.cardmods;

import CardAugments.util.CalcHelper;
import Professor.MainModfile;
import Professor.cards.cardvars.DynvarInterfaceManager;
import Professor.util.FormatHelper;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class ApplyPoisonMod extends AbstractCardModifier implements DynvarInterface {
    public static final String ID = MainModfile.makeID(ApplyPoisonMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int val;
    public int baseVal;
    public boolean valModified;
    public boolean valUpgraded;

    static {
        DynvarInterfaceManager.registerDynvarCarrier(ID);
    }

    public ApplyPoisonMod(int baseAmount) {
        this.baseVal = this.val = baseAmount;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card.target == AbstractCard.CardTarget.ALL_ENEMY || card.target == AbstractCard.CardTarget.NONE) {
            card.target = AbstractCard.CardTarget.ENEMY;
        }
        if (card.target == AbstractCard.CardTarget.SELF || card.target == AbstractCard.CardTarget.ALL) {
            card.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
        }
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new ApplyPowerAction(target, Wiz.adp(), new PoisonPower(target, Wiz.adp(), val)));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertAfterText(rawDescription , String.format(TEXT[0], DESCRIPTION_KEY));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (card.target != AbstractCard.CardTarget.ENEMY &&
                card.target != AbstractCard.CardTarget.ALL_ENEMY &&
                card.target != AbstractCard.CardTarget.SELF &&
                card.target != AbstractCard.CardTarget.NONE &&
                card.target != AbstractCard.CardTarget.SELF_AND_ENEMY &&
                card.target != AbstractCard.CardTarget.ALL) {
            return false;
        }
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, ID);
        if (!mods.isEmpty()) {
            ApplyPoisonMod mod = (ApplyPoisonMod) mods.get(0);
            mod.baseVal += this.baseVal;
            return false;
        }
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyPoisonMod(baseVal);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String key() {
        return ID;
    }

    @Override
    public int val(AbstractCard card) {
        return val;
    }

    @Override
    public int baseVal(AbstractCard card) {
        return baseVal;
    }

    @Override
    public boolean modified(AbstractCard card) {
        return valModified;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return valUpgraded;
    }
}
