package Professor.relics;

import Professor.TheProfessor;
import Professor.patches.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Professor.MainModfile.makeID;

public class UniCharm extends AbstractEasyRelic {
    public static final String ID = makeID(UniCharm.class.getSimpleName());
    private static final int ENERGY_AMT = 1;

    public UniCharm() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.hasTag(CustomTags.PROF_UNI)) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(3, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    /*public void atPreBattle() {
        this.grayscale = false;
        beginLongPulse();
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CustomTags.PROF_UNI) && !this.grayscale) {
            this.grayscale = true;
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            stopPulse();
        }
    }*/
}
