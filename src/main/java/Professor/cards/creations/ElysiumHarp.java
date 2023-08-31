package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ScrapeFollowUpAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class ElysiumHarp extends AbstractCreationCard {
    public final static String ID = makeID(ElysiumHarp.class.getSimpleName());

    public ElysiumHarp() {
        this(null);
    }

    public ElysiumHarp(ElementData data) {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        updateElementData(data);
        addCustomKeyword(KeywordManager.ELYSIUM_HARP);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseBlock = block = 7;
        baseMagicNumber = magicNumber = 4;
        //baseSecondMagic = secondMagic = 1;
        if (data != null) {
            baseBlock += 3*data.g;
            block = baseBlock;
            //baseMagicNumber += data.g;
            //magicNumber = baseMagicNumber;
            //baseSecondMagic += data.g;
            //secondMagic = baseSecondMagic;
        }
        //Worst: 7 Block, 4 draw
        //Best: 12 Block, 4 draw
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new ElysiumHarp(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("TINGSHA"));
        addToBot(new VFXAction(Wiz.adp(), new ShockWaveEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY, Color.GOLD.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        addToBot(new GainBlockAction(Wiz.adp(), block));
        addToBot(new DrawCardAction(this.magicNumber, new ScrapeFollowUpAction()));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
        //upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(PURPLE), WHITE, GOLD, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return ElysiumHarp.class.getSimpleName();
    }
}