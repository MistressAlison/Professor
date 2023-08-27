package Professor.cards;

import Professor.actions.InfuseRandomCardAction;
import Professor.cardmods.DealDamageMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class DreamPowder extends AbstractEasyCard {
    public final static String ID = makeID(DreamPowder.class.getSimpleName());

    public DreamPowder() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        isEthereal = true;
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        flash();
        addToBot(new InfuseRandomCardAction(magicNumber, new DealDamageMod(secondMagic)));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeSecondMagic(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.GRAY, AZURE), WHITE, mix(Color.GRAY, AZURE), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return DreamPowder.class.getSimpleName();
    }

    @Override
    public float itemScale() {
        return 1.1f;
    }
}