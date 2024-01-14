package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import Professor.vfx.BigExplosionVFX;
import Professor.vfx.SpotlightEnemyEffect;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class DawnGrimoire extends AbstractCreationCard {
    public final static String ID = makeID(DawnGrimoire.class.getSimpleName());

    public DawnGrimoire() {
        this(null);
    }

    public DawnGrimoire(ElementData data) {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.DAWN_GRIMOIRE);
        exhaust = true;
        tags.add(CardTags.HEALING);
        setDisplayRarity(CardRarity.RARE);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 3;
        isMultiDamage = true;
        if (data != null) {
            this.data = data;
            baseDamage += 3*data.r;
            damage = baseDamage;
            baseMagicNumber += data.y;
            magicNumber = baseMagicNumber;
        }
        //Worst: 8 AOE, 4 AOE Heal
        //Beat: 17 AOE, 7 AOE Heal
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
        //addToBot(new SFXAction("INTIMIDATE"));
        addToBot(new SFXAction("ORB_DARK_CHANNEL"));
        Wiz.forAllMonstersLiving(mon -> addToBot(new VFXAction(new BloodShotEffect(mon.hb.cX, mon.hb.cY, p.hb.cX, p.hb.cY, magicNumber/2))));
        addToBot(new VFXAction(new SpotlightEnemyEffect(m)));
        for (int i = 0 ; i < Wiz.getEnemies().size() ; i++) {
            addToBot(new VFXAction(new InversionBeamEffect(m.hb.cX)));
            addToBot(new BigExplosionVFX(m));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
        Wiz.forAllMonstersLiving(mon -> addToBot(new HealAction(p, p, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
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