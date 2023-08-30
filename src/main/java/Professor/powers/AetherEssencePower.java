package Professor.powers;

import Professor.MainModfile;
import Professor.cards.AetherEssence;
import Professor.patches.ForcedUpgradesPatches;
import Professor.powers.interfaces.OnCreateCardPower;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AetherEssencePower extends AbstractPower implements OnCreateCardPower {
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
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        if (!card.upgraded) {
            ForcedUpgradesPatches.applyUnlockIfNeeded(card);
            for (int i = 0 ; i < amount ; i++) {
                card.upgrade();
            }
        }
    }

    @Override
    public void onGenerateCardOption(AbstractCard card) {
        ForcedUpgradesPatches.applyUnlockIfNeeded(card);
        for (int i = 0 ; i < amount ; i++) {
            card.upgrade();
        }
    }
}