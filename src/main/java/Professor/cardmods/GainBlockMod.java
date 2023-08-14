package Professor.cardmods;

import Professor.MainModfile;
import Professor.cards.cardvars.DynvarInterfaceManager;
import Professor.relics.LocketOfDevotion;
import Professor.relics.MemoriaBracelet;
import Professor.util.CalcHelper;
import Professor.util.TextureScaler;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class GainBlockMod extends AbstractInfusion {
    public static final String ID = MainModfile.makeID(GainBlockMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    public static final Texture ICON = TextureScaler.rescale(AbstractPower.atlas.findRegion("128/channel"), 64, 64);
    private int relicStatsVal;

    static {
        DynvarInterfaceManager.registerDynvarCarrier(ID);
    }

    public GainBlockMod(int baseAmount) {
        super(ID, InfusionType.BLOCK, baseAmount, TEXT[0], ICON);
        priority = -1;
    }

    public GainBlockMod(int baseAmount, int relicStatsVal) {
        this(baseAmount);
        this.relicStatsVal = relicStatsVal;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, key);
        if (!mods.isEmpty()) {
            GainBlockMod mod = (GainBlockMod) mods.get(0);
            mod.relicStatsVal += this.relicStatsVal;
            mod.baseVal += this.baseVal;
            mod.val = mod.baseVal;
            return false;
        }
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        Wiz.att(new GainBlockAction(Wiz.adp(), Wiz.adp(), val));
        if (relicStatsVal > 0) {
            //If we only have the Relic version, pass 100% of the value. Without it, we wouldn't have gained from Dex or Braced
            if (relicStatsVal == baseVal) {
                MemoriaBracelet.onBlockModTrigger(CalcHelper.applyPowersToBlock(val));
                LocketOfDevotion.onBlockModTrigger(CalcHelper.applyPowersToBlock(val));
            } else {
                //Else calculate how much it actually increased the block
                int delta = val - CalcHelper.applyPowersToBlock(baseVal - relicStatsVal);
                MemoriaBracelet.onBlockModTrigger(CalcHelper.applyPowersToBlock(delta));
                LocketOfDevotion.onBlockModTrigger(CalcHelper.applyPowersToBlock(delta));
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainBlockMod(baseVal, relicStatsVal);
    }
}
