package Professor.patches;

import Professor.powers.interfaces.DrawManipPower;
import Professor.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DrawManipPatches {
    @SpirePatch2(clz = DrawCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCreature.class, int.class, boolean.class})
    public static class ModifyAmount {
        @SpirePostfixPatch
        public static void modify(DrawCardAction __instance) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof DrawManipPower) {
                    __instance.amount = ((DrawManipPower) p).changeAmount(__instance.amount);
                }
            }
        }
    }
}
