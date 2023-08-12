package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

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
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 1;
        if (data != null) {
            baseMagicNumber += 2*data.y;
            magicNumber = baseMagicNumber;
            baseSecondMagic += data.g;
            secondMagic = baseSecondMagic;
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
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(secondMagic));
        Wiz.applyToSelf(new VigorPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(4);
        upgradeSecondMagic(1);
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
}