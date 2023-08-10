package Professor.actions;

import Professor.MainModfile;
import Professor.util.TexLoader;
import Professor.util.Wiz;
import Professor.vfx.ParticleEffect;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ThrowObjectAction extends AbstractGameAction {
    private static final float THROW_TIME = 0.25f;
    private final Texture tex;
    private final Color color;
    private final float scale;
    private final Hitbox target;
    public boolean spawned = false;
    private boolean bounceOff;
    private AbstractGameEffect e;

    public ThrowObjectAction(String item, float scale, Hitbox target, Color color) {
        this(TexLoader.getTexture(MainModfile.makeImagePath("items/"+item+".png")), scale, target, color, true);
    }

    public ThrowObjectAction(String item, float scale, Hitbox target, Color color, boolean bounceOff) {
        this(TexLoader.getTexture(MainModfile.makeImagePath("items/"+item+".png")), scale, target, color, bounceOff);
    }

    public ThrowObjectAction(Texture tex, float scale, Hitbox target, Color color, boolean bounceOff) {
        this.tex = tex;
        this.target = target;
        this.color = color;
        this.scale = scale;
        this.bounceOff = bounceOff;
    }

    @Override
    public void update() {
        if (!spawned) {
            spawned = true;
            AbstractGameEffect hitBounce = new VfxBuilder(tex, target.cX, target.cY, bounceOff ? 1.5f : 0f)
                    .setScale(scale)
                    .gravity(50f)
                    .velocity(MathUtils.random(45f, 135f), MathUtils.random(600f, 800f))
                    .rotate(MathUtils.random(50f, 200f) * (MathUtils.randomBoolean() ? -1 : 1))
                    .build();
            AbstractGameEffect throwEffect = new VfxBuilder(tex, Wiz.adp().hb.cX, Wiz.adp().hb.cY, THROW_TIME)
                    .playSoundAt(0.0f, "ATTACK_WHIFF_2")
                    //.velocity(0f, 1920f*1.5f)
                    .moveX(Wiz.adp().hb.cX, target.cX, VfxBuilder.Interpolations.POW2OUT)
                    .moveY(Wiz.adp().hb.cY, target.cY, VfxBuilder.Interpolations.POW2OUT)
                    .rotate(MathUtils.random(100f, 300f) * (MathUtils.randomBoolean() ? -1 : 1))
                    .setScale(scale)
                    .emitEvery((x,y) -> new ParticleEffect(color, x, y), 0.01f)
                    .triggerVfxAt(THROW_TIME, 1, (x,y) -> hitBounce)
                    .build();
            AbstractDungeon.effectList.add(throwEffect);
            this.e = throwEffect;
        }
        this.isDone = e.isDone;
    }
}
