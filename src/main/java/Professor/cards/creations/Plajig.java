package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.powers.ExposedPower;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.ArrayList;
import java.util.List;

import static Professor.MainModfile.makeID;

public class Plajig extends AbstractCreationCard {
    public final static String ID = makeID(Plajig.class.getSimpleName());
    private List<TooltipInfo> tips;

    public Plajig() {
        this(null);
    }

    public Plajig(ElementData data) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 4;
        if (data != null) {
            baseDamage += 3*data.y;
            damage = baseDamage;
            baseMagicNumber += 2*data.r;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (tips == null) {
            tips = new ArrayList<>();
            tips.add(new TooltipInfo(BaseMod.getKeywordTitle(KeywordManager.PLAJIG), BaseMod.getKeywordDescription(KeywordManager.PLAJIG)));
        }
        return tips;
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new Plajig(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new SFXAction("THUNDERCLAP", 0.05F));
            addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, true));
        Wiz.applyToEnemy(m, new ExposedPower(m, magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, YELLOW, WHITE, YELLOW, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Plajig.class.getSimpleName();
    }
}