package Professor.powers;

import Professor.MainModfile;
import Professor.actions.SpectrumizeAction;
import Professor.cards.Septrin;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import Professor.util.PowerIconMaker;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SeptrinPower extends AbstractPower {

    public static final String POWER_ID = MainModfile.makeID(SeptrinPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SeptrinPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        PowerIconMaker.setIcons(this, Septrin.class.getSimpleName());
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (/*!SpectrumizeAction.spectrumizing &&*/ !(card instanceof RedNeutralizer) && !(card instanceof BlueNeutralizer) && !(card instanceof YellowNeutralizer) && !(card instanceof GreenNeutralizer)) {
            flash();
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    SpectrumizeAction.addCards(card);
                    this.isDone = true;
                }
            });
        }
    }
}