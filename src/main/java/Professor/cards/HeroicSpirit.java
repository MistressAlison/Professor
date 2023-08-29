package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.OnUseEnergyPatches;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class HeroicSpirit extends AbstractEasyCard implements OnUseEnergyPatches.OnUseEnergyObject {
    public final static String ID = makeID(HeroicSpirit.class.getSimpleName());

    public HeroicSpirit() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        isEthereal = true;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void upp() {
        isEthereal = false;
        uDesc();
    }

    @Override
    public void onUseEnergy(int amount) {
        if (amount > 0 && Wiz.adp().hand.contains(this)) {
            flash();
            addToBot(new DrawCardAction(magicNumber));
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.GRAY, PURPLE), WHITE, mix(Color.GRAY, AMETHYST), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return HeroicSpirit.class.getSimpleName();
    }
}