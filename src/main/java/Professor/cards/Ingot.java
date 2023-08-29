package Professor.cards;

import Professor.actions.ThrowObjectAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.damageMods.PiercingDamage;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Professor.MainModfile.makeID;

public class Ingot extends AbstractEasyCard {
    public final static String ID = makeID(Ingot.class.getSimpleName());

    public Ingot() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 10;
        baseMagicNumber = magicNumber = 1;
        DamageModifierManager.addModifier(this, new PiercingDamage());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new ThrowObjectAction(itemArt(), 2/5f, m.hb, GRAY));
        }
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        //addToBot(new RemoveAllBlockAction(m, p));
    }

    @Override
    public void upp() {
        //upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(PURPLE), WHITE, pastel(mix(DARK_GRAY, BRONZE)), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Ingot.class.getSimpleName();
    }
}