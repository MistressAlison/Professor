package Professor.powers;

import Professor.MainModfile;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.PowerIconMaker;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class DestabilizedPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(DestabilizedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Color hpColor = AbstractEasyCard.pastel(Color.PURPLE);
    public AbstractCreature source;

    // TODO ideas:
    //Stores damage received, explodes at the start of its next turn?
    public DestabilizedPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.source = source;
        this.isTurnBased = true;
        PowerIconMaker.setIcons(this, "MosaicHope");
        updateDescription();
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            damage *= 1.25f;
        }
        return damage;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToBot(new VFXAction(new ExplosionSmallEffect(owner.hb.cX, owner.hb.cY)));
            addToBot(new DamageAllButOneEnemyAction(source, owner, DamageInfo.createDamageMatrix(damageAmount/2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        }
        return damageAmount;
    }

    @Override
    public void atEndOfRound() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    /*public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            addToBot(new VFXAction(new ViceCrushEffect(owner.hb.cX, owner.hb.cY), 0.5F));
            addToBot(new DestabilizeDamageAction(owner, source, amount, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    public void onDeath() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && owner.currentHealth <= 0) {
            addToBot(new AnimateShakeAction(owner, 0.4f, 0.2f));
            addToBot(new BigExplosionVFX(owner));
            addToBot(new DamageAllEnemiesAction(source, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }*/

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    /*@Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return hpColor;
    }*/
}