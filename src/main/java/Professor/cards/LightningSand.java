package Professor.cards;

import Professor.actions.ModifyMagicAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class LightningSand extends AbstractEasyCard {
    public final static String ID = makeID(LightningSand.class.getSimpleName());

    public LightningSand() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        selfRetain = true;
        baseDamage = damage = 5;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < magicNumber ; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(3);
        upgradeDamage(2);
    }

    @Override
    public void onRetained() {
        flash();
        addToBot(new ModifyMagicAction(this.uuid, 1));
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(Color.PURPLE), BLACK, Color.YELLOW, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return LightningSand.class.getSimpleName();
    }
}