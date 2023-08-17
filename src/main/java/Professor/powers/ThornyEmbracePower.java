package Professor.powers;

import Professor.MainModfile;
import Professor.cutStuff.cards.ThornyEmbrace;
import Professor.util.PowerIconMaker;
import Professor.util.Wiz;
import Professor.vfx.BarbExplodeEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ThornyEmbracePower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(ThornyEmbracePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ThornyEmbracePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, ThornyEmbrace.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        int attackers = (int) Wiz.getEnemies().stream().filter(mon -> mon.getIntentBaseDmg() >= 0).count();
        if (attackers > 0) {
            flash();
            addToBot(new VFXAction(new BarbExplodeEffect(Color.FOREST)));
        }
        Wiz.forAllMonstersLiving(mon -> {
            if (mon.getIntentBaseDmg() >= 0) {
                addToBot(new DamageAction(mon, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
            }
        });
    }
}