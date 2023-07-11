/*
package Professor.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;

public class PunctuationPatches {
    @SpirePatch2(clz = AbstractCard.class, method = "initializeDescription")
    public static class MultiPunctuation {
        @SpireInsertPatch(locator = Locator.class, localvars = {"word"})
        public static void plz(AbstractCard __instance, @ByRef String[] word, StringBuilder ___sbuilder2) {
            if (word[0].length() > 0 && word[0].charAt(word[0].length() - 1) != ']' && !Character.isLetterOrDigit(word[0].charAt(word[0].length() - 1))) {
                ___sbuilder2.insert(0, word[0].charAt(word[0].length() - 1));
                word[0] = word[0].substring(0, word[0].length() - 1);
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(String.class, "toLowerCase");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
*/
