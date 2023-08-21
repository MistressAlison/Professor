package Professor.actions;

import Professor.MainModfile;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import Professor.patches.ArchetypeHelper;
import Professor.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class SpectrumizeAction extends AbstractGameAction {
    public static final String ID = MainModfile.makeID(SpectrumizeAction.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static boolean spectrumizing;
    public AbstractCard card;
    private final Consumer<List<AbstractCard>> callback;

    public SpectrumizeAction(int amount) {
        this(amount, c -> {});
    }

    public SpectrumizeAction(int amount, Consumer<List<AbstractCard>> callback) {
        this.amount = amount;
        this.actionType = ActionType.EXHAUST;
        this.callback = callback;
    }

    public SpectrumizeAction(AbstractCard card) {
        this(card, c -> {});
    }

    public SpectrumizeAction(AbstractCard card, Consumer<List<AbstractCard>> callback) {
        this.card = card;
        this.actionType = ActionType.EXHAUST;
        this.callback = callback;
    }

    @Override
    public void update() {
        if (card != null) {
            addCards(card);
            //addToTop(new ShowCardAndPoofAction(card));
            exhaustCard(card);
            callback.accept(Collections.singletonList(card));
        } else if (amount >= Wiz.adp().hand.size()) {
            int size = Wiz.adp().hand.size();
            for (int i = 0 ; i < size ; i++) {
                AbstractCard c = Wiz.adp().hand.getTopCard();
                addCards(c);
                //addToTop(new ShowCardAndPoofAction(c));
                exhaustCard(c);
            }
            callback.accept(Wiz.adp().hand.group);
        } else {
            addToTop(new SelectCardsInHandAction(amount, TEXT[0], l -> {
                for (AbstractCard c : l) {
                    addCards(c);
                    //addToTop(new ShowCardAndPoofAction(c));
                    exhaustCard(c);
                }
                callback.accept(l);
                //Clear to not put cards back in hand
                l.clear();
            }));
        }
        this.isDone = true;
    }

    private void exhaustCard(AbstractCard card) {
        spectrumizing = true;
        Wiz.adp().hand.moveToExhaustPile(card);
        spectrumizing = false;
    }

    public static void addCards(AbstractCard card) {
        if (ArchetypeHelper.isWind(card)) {
            Wiz.att(new MakeTempCardInHandAction(new GreenNeutralizer()));
        }
        if (ArchetypeHelper.isBolt(card)) {
            Wiz.att(new MakeTempCardInHandAction(new YellowNeutralizer()));
        }
        if (ArchetypeHelper.isIce(card)) {
            Wiz.att(new MakeTempCardInHandAction(new BlueNeutralizer()));
        }
        if (ArchetypeHelper.isFire(card)) {
            Wiz.att(new MakeTempCardInHandAction(new RedNeutralizer()));
        }
    }
}
