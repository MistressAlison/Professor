package Professor.actions;

import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class AncientBlueWaterAction extends AbstractGameAction {
    public AncientBlueWaterAction(int block) {
        amount = block;
        actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        int cards = Wiz.adp().hand.size();
        for (int i = 0 ; i < cards ; i++) {
            addToTop(new GainBlockAction(Wiz.adp(), amount));
        }
        addToTop(new SpectrumizeAction(cards));
        this.isDone = true;
    }
}
