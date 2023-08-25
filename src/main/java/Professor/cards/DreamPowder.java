package Professor.cards;

import Professor.actions.InfuseSpecificCardsAction;
import Professor.cardmods.DealDamageMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class DreamPowder extends AbstractEasyCard {
    public final static String ID = makeID(DreamPowder.class.getSimpleName());

    public DreamPowder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //baseBlock = block = 13;
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 5;
        //tags.add(CustomTags.PROF_REACTANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //blck();
        //Wiz.applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
        addToBot(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new InfuseSpecificCardsAction(DrawCardAction.drawnCards, new DealDamageMod(secondMagic)));
                this.isDone = true;
            }
        }));
    }

    @Override
    public void upp() {
        //upgradeBlock(4);
        upgradeMagicNumber(1);
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
        return DreamPowder.class.getSimpleName();
    }

    @Override
    public float itemScale() {
        return 1.1f;
    }
}