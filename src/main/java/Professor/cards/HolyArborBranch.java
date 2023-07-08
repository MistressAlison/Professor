package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.cards.tokens.HolyArborCrystal;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class HolyArborBranch extends AbstractEasyCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(HolyArborBranch.class.getSimpleName());

    public HolyArborBranch() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 7;
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new HolyArborCrystal();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        //addToBot(new ArmamentsAction(true));
        //addToBot(new BetterDiscardPileToHandAction(magicNumber));
        addToBot(new FetchAction(p.discardPile, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
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
        return HolyArborBranch.class.getSimpleName();
    }

    @Override
    public void onSynthesis(AbstractCard createdCard) {
        Wiz.makeInHand(new HolyArborCrystal());
    }
}