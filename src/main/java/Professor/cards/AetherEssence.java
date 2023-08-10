package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.AetherPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AetherEssence extends AbstractEasyCard {
    public final static String ID = makeID(AetherEssence.class.getSimpleName());

    public AetherEssence() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 8;
        //isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AetherPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(2);
        upgradeBaseCost(1);
        //isEthereal = false;
        //uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, ORANGE, BLACK, Color.GRAY, Color.PURPLE, false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return AetherEssence.class.getSimpleName();
    }
}