package Professor.cutStuff;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.vfx.ColoredThrowDaggerEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class CrossDaggers extends AbstractEasyCard {
    public final static String ID = makeID(CrossDaggers.class.getSimpleName());

    public CrossDaggers() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ColoredThrowDaggerEffect(m.hb.cX, m.hb.cY, upgraded ? RED : Color.GRAY)));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE, true);
        if (m != null) {
            this.addToBot(new VFXAction(new ColoredThrowDaggerEffect(m.hb.cX, m.hb.cY, upgraded ? AZURE : Color.GRAY)));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        needsArtRefresh = true;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, WHITE, Color.GRAY, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return upgraded ? "Halfmoons2" : "CrossDaggers";
    }

    @Override
    public String rollerKey() {
        return upgraded ? cardID+"+" : cardID;
    }
}