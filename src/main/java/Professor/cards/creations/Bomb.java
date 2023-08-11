package Professor.cards.creations;

import Professor.actions.ThrowObjectAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.vfx.BigExplosionVFX;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class Bomb extends AbstractCreationCard {
    public final static String ID = makeID(Bomb.class.getSimpleName());

    public Bomb() {
        this(null);
    }

    public Bomb(ElementData data) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.BOMB);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 6; // AOE
        baseSecondDamage = secondDamage = 10; // Single
        manualD2 = true;
        if (data != null) {
            baseDamage += 3*data.y;
            damage = baseDamage;
            baseSecondDamage += 5*data.r;
            secondDamage = baseSecondDamage;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new Bomb(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new ThrowObjectAction("Bomb", 0.33f, m.hb, Color.RED, false));
            addToBot(new BigExplosionVFX(m));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, secondDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void applyPowers() {
        int baseBase = baseDamage;
        isMultiDamage = false;
        baseDamage = baseSecondDamage;
        super.applyPowers();
        secondDamage = damage;
        isSecondDamageModified = secondDamage != baseSecondDamage;
        baseDamage = baseBase;
        isMultiDamage = true;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int baseBase = baseDamage;
        isMultiDamage = false;
        baseDamage = baseSecondDamage;
        super.calculateCardDamage(mo);
        secondDamage = damage;
        isSecondDamageModified = secondDamage != baseSecondDamage;
        baseDamage = baseBase;
        isMultiDamage = true;
        super.calculateCardDamage(null);
    }

    @Override
    public void upp() {
        upgradeSecondDamage(5);
        upgradeDamage(3);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, RED, WHITE, RED, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Bomb.class.getSimpleName();
    }
}