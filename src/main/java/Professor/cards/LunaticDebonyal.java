package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.LunaticDebonyalPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class LunaticDebonyal extends AbstractEasyCard {
    public final static String ID = makeID(LunaticDebonyal.class.getSimpleName());

    public LunaticDebonyal() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new LunaticDebonyalPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //isInnate = true;
        //uDesc();
        upgradeMagicNumber(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, RED, Color.GRAY, darken(RED), false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return "LunaticDebonyal2";
    }
}