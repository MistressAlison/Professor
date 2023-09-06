package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class EtherealStone extends AbstractEasyCard {
    public final static String ID = makeID(EtherealStone.class.getSimpleName());

    public EtherealStone() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        //isEthereal = true;
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 2;
        cardsToPreview = new VoidCard();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true, false));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.LIGHT_GRAY, WHITE, Color.LIGHT_GRAY, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return EtherealStone.class.getSimpleName();
    }

    /*@Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        addToBot(new DrawCardAction(secondMagic));
        return false;
    }*/
}