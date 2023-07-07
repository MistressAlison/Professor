package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.powers.BurnPower;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;
import java.util.List;

import static Professor.MainModfile.makeID;

public class Bomb extends AbstractCreationCard {
    public final static String ID = makeID(Bomb.class.getSimpleName());
    private List<TooltipInfo> tips;

    public Bomb() {
        this(null);
    }

    public Bomb(ElementData data) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
        isMultiDamage = true;
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 4;
        if (data != null) {
            baseDamage += 3*data.r;
            damage = baseDamage;
            baseMagicNumber += 2*data.y;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (tips == null) {
            tips = new ArrayList<>();
            tips.add(new TooltipInfo(BaseMod.getKeywordTitle(KeywordManager.BOMB), BaseMod.getKeywordDescription(KeywordManager.BOMB)));
        }
        return tips;
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new Bomb(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, true));
        addToBot(new DamageAllEnemiesAction(p, magicNumber, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE));
        //allDmg(AbstractGameAction.AttackEffect.FIRE);
        /*if (magicNumber > 0) {
            Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        }*/
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(2);
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
        return "Bomb";
    }
}