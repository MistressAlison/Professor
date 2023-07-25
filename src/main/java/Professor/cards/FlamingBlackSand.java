package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.ArchetypeHelper;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class FlamingBlackSand extends AbstractEasyCard {
    public final static String ID = makeID(FlamingBlackSand.class.getSimpleName());

    public FlamingBlackSand() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard c = Wiz.secondLastCardPlayed();
                if (c != null && ArchetypeHelper.isFire(c)) {
                    addToTop(new GainEnergyAction(magicNumber));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        //upgradeMagicNumber(1);
    }

    public void triggerOnGlowCheck() {
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (!Wiz.cardsPlayedThisCombat().isEmpty() && ArchetypeHelper.isFire(Wiz.lastCardPlayed())) {
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.FIREBRICK, WHITE, Color.FIREBRICK, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return FlamingBlackSand.class.getSimpleName();
    }
}