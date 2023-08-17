package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.powers.AmberOilPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AmberOil extends AbstractEasyCard {
    public final static String ID = makeID(AmberOil.class.getSimpleName());

    public AmberOil() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        // TODO this should actually be a Wind card given its effect, but we want to render Bolt
        tags.add(CustomTags.PROF_BOLT);
        tags.add(CustomTags.PROF_NOT_WIND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AmberOilPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, ORANGE, WHITE, ORANGE, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return AmberOil.class.getSimpleName();
    }
}