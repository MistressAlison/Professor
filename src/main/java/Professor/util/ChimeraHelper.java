package Professor.util;

import CardAugments.CardAugmentsMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ChimeraHelper {
    public static void rollOnSynthesisCard(AbstractCard card) {
        if (CardAugmentsMod.modifyInCombat) {
            CardAugmentsMod.rollCardAugment(card);
        }
    }
}
