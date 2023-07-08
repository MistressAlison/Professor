package Professor.cards.creations;

import Professor.actions.ApplyPowerActionWithFollowup;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Professor.MainModfile.makeID;

public class ExplosiveUni extends AbstractCreationCard {
    public final static String ID = makeID(ExplosiveUni.class.getSimpleName());

    public ExplosiveUni() {
        this(null);
    }

    public ExplosiveUni(ElementData data) {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.EXPLOSIVE_UNI);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 1;
        if (data != null) {
            this.data = data;
            baseDamage += 2*data.r;
            damage = baseDamage;
            baseMagicNumber += data.b;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new ExplosiveUni(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (magicNumber > 0) {
            addToBot(new ApplyPowerActionWithFollowup(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber)), new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber))));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.BROWN, WHITE, Color.BROWN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return ExplosiveUni.class.getSimpleName();
    }
}