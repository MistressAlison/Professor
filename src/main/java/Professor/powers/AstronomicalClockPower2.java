package Professor.powers;

import Professor.MainModfile;
import Professor.cards.AstronomicalClock;
import Professor.powers.interfaces.OnSetupSynthesisPower;
import Professor.ui.SynthesisItem;
import Professor.ui.SynthesisPanel;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AstronomicalClockPower2 extends AbstractPower implements OnSetupSynthesisPower {
    public static final String POWER_ID = MainModfile.makeID(AstronomicalClockPower2.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AstronomicalClockPower2(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, AstronomicalClock.class.getSimpleName());
        updateDescription();
        this.priority = 26;
    }

    @Override
    public void onInitialApplication() {
        if (SynthesisPanel.items.size() == 1) {
            //flash();
            addToBot(new GainBlockAction(owner, owner, amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onSetupSynthesis(SynthesisItem item) {
        if (SynthesisPanel.items.size() == 1) {
            //flash();
            addToBot(new GainBlockAction(owner, owner, amount));
        }
    }
}