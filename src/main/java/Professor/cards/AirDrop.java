package Professor.cards;

import Professor.actions.GatherAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AirDrop extends AbstractEasyCard {
    public final static String ID = makeID(AirDrop.class.getSimpleName());

    public AirDrop() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GatherAction(magicNumber, c -> c instanceof AbstractRecipeCard, true, upgraded));
    }

    @Override
    public void upp() {
        if (timesUpgraded > 2) {
            upgradeMagicNumber(1);
        } else if (timesUpgraded == 2) {
            upgradeBaseCost(0);
        } else {
            uDesc();
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.LIGHT_GRAY, BLACK, Color.LIGHT_GRAY, pastel(BLUE), false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return AirDrop.class.getSimpleName();
    }
}