package Professor.cards;

import Professor.actions.EasyXCostAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class MysteriousBroom extends AbstractEasyCard {
    public final static String ID = makeID(MysteriousBroom.class.getSimpleName());

    public MysteriousBroom() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (base, params) -> {
            int effect = base;
            for (int i : params) {
                effect += i;
            }
            if (effect > 0) {
                addToTop(new DrawCardAction(effect, new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (AbstractCard c : DrawCardAction.drawnCards) {
                            c.setCostForTurn(0);
                        }
                        this.isDone = true;
                    }
                }));
            }
            return true;
        }, magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, BLACK, WHITE, RED, mix(Color.GOLD, Color.GRAY), false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return MysteriousBroom.class.getSimpleName();
    }
}