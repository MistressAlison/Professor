package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.PhilosophersPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class OldenPhilosophersStone extends AbstractEasyCard {
    public final static String ID = makeID(OldenPhilosophersStone.class.getSimpleName());

    public OldenPhilosophersStone() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(BaseModCardTags.FORM);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PhilosophersPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        isEthereal = false;
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, RED, mix(CYAN, Color.LIGHT_GRAY), RED, mix(CYAN, Color.LIGHT_GRAY), false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return OldenPhilosophersStone.class.getSimpleName();
    }
}