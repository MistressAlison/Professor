package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.interfaces.InstantSynthesisCard;
import Professor.cards.interfaces.OnUseInSynthesisCard;
import Professor.patches.CustomTags;
import Professor.ui.SynthesisItem;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class LunarDrop extends AbstractEasyCard implements OnUseInSynthesisCard, InstantSynthesisCard {
    public final static String ID = makeID(LunarDrop.class.getSimpleName());

    public LunarDrop() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        selfRetain = true;
        // TODO Upgrade is Wind but not being detected.
        tags.add(CustomTags.PROF_WIND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upp() {
        uDesc();
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
        return LunarDrop.class.getSimpleName();
    }

    @Override
    public boolean onAdded(SynthesisItem item) {
        superFlash();
        if (upgraded && item.currentCreation.canUpgrade()) {
            item.currentCreation.upgrade();
        }
        return false;
    }

    @Override
    public boolean isInstant(SynthesisItem item) {
        return true;
    }
}