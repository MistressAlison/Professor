package Professor.cards.tokens;

import Professor.cards.abstracts.AbstractTokenCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class RedNeutralizer extends AbstractTokenCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(RedNeutralizer.class.getSimpleName());

    public RedNeutralizer() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 4;
        PurgeField.purge.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
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
        return RedNeutralizer.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        addToBot(new NewQueueCardAction(makeStatEquivalentCopy(), true, false, true));
        //addToBot(new DamageAllEnemiesAction(Wiz.adp(), DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
        return false;
    }
}