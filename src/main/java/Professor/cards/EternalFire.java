package Professor.cards;

import Professor.actions.BetterSelectCardsInHandAction;
import Professor.actions.BetterTransformCardInHandAction;
import Professor.cards.abstracts.AbstractEasyCard;
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

public class EternalFire extends AbstractEasyCard {
    public final static String ID = makeID(EternalFire.class.getSimpleName());

    public EternalFire() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 5;
        //tags.add(CustomTags.PROF_REACTANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        Wiz.forAdjacentCards(this, c -> addToBot(new ExhaustSpecificCardAction(c, p.hand)));
        if (Wiz.getAdjacentCards(this).stream().anyMatch(c -> !(c instanceof Necronomicurse))) {
            addToBot(new SFXAction("CARD_BURN", 0.2F));
            Wiz.makeInHand(this.makeStatEquivalentCopy());
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
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
}