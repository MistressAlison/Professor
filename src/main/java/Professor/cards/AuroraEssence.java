package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.AetherEssencePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AuroraEssence extends AbstractEasyCard {
    public final static String ID = makeID(AuroraEssence.class.getSimpleName());

    public AuroraEssence() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AetherEssencePower(p, magicNumber));
    }

    @Override
    public void upp() {
        if (timesUpgraded > 2) {
            upgradeMagicNumber(1);
        } else if (timesUpgraded == 2) {
            upgradeBaseCost(0);
        } else {
            upgradeBaseCost(1);
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, YELLOW, mix(BLACK, RED), pastel(GOLD), pastel(Color.GOLDENROD), false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return AuroraEssence.class.getSimpleName();
    }
}