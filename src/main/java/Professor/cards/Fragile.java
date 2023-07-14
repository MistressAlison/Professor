package Professor.cards;

import Professor.actions.ApplyCardModifierAction;
import Professor.actions.InfuseCardsInHandAction;
import Professor.actions.ModifyCardsInHandAction;
import Professor.cardmods.GainBlockMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Fragile extends AbstractEasyCard {
    public final static String ID = makeID(Fragile.class.getSimpleName());

    public Fragile() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InfuseCardsInHandAction(p.hand.size(), new GainBlockMod(magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
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