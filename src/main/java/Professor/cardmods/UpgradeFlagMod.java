package Professor.cardmods;

import Professor.patches.ForcedUpgradesPatches;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Professor.MainModfile.makeID;

public class UpgradeFlagMod extends AbstractCardModifier {
    public static String ID = makeID(UpgradeFlagMod.class.getSimpleName());

    @Override
    public void onInitialApplication(AbstractCard card) {
        ForcedUpgradesPatches.ForcedUpgradeField.inf.set(card, true);
    }

    public AbstractCardModifier makeCopy() {
        return new UpgradeFlagMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
