package Professor.patches;

import Professor.MainModfile;
import Professor.powers.UnstablePower;
import Professor.util.ImageHelper;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

import java.nio.charset.StandardCharsets;

public class ShaderOnEnemyPatches {
    public static ShaderProgram shader = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/watercolor2.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    public static FrameBuffer fb = ImageHelper.createBuffer();
    private static boolean capturing = false;
    private static boolean drawnToBuffer = false;

    public static void begin(AbstractCreature __instance, SpriteBatch sb) {
        capturing = false;
        if (__instance.hasPower(UnstablePower.POWER_ID)) {
            capturing = true;
            sb.end();
            if (drawnToBuffer) {
                fb.begin();
            } else {
                ImageHelper.beginBuffer(fb);
            }
            sb.begin();
        }
    }

    public static void end(SpriteBatch sb) {
        if (capturing) {
            sb.end();
            fb.end();
            sb.begin();
            drawnToBuffer = true;
            /*TextureRegion r = ImageHelper.getBufferTexture(fb);
            ShaderProgram origShader = sb.getShader();
            Color origColor = sb.getColor();
            sb.setShader(shader);
            shader.setUniformf("x_time", MainModfile.time);
            sb.setColor(Color.WHITE);
            sb.draw(r, 0, 0);
            sb.setShader(origShader);
            sb.setColor(origColor);*/
        }
    }

    public static void draw(SpriteBatch sb) {
        if (drawnToBuffer) {
            TextureRegion r = ImageHelper.getBufferTexture(fb);
            ShaderProgram origShader = sb.getShader();
            Color origColor = sb.getColor();
            sb.setShader(shader);
            shader.setUniformf("x_time", MainModfile.time);
            sb.setColor(Color.WHITE);
            sb.draw(r, 0, 0);
            sb.setShader(origShader);
            sb.setColor(origColor);
            drawnToBuffer = false;
            capturing = false;
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
            // If the player is dead, it is not turned off in renderHealthText
            end(sb);
        }
    }

    @SpirePatch2(clz = AbstractCreature.class, method = "renderHealthText")
    public static class ShaderTime2 {
        @SpirePostfixPatch
        public static void offForHealthText(SpriteBatch sb) {
            end(sb);
        }
    }

    @SpirePatch2(clz = MonsterGroup.class, method = "render")
    public static class DrawCaptures {
        @SpirePrefixPatch
        public static void reset() {

        }
        @SpirePostfixPatch
        public static void plz(MonsterGroup __instance, SpriteBatch sb) {
            draw(sb);
            for (AbstractMonster m : __instance.monsters) {
                float hbyo = ReflectionHacks.getPrivate(m, AbstractCreature.class, "hbYOffset");
                ReflectionHacks.privateMethod(AbstractCreature.class, "renderHealthText", SpriteBatch.class, float.class).invoke(m, sb, m.hb.cY - m.hb.height / 2.0F + hbyo);
            }
        }
    }
}
