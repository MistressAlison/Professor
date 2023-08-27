package Professor.cards;

import Professor.actions.ScaleAllByPredAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import java.util.ArrayList;
import java.util.Arrays;

import static Professor.MainModfile.makeID;

public class GiantClaws extends AbstractEasyCard {
    public final static String ID = makeID(GiantClaws.class.getSimpleName());
    public static final ArrayList<String> clawNames = new ArrayList<>();

    public GiantClaws() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 2;
        if (clawNames.isEmpty()) {
            clawNames.addAll(Arrays.asList(cardStrings.EXTENDED_DESCRIPTION).subList(1, cardStrings.EXTENDED_DESCRIPTION.length));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN.cpy(), Color.WHITE.cpy()), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        addToBot(new ScaleAllByPredAction(this, magicNumber, ScaleAllByPredAction.StatBoost.DAMAGE, c -> c.type == CardType.ATTACK && clawNames.stream().anyMatch(s -> CardModifierManager.onRenderTitle(c, c.name).contains(s))));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.BROWN, WHITE, Color.BROWN, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return GiantClaws.class.getSimpleName();
    }
}