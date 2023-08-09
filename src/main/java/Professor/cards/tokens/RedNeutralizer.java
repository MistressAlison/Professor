package Professor.cards.tokens;

import Professor.cards.abstracts.AbstractTokenCard;
import Professor.util.CardArtRoller;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class RedNeutralizer extends AbstractTokenCard {
    public final static String ID = makeID(RedNeutralizer.class.getSimpleName());

    public RedNeutralizer() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 5;
        //tags.add(CustomTags.PROF_REACTANT);
        PurgeField.purge.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
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
}