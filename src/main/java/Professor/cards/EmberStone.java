package Professor.cards;

import Professor.actions.ThrowObjectAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Professor.MainModfile.makeID;

public class EmberStone extends AbstractEasyCard {
    public final static String ID = makeID(EmberStone.class.getSimpleName());

    public EmberStone() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 12;
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new ThrowObjectAction(EmberStone.class.getSimpleName(), 1/3f, m.hb, ORANGE));
        }
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(2);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int baseBase = baseDamage;
        int lastMagic = magicNumber;
        if (mo.hasPower(VulnerablePower.POWER_ID)) {
            baseDamage += magicNumber;
        }
        super.calculateCardDamage(mo);
        baseDamage = baseBase;
        isDamageModified = baseDamage != damage;
        if (lastMagic != magicNumber) {
            calculateCardDamage(mo);
        }
    }

    public void triggerOnGlowCheck() {
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.getEnemies().stream().anyMatch(mon -> !mon.isDeadOrEscaped() && mon.hasPower(VulnerablePower.POWER_ID))) {
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, ORANGE, WHITE, ORANGE, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return EmberStone.class.getSimpleName();
    }
}