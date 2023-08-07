package Professor.relics;

import Professor.TheProfessor;
import Professor.powers.ExposedPower;
import Professor.util.Wiz;
import Professor.vfx.BarbExplodeEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Professor.MainModfile.makeID;

public class UniCharm extends AbstractEasyRelic {
    public static final String ID = makeID(UniCharm.class.getSimpleName());
    private static final int AMOUNT = 2;

    public UniCharm() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        addToBot(new VFXAction(new BarbExplodeEffect(Color.BROWN), 0.2f));
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(AMOUNT, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        Wiz.forAllMonstersLiving(m -> Wiz.applyToEnemy(m, new ExposedPower(m, AMOUNT)));
    }
}
