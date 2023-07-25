package Professor.cutStuff.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Professor.MainModfile.makeID;

public class ElysiumHarp extends AbstractCreationCard {
    public final static String ID = makeID(ElysiumHarp.class.getSimpleName());

    public ElysiumHarp() {
        this(null);
    }

    public ElysiumHarp(ElementData data) {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        updateElementData(data);
        addCustomKeyword(KeywordManager.ELYSIUM_HARP);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 2;
        if (data != null) {
            baseMagicNumber += 2*data.y;
            magicNumber = baseMagicNumber;
            baseSecondMagic += data.g;
            secondMagic = baseSecondMagic;
        }
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
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GOLD, WHITE, Color.GOLD, BLACK, false);
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