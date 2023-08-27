package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.interfaces.GlowAdjacentCard;
import Professor.powers.CardToHandPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class EternalFire extends AbstractEasyCard implements GlowAdjacentCard {
    public final static String ID = makeID(EternalFire.class.getSimpleName());
    private static final Color c = Color.RED.cpy();

    public EternalFire() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 7;
        //tags.add(CustomTags.PROF_REACTANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        Wiz.forAdjacentCards(this, c -> addToBot(new ExhaustSpecificCardAction(c, p.hand)));
        if (Wiz.getAdjacentCards(this).stream().anyMatch(c -> !(c instanceof Necronomicurse))) {
            addToBot(new SFXAction("CARD_BURN", 0.2F));
            Wiz.applyToSelf(new CardToHandPower(p, 1, this.makeStatEquivalentCopy()));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.GRAY, AZURE), WHITE, mix(Color.GRAY, AZURE), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return EternalFire.class.getSimpleName();
    }

    @Override
    public Color getGlowColor(AbstractCard card) {
        return c;
    }
}