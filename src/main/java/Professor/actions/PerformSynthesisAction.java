package Professor.actions;

import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.patches.CustomTags;
import Professor.ui.SynthesisItem;
import Professor.ui.SynthesisPanel;
import Professor.ui.SynthesisSlot;
import Professor.util.CustomSounds;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

public class PerformSynthesisAction extends AbstractGameAction {
    private final CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private static final float DUR = 1.5f;
    private final SynthesisItem item;
    private final AbstractCard card;

    public PerformSynthesisAction(SynthesisItem i) {
        this.item = i;
        this.startDuration = duration = DUR;
        this.card = item.currentCreation;
    }

    @Override
    public void update() {
        if (item.slots.stream().allMatch(s -> s.isSnapped() || s.isEmpty())) {
            if (duration == startDuration) {
                item.processing = true;
                CardCrawlGame.sound.play(CustomSounds.SYNTH_START_KEY2, 0.1f);
            }
            tickDuration();
        }
        if (isDone) {
            //card.setCostForTurn(0);
            cardToHand(card);
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (SynthesisSlot s : item.slots) {
                if (s.card != null) {
                    if (s.card.hasTag(CustomTags.PROF_CATALYST)) {
                        cardToHand(s.card);
                    } else {
                        temp.moveToDiscardPile(s.card);
                    }
                    if (s.card instanceof OnUseInSynthesisCard) {
                        ((OnUseInSynthesisCard) s.card).onSynthesis(card);
                    }
                }
            }
            AbstractDungeon.player.hand.glowCheck();
            CardCrawlGame.sound.play(CustomSounds.SYNTH_END_KEY, 0.1f);
            AbstractDungeon.effectList.add(new SanctityEffect(card.hb.cX, card.hb.cY));
            item.clear();
            SynthesisPanel.items.remove(item);
        }
    }

    private void cardToHand(AbstractCard cardToMove) {
        g.addToTop(cardToMove);
        cardToMove.targetDrawScale = 1.0f;
        float bx = cardToMove.current_x;
        float by = cardToMove.current_y;
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            g.moveToDiscardPile(cardToMove);
        } else {
            g.moveToHand(cardToMove);
        }
        cardToMove.current_x = bx;
        cardToMove.current_y = by;
    }
}
