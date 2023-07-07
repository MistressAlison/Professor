package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.BurnPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class EternalFire extends AbstractEasyCard {
    public final static String ID = makeID(EternalFire.class.getSimpleName());

    public EternalFire() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 5;
        //tags.add(CustomTags.PROF_CATALYST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(makeStatEquivalentCopy(), 1, false, true, false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, RED, WHITE, RED, BLACK, false);
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