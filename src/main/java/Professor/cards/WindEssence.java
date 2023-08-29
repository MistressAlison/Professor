package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.powers.WindEssencePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class WindEssence extends AbstractEasyCard {
    public final static String ID = makeID(WindEssence.class.getSimpleName());

    public WindEssence() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(CustomTags.PROF_WIND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new WindEssencePower(p, magicNumber));
    }

    @Override
    public void upp() {
        isInnate = true;
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, HARLEQUIN, WHITE, HARLEQUIN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return WindEssence.class.getSimpleName();
    }
}