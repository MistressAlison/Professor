package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class CleanWater extends AbstractEasyCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(CleanWater.class.getSimpleName());

    public CleanWater() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 4;
        baseMagicNumber = magicNumber = 3;
        //tags.add(CustomTags.PROF_REACTANT);
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
        return CleanWater.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        baseBlock += magicNumber;
        return false;
    }
}