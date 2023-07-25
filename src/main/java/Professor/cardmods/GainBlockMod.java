package Professor.cardmods;

import Professor.MainModfile;
import Professor.cards.cardvars.DynvarInterfaceManager;
import Professor.util.TextureScaler;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GainBlockMod extends AbstractInfusion {
    public static final String ID = MainModfile.makeID(GainBlockMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    public static final Texture icon = TextureScaler.rescale(AbstractPower.atlas.findRegion("128/channel"), 64, 64);

    static {
        DynvarInterfaceManager.registerDynvarCarrier(ID);
    }

    public GainBlockMod(int baseAmount) {
        super(ID, InfusionType.BLOCK, baseAmount, TEXT[0], icon);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.att(new GainBlockAction(Wiz.adp(), Wiz.adp(), val));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainBlockMod(baseVal);
    }
}
