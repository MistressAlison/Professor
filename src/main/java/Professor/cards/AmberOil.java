package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.AmberOilPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AmberOil extends AbstractEasyCard {
    public final static String ID = makeID(AmberOil.class.getSimpleName());

    public AmberOil() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AmberOilPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(4);
        //upgradeBaseCost(0);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, PERSIMMON, WHITE, PERSIMMON, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return AmberOil.class.getSimpleName();
    }
}