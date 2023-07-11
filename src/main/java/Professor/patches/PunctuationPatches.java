/*
package Professor.patches;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;

public class PunctuationPatches {
    @SpirePatch2(clz = AbstractCard.class, method = "initializeDescription")
    public static class MultiPunctuation {
        @SpireInsertPatch(locator = Locator.class, localvars = {"word"})
        public static void plz(@ByRef String[] word, StringBuilder ___sbuilder2) {
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

    @SpirePatch2(clz = AbstractCard.class, method = "renderDescription")
    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderDescription")
    public static class FixHighlighting {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp", "punctuation"})
        public static void plz(@ByRef String[] tmp, @ByRef String[] punctuation) {
            if (tmp[0].length() > 0 && tmp[0].charAt(tmp[0].length() - 1) != '+' && tmp[0].charAt(tmp[0].length() - 1) != ' ' && !Character.isLetter(tmp[0].charAt(tmp[0].length() - 1))) {
                punctuation[0] = tmp[0].charAt(tmp[0].length() - 1) + punctuation[0].trim();
                tmp[0] = tmp[0].substring(0, tmp[0].length() - 1);
                punctuation[0] = punctuation[0] + ' ';
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
*/
