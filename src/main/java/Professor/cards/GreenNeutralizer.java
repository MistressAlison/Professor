package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Professor.MainModfile.makeID;

public class GreenNeutralizer extends AbstractEasyCard {
    public final static String ID = makeID(GreenNeutralizer.class.getSimpleName());

    public GreenNeutralizer() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(CustomTags.PROF_REACTANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new DiscardAction(p, p, magicNumber, false, false));
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
        return "GreenNeutralizer";
    }
}