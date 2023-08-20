package Professor.cards;

import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.GainBlockMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Eiche extends AbstractEasyCard {
    public final static String ID = makeID(Eiche.class.getSimpleName());

    public Eiche() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 6;
        baseMagicNumber = magicNumber = 3;
        //baseSecondMagic = secondMagic = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        //Wiz.applyToSelf(new BracedPower(p, magicNumber));
        addToBot(new InfuseCardsInHandAction(1, new GainBlockMod(magicNumber)));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
        //upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, AZURE, WHITE, AZURE, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Eiche.class.getSimpleName();
    }
}