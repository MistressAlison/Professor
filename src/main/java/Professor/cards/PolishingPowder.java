package Professor.cards;

import Professor.actions.ModifyCardsInHandAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class PolishingPowder extends AbstractEasyCard {
    public final static String ID = makeID(PolishingPowder.class.getSimpleName());

    public PolishingPowder() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ModifyCardsInHandAction(p.hand.size(), l -> {
            for (AbstractCard c : l) {
                c.setCostForTurn(c.costForTurn - magicNumber);
            }
        }));
    }

    @Override
    public void upp() {
        if (timesUpgraded >= 4) {
            upgradeMagicNumber(1);
        } else if (timesUpgraded == 3) {
            upgradeBaseCost(0);
        } else if (timesUpgraded == 2) {
            upgradeBaseCost(1);
        } else {
            upgradeBaseCost(2);
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GOLDENROD, WHITE, Color.GOLDENROD, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return PolishingPowder.class.getSimpleName();
    }
}