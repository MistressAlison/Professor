package Professor.cards.tokens;

import Professor.cards.abstracts.AbstractTokenCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class GreenNeutralizer extends AbstractTokenCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(GreenNeutralizer.class.getSimpleName());

    public GreenNeutralizer() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 2;
        //tags.add(CustomTags.PROF_REACTANT);
        PurgeField.purge.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        //addToBot(new DiscardAction(p, p, magicNumber, false, false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, GREEN, WHITE, GREEN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return GreenNeutralizer.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        addToBot(new NewQueueCardAction(makeStatEquivalentCopy(), true, false, true));
        /*for (int i = 0 ; i < secondMagic ; i++) {
            addToBot(new UpgradeRandomCardAction());
        }*/
        return false;
    }
}