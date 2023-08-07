package Professor.actions;

import Professor.MainModfile;
import Professor.vfx.ColoredThrowDaggerEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThrowCrystalAction extends AbstractGameAction {
    private final AbstractCard card;

    public ThrowCrystalAction(AbstractCard card, int hits) {
        setValues(null, AbstractDungeon.player, hits);
        this.card = card;
    }

    @Override
    public void update() {
        if (amount <= 0) {
            this.isDone = true;
            return;
        }
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target != null) {
            this.card.calculateCardDamage((AbstractMonster)this.target);
            if (amount > 1) {
                addToTop(new ThrowCrystalAction(card, amount-1));
            }
            addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
            addToTop(new VFXAction(new ColoredThrowDaggerEffect(target.hb.cX, target.hb.cY, MainModfile.getRainbowColor())));
        }
        this.isDone = true;
    }
}
