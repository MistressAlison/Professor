package Professor.powers;

import Professor.MainModfile;
import Professor.powers.interfaces.CheatCostPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AetherFormPower extends AbstractPower implements CheatCostPower {

    public static final String POWER_ID = MainModfile.makeID(AetherFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    private int triggers;

    public AetherFormPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("controlled_change");
        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        this.greenColor.a = c.a;
        c = this.greenColor;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount-triggers), x, y, this.fontScale, c);
    }

    @Override
    public void atStartOfTurn() {
        triggers = 0;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public boolean canAfford(AbstractCard card) {
        return amount > triggers;
    }

    @Override
    public void onActivate(AbstractCard card) {
        triggers++;
    }
}