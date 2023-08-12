package Professor.actions;

import Professor.MainModfile;
import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.nio.charset.StandardCharsets;

public class ApplyShaderEffect extends AbstractGameEffect {
    private final ScreenPostProcessor spp;
    private final ShaderProgram sp;

    public ApplyShaderEffect(ShaderProgram sp, float duration) {
        this.duration = duration;
        this.startingDuration = duration;
        this.sp = sp;
        this.spp = (sb, textureRegion, orthographicCamera) -> {
            sb.setColor(Color.WHITE);
            sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
            ShaderProgram back = sb.getShader();
            sb.setShader(sp);
            sb.draw(textureRegion, 0, 0);
            sb.setShader(back);
        };
        this.color = Color.WHITE.cpy();
    }

    @Override
    public void update() {
        if (duration == startingDuration) {
            ScreenPostProcessorManager.addPostProcessor(spp);
        }
        super.update();
        if (isDone) {
            ScreenPostProcessorManager.removePostProcessor(spp);
        }
    }

    @Override
    public void render(SpriteBatch sb) {}

    @Override
    public void dispose() {
        sp.dispose();
    }

    public static ShaderProgram getFragShader(String key) {
        return new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/"+key+".frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    }
}
