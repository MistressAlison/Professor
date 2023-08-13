package Professor.cutStuff.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.vfx.ColoredFlyingDaggerEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Volley extends AbstractEasyCard {
    public final static String ID = makeID(Volley.class.getSimpleName());

    public Volley() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ALL_ENEMY);
        baseDamage = damage = 4;
        //baseMagicNumber = magicNumber = 2;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int k = 0 ; k < 5 ; k++) {
            addToBot(new VFXAction(new ColoredFlyingDaggerEffect(p.hb.cX, p.hb.cY, MathUtils.random(20f)-10f, AbstractDungeon.getMonsters().shouldFlipVfx(), upgraded ? Color.GOLDENROD : Color.WHITE)));
        }
        allDmg(AbstractGameAction.AttackEffect.NONE);
        //Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new ExposedPower(mon, magicNumber)));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        //upgradeMagicNumber(1);
        needsArtRefresh = true;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, WHITE, Color.GRAY, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Zap.ID;
    }

    @Override
    public String itemArt() {
        return upgraded ? "WindBlaster2" : "Kurzbogen2";
    }

    @Override
    public String rollerKey() {
        return upgraded ? cardID+"+" : cardID;
    }
}