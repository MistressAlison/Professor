package Professor.patches;

import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.ui.SynthesisPanel;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

public class UseCardActionPatches {
    @SpirePatch2(clz = UseCardAction.class, method = "update")
    public static class DirectToSynthesis {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> redirect(UseCardAction __instance, AbstractCard ___targetCard) {
            if (___targetCard instanceof AbstractRecipeCard) {
                EmpowerRedirectPatches.setRedirect(___targetCard, SynthesisPanel.BASE_X, SynthesisPanel.BASE_Y);
                AbstractDungeon.player.hand.empower(___targetCard);
                __instance.isDone = true;
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return SpireReturn.Return();
            }
            if (SynthesisPanel.currentRecipe != null) {
                EmpowerRedirectPatches.setRedirect(___targetCard, SynthesisPanel.currentRecipe);
                AbstractDungeon.player.hand.empower(___targetCard);
                AbstractDungeon.actionManager.addToTop(new ShowCardAction(___targetCard));
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
                } else {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.7F));
                }
                SynthesisPanel.addCard(___targetCard);
                __instance.isDone = true;
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
