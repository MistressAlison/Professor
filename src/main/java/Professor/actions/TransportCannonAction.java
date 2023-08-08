package Professor.actions;

import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class TransportCannonAction extends AbstractGameAction {
    private final DamageInfo info;

    public TransportCannonAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.target = target;
        this.source = AbstractDungeon.player;
    }

    @Override
    public void update() {
        CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1");
        int size = Wiz.adp().hand.size();
        for (int i = 0; i < size; i++) {
            addToTop(new DamageAction(target, info, AttackEffect.SLASH_DIAGONAL));
        }
        Wiz.applyToSelfTop(new DrawCardNextTurnPower(Wiz.adp(), size));
        addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, size, false));
        this.isDone = true;
    }
}