package Professor.powers;

import Professor.MainModfile;
import Professor.cards.AetherEssence;
import Professor.powers.interfaces.OnFinishSynthesisPower;
import Professor.ui.SynthesisItem;
import Professor.util.PowerIconMaker;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

public class AetherPower extends AbstractPower implements OnFinishSynthesisPower {
    public static final String POWER_ID = MainModfile.makeID(AetherPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AetherPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, AetherEssence.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount <= 0) {
            this.description = DESCRIPTIONS[0];
        } else {
            if (amount == 1) {
                this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
            } else {
                this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
            }
        }
    }

    @Override
    public boolean returnCardsToHand(SynthesisItem item) {
        if (amount > 0) {
            boolean didSomething = false;
            flash();
            for (AbstractCard c : item.getAddedCards()) {
                for (int i = 0 ; i < amount ; i++) {
                    if (c.canUpgrade()) {
                        c.superFlash();
                        c.upgrade();
                        didSomething = true;
                    }
                }
            }
            if (didSomething) {
                CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.2f);
            }
        }
        return true;
    }

    @SpirePatch2(clz = ApplyPowerAction.class, method = "update")
    public static class FixText {
        static int match = 0;
        @SpireInstrumentPatch
        public static ExprEditor patch() {
            return new ExprEditor() {

                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getClassName().equals(PowerBuffEffect.class.getName())) {
                        match++;
                        if (match == 2) {
                            e.replace("$3 = Professor.powers.AetherPower.changeText(powerToApply, $3); $_ = $proceed($$);");
                        }
                    }
                }
            };
        }
    }

    public static String changeText(AbstractPower p, String orig) {
        if (p instanceof AetherPower && p.amount == 0) {
            return p.name;
        }
        return orig;
    }
}