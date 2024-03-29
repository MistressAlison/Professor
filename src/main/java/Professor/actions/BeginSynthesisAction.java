package Professor.actions;

import Professor.MainModfile;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.cards.interfaces.InstantSynthesisCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.patches.CustomTags;
import Professor.patches.EmpowerRedirectPatches;
import Professor.powers.interfaces.OnSetupSynthesisPower;
import Professor.ui.SynthesisItem;
import Professor.ui.SynthesisPanel;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class BeginSynthesisAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(MainModfile.makeID("SynthesisAction")).TEXT;
    protected final AbstractRecipeCard recipeCard;
    protected final String extraInfo;

    public BeginSynthesisAction(AbstractRecipeCard recipe) {
        this(recipe, "");
    }

    public BeginSynthesisAction(AbstractRecipeCard recipe, String info) {
        this.recipeCard = recipe;
        this.extraInfo = info;
    }

    @Override
    public void update() {
        if (Wiz.adp().hand.size() < recipeCard.getValance()) {
            addToTop(new TalkAction(true, TEXT[1], 0f, 2f));
            this.isDone = true;
            return;
        }
        addToTop(new BetterSelectCardsInHandAction(recipeCard.getValance(), TEXT[0]+extraInfo, false, false, c -> true, l -> {
            recipeCard.triggered = true;
            boolean instant = false;
            SynthesisItem item = new SynthesisItem(recipeCard, l);
            SynthesisPanel.addSynthesisItem(item);
            EmpowerRedirectPatches.setRedirect(recipeCard, SynthesisPanel.getBaseX(), SynthesisPanel.getBaseY());
            AbstractDungeon.player.hand.empower(recipeCard);
            recipeCard.fadingOut = true;
            for (AbstractCard c : l) {
                EmpowerRedirectPatches.setRedirect(c, SynthesisPanel.getBaseX(), SynthesisPanel.getBaseY());
                AbstractDungeon.player.hand.empower(c);
                if (c instanceof OnUseInSynthesisCard) {
                    if (((OnUseInSynthesisCard) c).onAdded(item)) {
                        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    }
                }
                if (c instanceof InstantSynthesisCard) {
                    instant |= ((InstantSynthesisCard) c).isInstant(item);
                }
            }
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnSetupSynthesisPower) {
                    ((OnSetupSynthesisPower) p).onSetupSynthesis(item);
                }
            }
            l.clear();
            AbstractDungeon.onModifyPower();
            if (instant) {
                addToBot(new PerformSynthesisAction(item));
            }
            }));
        this.isDone = true;
    }
}
