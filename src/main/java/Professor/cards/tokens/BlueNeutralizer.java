package Professor.cards.tokens;

import Professor.cards.abstracts.AbstractTokenCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.patches.CustomTags;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class BlueNeutralizer extends AbstractTokenCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(BlueNeutralizer.class.getSimpleName());

    public BlueNeutralizer() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = block = 6;
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
        tags.add(CustomTags.PROF_NOT_BOLT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(3);
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
        return BlueNeutralizer.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        addToBot(new NewQueueCardAction(makeStatEquivalentCopy(), true, false, true));
        //Wiz.applyToSelf(new BracedPower(Wiz.adp(), magicNumber));
        return false;
    }
}