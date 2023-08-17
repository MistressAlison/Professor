package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Professor.MainModfile.makeID;

public class RainbowNeutralizer extends AbstractEasyCard {
    public final static String ID = makeID(RainbowNeutralizer.class.getSimpleName());

    public RainbowNeutralizer() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        selfRetain = true;
        baseDamage = damage = 13;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        //upgradeMagicNumber(3);
    }

    @Override
    public void triggerWhenDrawn() {
        flash();
        Wiz.applyToSelf(new VigorPower(Wiz.adp(), magicNumber));
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(RED), YELLOW, pastel(Color.PINK), pastel(YELLOW), false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return RainbowNeutralizer.class.getSimpleName();
    }
}