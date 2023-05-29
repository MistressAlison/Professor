package Professor.actions;

import Professor.MainModfile;
import Professor.cards.BlueNeutralizer;
import Professor.cards.GreenNeutralizer;
import Professor.cards.RedNeutralizer;
import Professor.cards.YellowNeutralizer;
import Professor.patches.ArchetypeHelper;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SpectrumizeAction extends AbstractGameAction {
    public static final String ID = MainModfile.makeID(SpectrumizeAction.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public SpectrumizeAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.EXHAUST;
    }

    @Override
    public void update() {
        addToTop(new SelectCardsInHandAction(amount, TEXT[0], l -> {
            for (AbstractCard c : l) {
                addCards(c);
                addToTop(new ShowCardAndPoofAction(c));
            }
            //Clear to not put cards back in hand
            l.clear();
        }));
        this.isDone = true;
    }

    private void addCards(AbstractCard card) {
        if (ArchetypeHelper.isWind(card)) {
            addToTop(new MakeTempCardInHandAction(new GreenNeutralizer()));
        }
        if (ArchetypeHelper.isBolt(card)) {
            addToTop(new MakeTempCardInHandAction(new YellowNeutralizer()));
        }
        if (ArchetypeHelper.isIce(card)) {
            addToTop(new MakeTempCardInHandAction(new BlueNeutralizer()));
        }
        if (ArchetypeHelper.isFire(card)) {
            addToTop(new MakeTempCardInHandAction(new RedNeutralizer()));
        }
    }
}
