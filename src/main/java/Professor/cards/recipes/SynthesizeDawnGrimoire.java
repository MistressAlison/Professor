package Professor.cards.recipes;

import Professor.actions.BeginSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.cards.creations.DawnGrimoire;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SynthesizeDawnGrimoire extends AbstractRecipeCard {
    public final static String ID = makeID(SynthesizeDawnGrimoire.class.getSimpleName());

    public SynthesizeDawnGrimoire() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseInfo = info = 3;
        cardsToPreview = new DawnGrimoire();
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new BeginSynthesisAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        //upgradeBaseCost(0);
        //selfRetain = true;
        //uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, RED, WHITE, RED, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return "DawnGrimoire";
    }

    @Override
    public int getValance() {
        return info;
    }

    @Override
    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
        return new DawnGrimoire(new AbstractCreationCard.ElementData(red, blue, yellow, green));
    }
}