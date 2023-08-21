package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.BlackWaterPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class BlackWater extends AbstractEasyCard {
    public final static String ID = makeID(BlackWater.class.getSimpleName());

    public BlackWater() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        //tags.add(CustomTags.PROF_REACTANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BlackWaterPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
        //isInnate = true;
        //uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(WHITE, BLACK), WHITE, mix(WHITE, BLACK), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return BlackWater.class.getSimpleName();
    }
}