package Professor.cutStuff.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;

import static Professor.MainModfile.makeID;

public class DawnGrimoire extends AbstractCreationCard {
    public final static String ID = makeID(DawnGrimoire.class.getSimpleName());

    public DawnGrimoire() {
        this(null);
    }

    public DawnGrimoire(ElementData data) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.DAWN_GRIMOIRE);
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 4;
        if (data != null) {
            baseDamage += 4*data.r;
            damage = baseDamage;
            baseMagicNumber += data.y;
            magicNumber = baseMagicNumber;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new DawnGrimoire(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new SFXAction("ORB_DARK_CHANNEL"));
        addToBot(new VFXAction(new SpotlightEffect()));
        addToBot(new SFXAction("INTIMIDATE"));
        Wiz.forAllMonstersLiving(mon -> addToBot(new VFXAction(new BloodShotEffect(mon.hb.cX, mon.hb.cY, p.hb.cX, p.hb.cY, magicNumber/2))));
        //addToBot(BindingHelper.makeAction(Collections.singletonList(new DawnGrimDamage(magicNumber)), new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE)));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        Wiz.forAllMonstersLiving(mon -> addToBot(new HealAction(p, p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
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
        return DawnGrimoire.class.getSimpleName();
    }
}