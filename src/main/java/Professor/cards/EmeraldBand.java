package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Professor.MainModfile.makeID;

public class EmeraldBand extends AbstractEasyCard {
    public final static String ID = makeID(EmeraldBand.class.getSimpleName());

    public EmeraldBand() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, 1, false));
        Wiz.applyToSelf(new VigorPower(p, magicNumber));
        //Wiz.applyToSelf(new StrengthPower(p, this.magicNumber));
        //Wiz.applyToSelf(new LoseStrengthPower(p, this.magicNumber));

        //0 cost discard 1(2) gain 1(2) energy?
        //1 cost discard and buff
        //1 cost move a card and reduce cost? Screens stinky in starter deck
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.FOREST, WHITE, Color.FOREST, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return EmeraldBand.class.getSimpleName();
    }
}