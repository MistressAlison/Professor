package Professor.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class BetterSelectCardsInHandAction extends AbstractGameAction {
    private final Predicate<AbstractCard> predicate;
    private final Consumer<List<AbstractCard>> callback;
    private final String text;
    private final boolean anyNumber;
    private final boolean canPickZero;
    private final ArrayList<AbstractCard> hand;
    private final ArrayList<AbstractCard> tempHand;

    public BetterSelectCardsInHandAction(int amount, String textForSelect, boolean anyNumber, boolean canPickZero, Predicate<AbstractCard> cardFilter, Consumer<List<AbstractCard>> callback) {
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.text = textForSelect;
        this.anyNumber = anyNumber;
        this.canPickZero = canPickZero;
        this.predicate = cardFilter;
        this.callback = callback;
        this.hand = AbstractDungeon.player.hand.group;
        this.tempHand = new ArrayList<>();
        this.tempHand.addAll(this.hand);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.hand.size() != 0 && this.hand.stream().anyMatch(this.predicate) && this.callback != null) {
                this.tempHand.removeIf(this.predicate);
                if (this.tempHand.size() > 0) {
                    this.hand.removeIf(this.tempHand::contains);
                }
                AbstractDungeon.handCardSelectScreen.open(this.text, this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
            } else {
                if (callback != null) {
                    callback.accept(Collections.emptyList());
                }
                this.isDone = true;
            }
        } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            this.callback.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            this.hand.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            if (this.tempHand.size() > 0) {
                this.hand.addAll(this.tempHand);
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            this.isDone = true;
        } else {
            this.tickDuration();
        }
    }
}