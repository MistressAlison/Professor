package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static Professor.MainModfile.makeID;

public class IceBomb extends AbstractCreationCard {
    public final static String ID = makeID(IceBomb.class.getSimpleName());

    public IceBomb() {
        this(null);
    }

    public IceBomb(ElementData data) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 1;
        if (data != null) {
            baseDamage += 3*data.b;
            damage = baseDamage;
            baseMagicNumber += data.g;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new IceBomb(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BlizzardEffect(damage/3, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new WeakPower(mon, magicNumber, false)));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, CYAN, WHITE, CYAN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return IceBomb.class.getSimpleName();
    }
}