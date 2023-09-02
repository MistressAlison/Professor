package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class MagmaPowder extends AbstractEasyCard {
    public final static String ID = makeID(MagmaPowder.class.getSimpleName());

    public MagmaPowder() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
        if (baseMagicNumber == -1) {
            baseMagicNumber = magicNumber = 0;
        }
        upgradeMagicNumber(3);
    }

    @Override
    public void applyPowers() {
        baseDamage = countCards();
        if (magicNumber > 0) {
            baseDamage += magicNumber;
        }
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = countCards();
        if (magicNumber > 0) {
            baseDamage += magicNumber;
        }
        super.calculateCardDamage(mo);
    }

    public int countCards() {
        return (int) Wiz.adp().hand.group.stream().filter(c -> c != this).count();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.GRAY, Color.RED), WHITE, mix(Color.GRAY, Color.RED), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return MagmaPowder.class.getSimpleName();
    }
}