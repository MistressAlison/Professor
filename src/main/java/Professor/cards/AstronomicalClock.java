package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.AstronomicalClockPower;
import Professor.powers.AstronomicalClockPower2;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AstronomicalClock extends AbstractEasyCard {
    public final static String ID = makeID(AstronomicalClock.class.getSimpleName());

    public AstronomicalClock() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AstronomicalClockPower(p, magicNumber));
        if (secondMagic > 0) {
            Wiz.applyToSelf(new AstronomicalClockPower2(p, secondMagic));
        }
    }

    @Override
    public void upp() {
        if (timesUpgraded == 1) {
            upgradeBaseCost(1);
        } else if (timesUpgraded == 2) {
            upgradeBaseCost(0);
        } else {
            if (baseSecondMagic == -1) {
                baseSecondMagic = secondMagic = 0;
            }
            upgradeSecondMagic(3);
        }
        //isInnate = true;
        //uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, ORANGE, YELLOW, mix(GRAY, CERULEAN), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return AstronomicalClock.class.getSimpleName();
    }
}