package Professor.cards;

import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.GainBlockMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Fragile extends AbstractEasyCard {
    public final static String ID = makeID(Fragile.class.getSimpleName());

    public Fragile() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InfuseCardsInHandAction(secondMagic, new GainBlockMod(magicNumber)));
        //addToBot(new InfuseSpecificCardsAction(Wiz.getAdjacentCards(this), new GainBlockMod(magicNumber)));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.SKY, WHITE, Color.SKY, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Fragile.class.getSimpleName();
    }
}