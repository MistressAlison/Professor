package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class SaintsDiamond extends AbstractEasyCard implements OnUseInSynthesisCard {
    public final static String ID = makeID(SaintsDiamond.class.getSimpleName());

    public SaintsDiamond() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 15;
        baseMagicNumber = magicNumber = 1;
        //tags.add(CustomTags.PROF_CATALYST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(5);
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
        return SaintsDiamond.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        this.modifyCostForCombat(-1);
        superFlash();
        return false;
    }
}