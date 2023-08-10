package Professor.relics;

import Professor.TheProfessor;
import Professor.util.Wiz;
import Professor.vfx.BarbExplodeEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Professor.MainModfile.makeID;

public class UniCharm extends AbstractEasyRelic {
    public static final String ID = makeID(UniCharm.class.getSimpleName());
    private static final int AMOUNT = 8;

    public UniCharm() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != Wiz.adp() && info.type == DamageInfo.DamageType.NORMAL && !grayscale) {
            flash();
            addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(AMOUNT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            addToTop(new VFXAction(new BarbExplodeEffect(Color.BROWN), 0.2f));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            grayscale = true;
        }
        return damageAmount;
    }
}
