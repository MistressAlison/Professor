package Professor.cards;

import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.DealDamageMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AncientUni extends AbstractEasyCard {
    public final static String ID = makeID(AncientUni.class.getSimpleName());

    public AncientUni() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
        tags.add(CustomTags.PROF_UNI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new InfuseCardsInHandAction(1, new DealDamageMod(magicNumber)));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, BLACK, WHITE, WHITE, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return AncientUni.class.getSimpleName();
    }
}