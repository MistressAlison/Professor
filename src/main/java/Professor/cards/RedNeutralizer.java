package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class RedNeutralizer extends AbstractEasyCard {
    public final static String ID = makeID(RedNeutralizer.class.getSimpleName());

    public RedNeutralizer() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
        tags.add(CustomTags.PROF_REACTANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
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
        return "RedNeutralizer";
    }
}