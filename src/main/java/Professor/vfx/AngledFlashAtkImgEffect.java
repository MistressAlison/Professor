package Professor.vfx;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class AngledFlashAtkImgEffect extends FlashAtkImgEffect {
    public AngledFlashAtkImgEffect(float x, float y, float r, AbstractGameAction.AttackEffect effect, boolean mute) {
        super(x, y, effect, mute);
        rotation = r;
    }

    public AngledFlashAtkImgEffect(float x, float y, float r, AbstractGameAction.AttackEffect effect) {
        super(x, y, effect);
        rotation  = r;
    }
}
