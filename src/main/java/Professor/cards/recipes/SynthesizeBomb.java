package Professor.cards.recipes;

import Professor.actions.BeginSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.cards.creations.Bomb;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SynthesizeBomb extends AbstractRecipeCard {
    public final static String ID = makeID(SynthesizeBomb.class.getSimpleName());

    public SynthesizeBomb() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Bomb();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BeginSynthesisAction(this));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (p.hand.size() <= getValance()) {
                canUse = false;
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }
            return canUse;
        }
    }

    @Override
    public void upp() {
        uDesc();
        //upgradeMagicNumber(1);
        cardsToPreview.upgrade();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, RED, WHITE, RED, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return "Bomb";
    }

    @Override
    public int getValance() {
        return magicNumber;
    }

    @Override
    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
        AbstractCreationCard ret = new Bomb(new AbstractCreationCard.ElementData(red, blue, yellow, green));
        if (upgraded) {
            ret.upgrade();
        }
        return ret;
    }
}