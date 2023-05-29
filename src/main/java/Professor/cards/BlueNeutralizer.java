package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class BlueNeutralizer extends AbstractEasyCard {
    public final static String ID = makeID(BlueNeutralizer.class.getSimpleName());

    public BlueNeutralizer() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 4;
        tags.add(CustomTags.PROF_REACTANT);
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
        return "BlueNeutralizer";
    }
}