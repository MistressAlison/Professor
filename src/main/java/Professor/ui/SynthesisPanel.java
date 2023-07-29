package Professor.ui;

import Professor.actions.PerformSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.patches.ArchetypeHelper;
import Professor.patches.CustomTags;
import Professor.util.ChimeraHelper;
import Professor.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import com.megacrit.cardcrawl.vfx.BobEffect;
import javassist.CtBehavior;

import java.util.ArrayList;

public class SynthesisPanel {
    public static final float Y_OFFSET = 100f * Settings.scale;
    public static final float BASE_X = Wiz.adp().hb.cX;
    public static final float BASE_Y = Wiz.adp().hb.cY + Wiz.adp().hb.height/2f + Y_OFFSET;
    public static final float ORBIT_R = 120f * Settings.scale;
    private static final BobEffect bob = new BobEffect(3.0f * Settings.scale, 3.0f);
    public static final ArrayList<SynthesisSlot> slots = new ArrayList<>();
    public static AbstractRecipeCard currentRecipe;
    public static AbstractCreationCard currentCreation;
    public static int r,b,y,g;
    public static boolean hovered;
    public static float radius = ORBIT_R;
    public static float angle;
    public static float baseAngularSpeed = 5f;
    public static float angularSpeed = baseAngularSpeed;
    public static float angularAcceleration = 25f;
    public static boolean processing;

    public static void update() {
        bob.update();
        if (currentCreation != null) {
            currentCreation.target_y = BASE_Y + bob.y;
            currentCreation.target_x = BASE_X;
            currentCreation.targetAngle = 0;
            currentCreation.update();
            currentCreation.hb.update();
            if (!processing && currentCreation.hb.hovered) {
                currentCreation.targetDrawScale = 0.75f;
                hovered = true;
            } else {
                currentCreation.targetDrawScale = 0.2f;
                hovered = false;
            }
        }
        positionSlots();
        for (SynthesisSlot s : slots) {
            s.update();
        }
    }

    public static void render(SpriteBatch sb) {
        for (SynthesisSlot s : slots) {
            s.render(sb);
        }
        if (currentCreation != null) {
            currentCreation.render(sb);
            if (!processing && currentCreation.hb.hovered) {
                TipHelper.renderTipForCard(currentCreation, sb, currentCreation.keywords);
            }
        }
    }

    public static void beginSynthesis(AbstractRecipeCard recipe) {
        currentRecipe = recipe;
        currentCreation = currentRecipe.getCreation(r, b, y, g);
        currentCreation.current_x = BASE_X;
        currentCreation.current_y = BASE_Y;
        currentCreation.drawScale = 0.2f;
        updateCreation();
        addSlots(recipe.getValance());
        if (Loader.isModLoaded("ChimeraCards")) {
            ChimeraHelper.rollOnSynthesisCard(currentCreation);
        }
    }

    public static void performSynthesis() {
        AbstractDungeon.actionManager.addToTop(new PerformSynthesisAction());
    }

    public static void addSlots(int amount) {
        for (int i = 0 ; i < amount ; i++) {
            slots.add(new SynthesisSlot(BASE_X, BASE_Y));
        }
    }

    public static void fillSlot(AbstractCard card) {
        for (SynthesisSlot s : slots) {
            if (s.isEmpty()) {
                s.addCard(card);
                if (ArchetypeHelper.isFire(card)) {
                    r++;
                }
                if (ArchetypeHelper.isIce(card)) {
                    b++;
                }
                if (ArchetypeHelper.isBolt(card)) {
                    y++;
                }
                if (ArchetypeHelper.isWind(card)) {
                    g++;
                }
                //CardCrawlGame.sound.play(CustomSounds.SYNTH_MIX_KEY, 0.1f);
                break;
            }
        }
        updateCreation();
        if (slots.stream().noneMatch(SynthesisSlot::isEmpty)) {
            performSynthesis();
        }
    }

    public static void updateCreation() {
        currentCreation.updateElementData(new AbstractCreationCard.ElementData(r, b, y, g));
    }

    public static void positionSlots() {
        if (processing || !hovered && slots.stream().noneMatch(SynthesisSlot::isHovered)) {
            angle += angularSpeed* Gdx.graphics.getDeltaTime();
            angle %= 360;
        }
        if (processing) {
            angularSpeed += angularAcceleration;
            radius--;
        }
        float da = 360f/slots.size();
        for (SynthesisSlot s : slots) {
            s.setTarget((float) (BASE_X + radius * Math.cos(Math.toRadians(angle))), (float) (BASE_Y + radius * Math.sin(Math.toRadians(angle))));
            angle += da;
            angle %= 360;
        }
    }

    public static void addCard(AbstractCard card) {
        if (card.hasTag(CustomTags.PROF_REACTANT)) {
            addSlots(1);
        }
        fillSlot(card);
    }

    public static void clear() {
        currentRecipe = null;
        currentCreation = null;
        slots.clear();
        r = b = y = g = 0;
        angularSpeed = baseAngularSpeed;
        radius = ORBIT_R;
        processing = false;
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
