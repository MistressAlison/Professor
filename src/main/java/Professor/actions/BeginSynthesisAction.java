package Professor.actions;

import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.patches.EmpowerRedirectPatches;
import Professor.ui.SynthesisPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BeginSynthesisAction extends AbstractGameAction {
    protected final AbstractRecipeCard recipeCard;

    public BeginSynthesisAction(AbstractRecipeCard recipe) {
        this.recipeCard = recipe;
    }

    @Override
    public void update() {
        if (SynthesisPanel.currentCreation != null) {
            addToTop(new BeginSynthesisAction(recipeCard));
            addToTop(new PerformSynthesisAction());
        } else {
            SynthesisPanel.beginSynthesis(recipeCard);
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1f);
            /*addToTop(new BetterSelectCardsInHandAction(recipeCard.getValance(), "Test", true, true, c -> true, l -> {
                for (AbstractCard c : l) {
                    EmpowerRedirectPatches.setRedirect(c, SynthesisPanel.BASE_X, SynthesisPanel.BASE_Y);
                    AbstractDungeon.player.hand.empower(c);
                    SynthesisPanel.addCard(c);
                }
                l.clear();
                addToTop(new PerformSynthesisAction());
            }));*/
        }
        this.isDone = true;
    }
}
