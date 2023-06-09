package Professor.actions;

import Professor.cards.interfaces.OnInfusionPower;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Predicate;

public class InfuseCardsInHandAction extends ModifyCardsInHandAction {
    public InfuseCardsInHandAction(int amount, AbstractCardModifier mod) {
        this(amount, false, c -> true, mod);
    }

    public InfuseCardsInHandAction(int amount, boolean anyAmount, Predicate<AbstractCard> filter, AbstractCardModifier mod) {
        super(amount, anyAmount, filter, l -> {
            for (AbstractCard c : l) {
                int times = 1;
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof OnInfusionPower) {
                        times += ((OnInfusionPower) p).increaseTimes(c, mod);
                    }
                }
                for (int i = 0 ; i < times ; i++) {
                    CardModifierManager.addModifier(c, mod.makeCopy());
                    //Wiz.att(new ApplyCardModifierAction(c, mod.makeCopy()));
                }
            }
        });
    }
}
