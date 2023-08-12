package Professor.vfx;

import Professor.MainModfile;
import basemod.ReflectionHacks;
import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ShaderTest implements ScreenPostProcessor {
    public static ShaderProgram sp = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/sobel.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    public static float time;

    @Override
    public void postProcess(SpriteBatch sb, TextureRegion textureRegion, OrthographicCamera orthographicCamera) {
        time += Gdx.graphics.getRawDeltaTime();
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
        ShaderProgram back = sb.getShader();
        sb.setShader(sp);
        if (!AbstractDungeon.isScreenUp && AbstractDungeon.scene instanceof TheBottomScene) {
            ArrayList<InteractableTorchEffect> effects = ReflectionHacks.<ArrayList<InteractableTorchEffect>>getPrivate(AbstractDungeon.scene, TheBottomScene.class, "torches").stream().filter(e -> ReflectionHacks.getPrivate(e, InteractableTorchEffect.class, "activated")).collect(Collectors.toCollection(ArrayList::new));
            if (!effects.isEmpty()) {
                int size = Math.min(8, effects.size());
                float[] x = new float[size];
                float[] y = new float[size];
                float[] xy = new float[size*2];
                for (int i = 0; i < size; i++) {
                    x[i] = ReflectionHacks.getPrivate(effects.get(i), InteractableTorchEffect.class, "x");
                    y[i] = ReflectionHacks.getPrivate(effects.get(i), InteractableTorchEffect.class, "y");
                    xy[2*i] = ReflectionHacks.getPrivate(effects.get(i), InteractableTorchEffect.class, "x");
                    xy[2*i+1] = ReflectionHacks.getPrivate(effects.get(i), InteractableTorchEffect.class, "y");
                }
                sp.setUniformi("torches", size);
                //sp.setUniform2fv("u_torch[0]", x, 0, size);
                //sp.setUniform2fv("u_torch[1]", y, 0, size);
                sp.setUniform2fv("u_torch[0]", xy, 0, size*2);
            } else {
                sp.setUniformi("torches", 0);
            }
        }
        sp.setUniformf("x_time", time);
        sp.setUniformf("u_mouse", InputHelper.mX, InputHelper.mY);
        sb.draw(textureRegion, 0, 0);
        sb.setShader(back);
    }
}
