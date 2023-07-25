package Professor.cardmods;

import Professor.MainModfile;
import Professor.cards.cardvars.DynvarInterfaceManager;
import Professor.util.TextureScaler;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DealDamageMod extends AbstractInfusion {
    public static final String ID = MainModfile.makeID(DealDamageMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    public static final Texture icon = TextureScaler.rescale(AbstractPower.atlas.findRegion("128/pressure_points"), 64, 64);

    static {
        DynvarInterfaceManager.registerDynvarCarrier(ID);
    }

    public DealDamageMod(int baseAmount) {
        super(ID, InfusionType.DAMAGE_DIRECT, baseAmount, TEXT[0], icon);
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
        Wiz.atb(new DamageAction(target, new DamageInfo(Wiz.adp(), val, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (!usesVanillaTargeting(card)) {
            return false;
        }
        return super.shouldApply(card);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealDamageMod(baseVal);
    }
}
