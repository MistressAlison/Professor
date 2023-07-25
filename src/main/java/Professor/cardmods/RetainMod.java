package Professor.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Professor.MainModfile.makeID;

public class RetainMod extends AbstractCardModifier {
    public static String ID = makeID("RetainMod");
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.selfRetain;
    }

    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    public AbstractCardModifier makeCopy() {
        return new RetainMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
