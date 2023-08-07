package Professor.cards;

import Professor.actions.ThrowCrystalAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SmallCrystals extends AbstractEasyCard {
    public final static String ID = makeID(SmallCrystals.class.getSimpleName());

    public SmallCrystals() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 3;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ThrowCrystalAction(this, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeDamage(2);
        upgradeMagicNumber(1);
        needsArtRefresh = true;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, YELLOW, Color.ORANGE, lighten(Color.PINK), pastel(AZURE), false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return SmallCrystals.class.getSimpleName();
    }
}