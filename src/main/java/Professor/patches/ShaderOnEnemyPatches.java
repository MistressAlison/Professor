package Professor.patches;

import Professor.MainModfile;
import Professor.powers.UnstablePower;
import Professor.util.ImageHelper;
import Professor.util.Wiz;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

import java.nio.charset.StandardCharsets;

public class ShaderOnEnemyPatches {
    public static ShaderProgram shader = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/watercolor2.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    public static FrameBuffer fb = ImageHelper.createBuffer();
    private static boolean capturing = false;

    public static void begin(AbstractCreature __instance, SpriteBatch sb) {
        capturing = false;
        if (__instance.hasPower(UnstablePower.POWER_ID)) {
            capturing = true;
            sb.end();
            ImageHelper.beginBuffer(fb);
            sb.begin();
        }
    }

    public static void draw(SpriteBatch sb) {
        if (capturing) {
            fb.end();
            TextureRegion r = ImageHelper.getBufferTexture(fb);
            ShaderProgram back = sb.getShader();
            sb.setShader(shader);
            shader.setUniformf("x_time", MainModfile.time);
            sb.draw(r, 0, 0);
            sb.setShader(back);
        }
    }

    @SpirePatch2(clz = AbstractMonster.class, method = "render")
    @SpirePatch2(clz = CustomMonster.class, method = "render")
    public static class ShaderTime {
        @SpirePrefixPatch
        public static void onAtStart(AbstractMonster __instance, SpriteBatch sb) {
            begin(__instance, sb);
        }

        @SpirePostfixPatch
        public static void failsafe(SpriteBatch sb) {
            if (capturing) {
                draw(sb);
            }
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "renderHealthText")
    public static class ShaderTime2 {
        @SpirePrefixPatch
        public static void ofForHealthText(SpriteBatch sb) {
            draw(sb);
        }

        @SpirePostfixPatch
        public static void backOn(AbstractCreature __instance, SpriteBatch sb) {
            begin(__instance, sb);
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "renderBlockIconAndValue")
    public static class ShaderTime3 {
        /*@SpirePrefixPatch
        public static void backOn(AbstractCreature __instance, SpriteBatch sb) {
            begin(__instance, sb);
        }*/

        @SpirePrefixPatch
        //@SpireInsertPatch(locator = BlockTextLocator.class)
        public static void offForBlockText(SpriteBatch sb) {
            draw(sb);
        }

        public static class BlockTextLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "renderPowerIcons")
    public static class ShaderTime5 {
        private static final Color blockTextColor = new Color(0.9F, 0.9F, 0.9F, 1.0F);
        /*@SpirePrefixPatch
        public static void backOn(AbstractCreature __instance, SpriteBatch sb) {
            begin(__instance, sb);
        }*/

        //@SpireInsertPatch(locator = PowerTextLocator.class)
        @SpirePrefixPatch
        public static void offForPowerText(AbstractCreature __instance, SpriteBatch sb, float ___hbYOffset, float ___BLOCK_ICON_X, Color ___blockTextColor, float ___blockScale) {
            //boolean fix = capturing;
            if (capturing) {
                draw(sb);
            }

            /*if (__instance.currentBlock != 0 && __instance.hbAlpha != 0.0F) {
                float x = __instance.hb.cX - __instance.hb.width / 2.0F;
                float y = __instance.hb.cY - __instance.hb.height / 2.0F + ___hbYOffset;
                FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(__instance.currentBlock), x + ___BLOCK_ICON_X, y - 16.0F * Settings.scale, Color.WHITE, ___blockScale);
            }*/
        }
        public static class PowerTextLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
                return new int[]{LineFinder.findAllInOrder(ctBehavior, m)[1]};
            }
        }
    }
}
