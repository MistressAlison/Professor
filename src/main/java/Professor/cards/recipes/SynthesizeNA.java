package Professor.cards.recipes;

import Professor.actions.BeginSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.cards.creations.NA;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SynthesizeNA extends AbstractRecipeCard {
    public final static String ID = makeID(SynthesizeNA.class.getSimpleName());

    public SynthesizeNA() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseInfo = info = 6;
        cardsToPreview = new NA();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new BeginSynthesisAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        //upgradeBaseCost(0);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, BLACK, WHITE, BLACK, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return "NA";
    }

    @Override
    public int getValance() {
        return info;
    }

    @Override
    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
        return new NA(new AbstractCreationCard.ElementData(red, blue, yellow, green));
    }
}