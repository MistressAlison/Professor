package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import Professor.powers.SeptrinPower;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Septrin extends AbstractEasyCard {
    public final static String ID = makeID(Septrin.class.getSimpleName());

    public Septrin() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        MultiCardPreview.add(this, new RedNeutralizer(), new BlueNeutralizer(), new YellowNeutralizer(), new GreenNeutralizer());
        addCustomKeyword(KeywordManager.CATALYST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SeptrinPower(p));
        if (magicNumber > 0) {
            addToBot(new ExhaustAction(this.magicNumber, false, true, true));
        }
    }

    @Override
    public void upp() {
        if (timesUpgraded > 2) {
            upgradeMagicNumber(1);
        } else if (timesUpgraded == 2) {
            baseMagicNumber = magicNumber = 0;
            upgradeMagicNumber(2);
        } else {
            upgradeBaseCost(0);
        }
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