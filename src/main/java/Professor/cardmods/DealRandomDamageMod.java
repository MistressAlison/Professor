package Professor.cardmods;

import CardAugments.util.CalcHelper;
import Professor.MainModfile;
import Professor.cards.cardvars.DynvarInterfaceManager;
import Professor.util.FormatHelper;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DealRandomDamageMod extends AbstractCardModifier implements DynvarInterface {
    public static final String ID = MainModfile.makeID(DealRandomDamageMod.class.getSimpleName());
    public static final String DESCRIPTION_KEY = "!"+ID+"!";
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int val;
    public int baseVal;
    public boolean valModified;
    public boolean valUpgraded;

    static {
        DynvarInterfaceManager.registerDynvarCarrier(ID);
    }

    public DealRandomDamageMod(int baseAmount) {
        this.baseVal = this.val = baseAmount;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractMonster mon = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (mon!= null) {
                    Wiz.att(new DamageAction(target, new DamageInfo(Wiz.adp(), CalcHelper.calculateCardDamage(baseVal, mon), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void onApplyPowers(AbstractCard card) {
        val = CalcHelper.applyPowers(baseVal);
        valModified = val != baseVal;
    }

    @Override
    public void onCalculateCardDamage(AbstractCard card, AbstractMonster mo) {
        val = CalcHelper.calculateCardDamage(baseVal, mo);
        valModified = val != baseVal;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return FormatHelper.insertAfterText(rawDescription , String.format(TEXT[0], DESCRIPTION_KEY));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, ID);
        if (!mods.isEmpty()) {
            DealRandomDamageMod mod = (DealRandomDamageMod) mods.get(0);
            mod.baseVal += this.baseVal;
            return false;
        }
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealRandomDamageMod(baseVal);
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
