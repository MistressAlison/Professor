package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.interfaces.GlowAdjacentCard;
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

public class FlamingBlackSand extends AbstractEasyCard implements GlowAdjacentCard {
    public final static String ID = makeID(FlamingBlackSand.class.getSimpleName());
    private static final Color c = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();

    public FlamingBlackSand() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        boolean energy = false;
        for (AbstractCard c : Wiz.getAdjacentCards(this)) {
            if (ArchetypeHelper.isFire(c)) {
                energy = true;
            }
        }
        if (energy) {
            addToBot(new GainEnergyAction(magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        //upgradeMagicNumber(1);
    }

    public void triggerOnGlowCheck() {
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (Wiz.getAdjacentCards(this).stream().anyMatch(ArchetypeHelper::isFire)) {
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

    @Override
    public boolean glowAdjacent(AbstractCard card) {
        return GlowAdjacentCard.super.glowAdjacent(card) && ArchetypeHelper.isFire(card);
    }

    @Override
    public Color getGlowColor(AbstractCard card) {
        return c;
    }
}