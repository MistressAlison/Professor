package Professor.cards.interfaces;

import Professor.ui.SynthesisItem;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnUseInSynthesisCard {
    default void onComplete(AbstractCard createdCard) {};
    boolean onAdded(SynthesisItem item);
}
