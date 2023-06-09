package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.SeptrinPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Septrin extends AbstractEasyCard {
    public final static String ID = makeID(Septrin.class.getSimpleName());

    public Septrin() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SeptrinPower(p));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.PINK, Color.SKY, Color.PINK, Color.SKY, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return Septrin.class.getSimpleName();
    }
}