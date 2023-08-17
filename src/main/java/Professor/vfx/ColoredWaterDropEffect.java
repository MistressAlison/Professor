package Professor.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

public class ColoredWaterDropEffect extends WaterDropEffect {
    private float x;
    private float y;
    private int frame = 0;
    private float animTimer = 0.1F;
    public ColoredWaterDropEffect(float x, float y, Color c) {
        super(x, y);
        this.x = x;
        this.y = y;
        color.set(c);
        scale /= Settings.scale;
    }

    @Override
    public void update() {
        this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);// 30
        this.animTimer -= Gdx.graphics.getDeltaTime();// 31
        if (this.animTimer < 0.0F) {// 32
            this.animTimer += 0.1F;// 33
            ++this.frame;// 34
            if (this.frame == 3) {// 36
                for(int i = 0; i < 3; ++i) {// 37
                    AbstractDungeon.effectsQueue.add(new ColoredWaterSplashEffect(this.x, this.y, color));// 38
                }
            }

            if (this.frame > 5) {// 42
                this.frame = 5;// 43
                this.isDone = true;// 44
            }
        }
    }
}
