package Professor.actions;

import Professor.cardmods.AbstractInfusion;
import Professor.powers.interfaces.OnInfusionPower;
import Professor.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InfuseRandomCardAction extends AbstractGameAction {
    private final AbstractCardModifier mod;
    private final Predicate<AbstractCard> filter;

    public InfuseRandomCardAction(int amount, AbstractCardModifier mod) {
        this(amount, mod, c -> true);
    }

    public InfuseRandomCardAction(int amount, AbstractCardModifier mod, Predicate<AbstractCard> filter) {
        this.mod = mod;
        this.amount = amount;
        this.filter = filter.and(AbstractInfusion::usesVanillaTargeting);
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = Wiz.adp().hand.group.stream().filter(filter).collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0 ; i < amount ; i++) {
            AbstractCard c = Wiz.getRandomItem(validCards);
            int times = 1;
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnInfusionPower) {
                    times += ((OnInfusionPower) p).increaseTimes(c, mod);
                }
            }
            for (int k = 0 ; k < times ; k++) {
                CardModifierManager.addModifier(c, mod.makeCopy());
                //Wiz.att(new ApplyCardModifierAction(c, mod.makeCopy()));
            }
            c.superFlash();
        }
        CardCrawlGame.sound.play("MONSTER_COLLECTOR_SUMMON", 0.2f);
        CardCrawlGame.sound.play("RELIC_DROP_MAGICAL", 0.2f);
        this.isDone = true;
    }
}
