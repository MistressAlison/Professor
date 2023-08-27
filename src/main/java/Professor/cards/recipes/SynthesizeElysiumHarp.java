package Professor.cards.recipes;

import Professor.actions.BeginSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.cards.creations.ElysiumHarp;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SynthesizeElysiumHarp extends AbstractRecipeCard {
    public final static String ID = makeID(SynthesizeElysiumHarp.class.getSimpleName());

    public SynthesizeElysiumHarp() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        baseInfo = info = 2;
        cardsToPreview = new ElysiumHarp();
        isEthereal = true;
        addVanillaKeyword(GameDictionary.BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new BeginSynthesisAction(this, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    @Override
    public void upp() {
        //isEthereal = false;
        upgradeMagicNumber(1);
        //uDesc();
        //cardsToPreview.upgrade();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GOLD, WHITE, Color.GOLDENROD, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return "ElysiumHarp";
    }

    @Override
    public int getValance() {
        return info;
    }

    @Override
    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
        return new ElysiumHarp(new AbstractCreationCard.ElementData(red, blue, yellow, green));
    }
}