package Professor.ui;

import Professor.util.Wiz;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Collections;

public class SynthesisPanel {
    public static final float ITEMS_PER_ROW = 4;
    public static final float Y_OFFSET = 200f * Settings.scale;
    public static final float PAD_X = SynthesisItem.ORBIT_R * 2 + 60f * Settings.scale;
    public static final float PAD_Y = SynthesisItem.ORBIT_R * 2 + 60f * Settings.scale;
    public static final ArrayList<SynthesisItem> items = new ArrayList<>();
    //public static boolean processing;

    public static float getBaseX() {
        return Wiz.adp().hb.cX;
    }

    public static float getBaseY() {
        return Wiz.adp().hb.cY + Wiz.adp().hb.height/2f + Y_OFFSET;
    }

    public static void update() {
        for (SynthesisItem i : items) {
            layoutItems();
            i.update();
        }
    }

    public static void render(SpriteBatch sb) {
        for (SynthesisItem i : items) {
            i.render(sb);
        }
    }

    public static void layoutItems() {
        int index = 0;
        int row = 0;
        int amtThisRow = (int) Math.min(items.size(), ITEMS_PER_ROW);
        for (SynthesisItem item : items) {
            item.tX = getBaseX() - (amtThisRow-1)/2f * PAD_X + index * PAD_X;
            item.tY = getBaseY() - row * PAD_Y;
            index++;
            if (index == ITEMS_PER_ROW) {
                index = 0;
                row++;
                amtThisRow = (int) Math.min(items.size() - (row * ITEMS_PER_ROW), ITEMS_PER_ROW);
            }
        }
    }

    public static void addSynthesisItem(SynthesisItem i) {
        items.add(i);
    }

    public static void performSynthesis() {
        Collections.reverse(items);
        for (SynthesisItem i : items) {
            i.performSynthesis();
        }
        Collections.reverse(items);
    }

    public static void clear() {
        for (SynthesisItem i : items) {
            i.clear();
        }
        items.clear();
        //processing = false;
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "render")
    public static class RenderPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void render(OverlayMenu __instance, SpriteBatch sb) {
            SynthesisPanel.render(sb);
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "render");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class UpdatePile {
        @SpirePostfixPatch
        public static void update(AbstractPlayer __instance) {
            SynthesisPanel.update();
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class EmptyCards {
        @SpirePostfixPatch
        public static void yeet() {
            SynthesisPanel.clear();
        }
    }
}
