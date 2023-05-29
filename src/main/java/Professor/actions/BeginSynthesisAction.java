package Professor.actions;

import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.ui.SynthesisPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BeginSynthesisAction extends AbstractGameAction {
    private final AbstractRecipeCard recipeCard;

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
        }
        this.isDone = true;
    }
}
