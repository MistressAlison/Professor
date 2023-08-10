package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Professor.MainModfile.makeID;

public class MagmaPowder extends AbstractEasyCard {
    public final static String ID = makeID(MagmaPowder.class.getSimpleName());

    public MagmaPowder() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        selfRetain = true;
        baseDamage = damage = 15;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }

    @Override
    public void triggerWhenDrawn() {
        flash();
        Wiz.applyToSelf(new VigorPower(Wiz.adp(), magicNumber));
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(Color.PURPLE), WHITE, mix(RED, ORANGE), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return MagmaPowder.class.getSimpleName();
    }
}