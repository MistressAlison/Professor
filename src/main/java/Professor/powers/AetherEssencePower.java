package Professor.powers;

import Professor.MainModfile;
import Professor.cards.AetherEssence;
import Professor.ui.SynthesisPanel;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AetherEssencePower extends AbstractPower {

    public static final String POWER_ID = MainModfile.makeID(AetherEssencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AetherEssencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, AetherEssence.class.getSimpleName());
        updateDescription();
        this.priority = 25;
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (!SynthesisPanel.items.isEmpty()) {
            damage *= Math.pow(0.5, amount);
        }
        return damage;
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (int)(100 - Math.pow(0.5, amount)*100) + DESCRIPTIONS[1];
    }
}