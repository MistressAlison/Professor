package Professor.cards;

import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.ApplyDestabilizedMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class DuskyPowder extends AbstractEasyCard {
    public final static String ID = makeID(DuskyPowder.class.getSimpleName());

    public DuskyPowder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InfuseCardsInHandAction(1, new ApplyDestabilizedMod(magicNumber)));
    }

    @Override
    public void upp() {
        if (timesUpgraded == 1) {
            upgradeBaseCost(0);
        } else {
            upgradeMagicNumber(1);
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.GRAY, PURPLE), WHITE, mix(Color.GRAY, VERONICA), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return DuskyPowder.class.getSimpleName();
    }
}