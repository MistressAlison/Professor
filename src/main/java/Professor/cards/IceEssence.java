package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.IceEssencePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class IceEssence extends AbstractEasyCard {
    public final static String ID = makeID(IceEssence.class.getSimpleName());

    public IceEssence() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new IceEssencePower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        isInnate = true;
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, CYAN, WHITE, CYAN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return IceEssence.class.getSimpleName();
    }
}