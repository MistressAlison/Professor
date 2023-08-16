package Professor.patches;

import Professor.MainModfile;
import Professor.powers.ExposedPower;
import Professor.util.ImageHelper;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.nio.charset.StandardCharsets;

public class ShaderOnEnemyPatches {
    @SpirePatch2(clz = AbstractMonster.class, method = "render")
    @SpirePatch2(clz = CustomMonster.class, method = "render")
    public static class ShaderTime {
        public static ShaderProgram shader = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/watercolor2.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
        public static FrameBuffer fb = ImageHelper.createBuffer();
        private static boolean capturing = false;

        @SpirePrefixPatch
        public static void capture(AbstractMonster __instance, SpriteBatch sb) {
            if (__instance.hasPower(ExposedPower.POWER_ID)) {
                capturing = true;
                sb.end();
                ImageHelper.beginBuffer(fb);
                sb.begin();
            }
        }

        @SpirePostfixPatch
        public static void render(SpriteBatch sb) {
            if (capturing) {
                fb.end();
                TextureRegion r = ImageHelper.getBufferTexture(fb);
                //r.flip(false, true);
                ShaderProgram back = sb.getShader();
                sb.setShader(shader);
                shader.setUniformf("x_time", MainModfile.time);
                sb.draw(r, 0, 0);
                sb.setShader(back);
            }
        }
    }
}
