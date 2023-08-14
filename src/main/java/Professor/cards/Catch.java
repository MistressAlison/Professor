package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.red.Shockwave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;

import static Professor.MainModfile.makeID;

public class Catch extends AbstractEasyCard {
    public final static String ID = makeID(Catch.class.getSimpleName());

    public Catch() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        //baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new EntangleEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY)));
            addToBot(new SFXAction("POWER_ENTANGLED", 0.05f));
        }
        //dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
    }

    @Override
    public void upp() {
        //upgradeDamage(1);
        upgradeMagicNumber(1);
        needsArtRefresh = true;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, WHITE, Color.GOLD, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Shockwave.ID;
    }

    @Override
    public String itemArt() {
        return upgraded ? "MasterBugNet" : "BugNet";
    }

    @Override
    public String rollerKey() {
        return upgraded ? cardID+"+" : cardID;
    }
}