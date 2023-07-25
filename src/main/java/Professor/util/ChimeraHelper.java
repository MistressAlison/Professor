package Professor.util;

import CardAugments.CardAugmentsMod;
import CardAugments.patches.InfiniteUpgradesPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ChimeraHelper {
    public static void rollOnSynthesisCard(AbstractCard card) {
        if (CardAugmentsMod.modifyInCombat) {
            CardAugmentsMod.rollCardAugment(card);
        }
    }

    public static boolean hasSearing(AbstractCard card) {
        return InfiniteUpgradesPatches.InfUpgradeField.inf.get(card);
    }
}
