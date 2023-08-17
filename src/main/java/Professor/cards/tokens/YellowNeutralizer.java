package Professor.cards.tokens;

import Professor.cards.abstracts.AbstractTokenCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class YellowNeutralizer extends AbstractTokenCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(YellowNeutralizer.class.getSimpleName());

    public YellowNeutralizer() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 4;
        //tags.add(CustomTags.PROF_REACTANT);
        PurgeField.purge.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Wiz.applyToSelf(new VigorPower(p, magicNumber));
        //Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GOLD, WHITE, Color.GOLD, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return YellowNeutralizer.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        addToBot(new NewQueueCardAction(makeStatEquivalentCopy(), true, false, true));
        //Wiz.applyToSelf(new VigorPower(Wiz.adp(), secondMagic));
        return false;
    }
}