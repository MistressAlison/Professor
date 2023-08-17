package Professor.cutStuff.cards;

import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.ApplyPoisonMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static Professor.MainModfile.makeID;

public class PoisonGrass extends AbstractEasyCard {
    public final static String ID = makeID(PoisonGrass.class.getSimpleName());

    public PoisonGrass() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new PoisonPower(m, p, magicNumber));
        addToBot(new InfuseCardsInHandAction(1, new ApplyPoisonMod(magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.PURPLE, WHITE, Color.PURPLE, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return PoisonGrass.class.getSimpleName();
    }
}