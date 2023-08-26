package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.vfx.ColoredAngledFlashAtkImgEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Strike extends AbstractEasyCard {
    public final static String ID = makeID(Strike.class.getSimpleName());
    private static final Color L_RED = lighten(RED);
    private static final Color L_BLUE = lighten(BLUE);

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new SFXAction("ATTACK_FAST"));
            addToBot(new VFXAction(new ColoredAngledFlashAtkImgEffect(m.hb.cX, m.hb.cY, 180f, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, upgraded ? L_RED : WHITE, true), 0.1f));
            addToBot(new SFXAction("ATTACK_FAST"));
            addToBot(new VFXAction(new ColoredAngledFlashAtkImgEffect(m.hb.cX, m.hb.cY, 0f, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, upgraded ? L_BLUE : WHITE, true)));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        needsArtRefresh = true;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(GRAY, Color.FIREBRICK), WHITE, mix(GRAY, Color.FIREBRICK), BLACK, false);
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