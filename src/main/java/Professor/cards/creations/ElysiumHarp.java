package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cutStuff.powers.ExposedPower;
import Professor.patches.EnterCardGroupPatches;
import Professor.powers.StaggerPower;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class ElysiumHarp extends AbstractCreationCard implements EnterCardGroupPatches.OnEnterCardGroupCard {
    public final static String ID = makeID(ElysiumHarp.class.getSimpleName());

    public ElysiumHarp() {
        this(null);
    }

    public ElysiumHarp(ElementData data) {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        updateElementData(data);
        addCustomKeyword(KeywordManager.ELYSIUM_HARP);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseMagicNumber = magicNumber = 4;
        //baseSecondMagic = secondMagic = 1;
        if (data != null) {
            baseMagicNumber += data.g;
            magicNumber = baseMagicNumber;
            //baseSecondMagic += data.g;
            //secondMagic = baseSecondMagic;
        }
        //Worst: 5 Vigor, 1 draw
        //Best: 9 Vigor, 3 draw
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new ElysiumHarp(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
        //upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(Color.PURPLE), WHITE, Color.GOLD, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return ElysiumHarp.class.getSimpleName();
    }

    @Override
    public void onEnter(CardGroup g) {
        if (g == Wiz.adp().hand) {
            superFlash();
            addToBot(new SFXAction("TINGSHA"));
            addToBot(new VFXAction(Wiz.adp(), new ShockWaveEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY, Color.GOLD.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
            //addToBot(new DrawCardAction(secondMagic));
            Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new StaggerPower(mon, magicNumber)));
        }
    }
}