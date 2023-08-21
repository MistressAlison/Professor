package Professor.cards;

import Professor.actions.ThrowObjectAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.UnstablePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class DarkCrystalFragment extends AbstractEasyCard {
    public final static String ID = makeID(DarkCrystalFragment.class.getSimpleName());

    public DarkCrystalFragment() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseDamage = damage = 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            //addToBot(new ThrowObjectAction(itemArt(), 1/3f, m.hb, Color.PURPLE));
            addToBot(new ThrowObjectAction(itemArt(), 1/3f, m.hb, Color.PURPLE));
        }
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        Wiz.applyToEnemy(m, new UnstablePower(m, magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        //upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.YELLOW, Color.ORANGE, darken(Color.PURPLE), Color.BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return DarkCrystalFragment.class.getSimpleName();
    }

    @Override
    public float itemScale() {
        return 0.7f;
    }
}