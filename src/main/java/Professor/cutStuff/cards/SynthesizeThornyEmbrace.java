package Professor.cutStuff.cards;

import Professor.actions.BeginSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SynthesizeThornyEmbrace extends AbstractRecipeCard {
    public final static String ID = makeID(SynthesizeThornyEmbrace.class.getSimpleName());

    public SynthesizeThornyEmbrace() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseInfo = info = 2;
        cardsToPreview = new ThornyEmbrace();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BeginSynthesisAction(this, cardStrings.EXTENDED_DESCRIPTION[1]));
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
        upgradeBaseCost(0);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.FOREST, Color.GRAY), WHITE, mix(Color.FOREST, Color.GRAY), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return "ThornyEmbrace";
    }

    @Override
    public int getValance() {
        return info;
    }

    @Override
    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
        return new ThornyEmbrace(new AbstractCreationCard.ElementData(red, blue, yellow, green));
    }
}