package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.DragonBloodPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AnotherPlanet extends AbstractEasyCard {
    public final static String ID = makeID(AnotherPlanet.class.getSimpleName());

    public AnotherPlanet() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new DragonBloodPower(p, magicNumber));
    }

    @Override
    public void upp() {

    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(AZURE,Color.GRAY), AZURE, mix(AZURE,Color.GRAY), AZURE, false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return AnotherPlanet.class.getSimpleName();
    }
}