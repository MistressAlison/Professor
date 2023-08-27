package Professor.cards.recipes;

import Professor.actions.BeginSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.cards.creations.Luft;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SynthesizeLuft extends AbstractRecipeCard {
    public final static String ID = makeID(SynthesizeLuft.class.getSimpleName());

    public SynthesizeLuft() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseInfo = info = 2;
        cardsToPreview = new Luft();
        addVanillaKeyword(GameDictionary.UPGRADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new BeginSynthesisAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        //uDesc();
        //selfRetain = true;
        //upgradeBaseCost(0);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, SPRING_GREEN, WHITE, SPRING_GREEN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return "Luft";
    }

    @Override
    public int getValance() {
        return info;
    }

    @Override
    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
        return new Luft(new AbstractCreationCard.ElementData(red, blue, yellow, green));
    }
}