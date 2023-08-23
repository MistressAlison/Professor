package Professor.powers;

import Professor.MainModfile;
import Professor.actions.InfuseCardsInHandAction;
import Professor.actions.InfuseSpecificCardsAction;
import Professor.cardmods.DealAOEDamageMod;
import Professor.cardmods.DealDamageMod;
import Professor.cards.LunaticDebonyal;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;

public class LunaticDebonyalPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(LunaticDebonyalPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean active;

    public LunaticDebonyalPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, LunaticDebonyal.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        active = true;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (active && InfuseCardsInHandAction.shenaniganFilter.test(card)) {
            flash();
            addToBot(new InfuseSpecificCardsAction(Collections.singletonList(card), new DealAOEDamageMod(amount)));
            active = false;
        }
    }
}