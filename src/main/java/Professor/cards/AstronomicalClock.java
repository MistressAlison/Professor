package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.powers.AetherEssencePower;
import Professor.powers.AstronomicalClockPower;
import Professor.ui.SynthesisPanel;
import Professor.util.CardArtRoller;
import Professor.util.GameSpeedController;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AstronomicalClock extends AbstractEasyCard {
    public final static String ID = makeID(AstronomicalClock.class.getSimpleName());

    public AstronomicalClock() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!SynthesisPanel.items.isEmpty()) {
            addToBot(new GameSpeedController.SlowMotionAction(4f, 1.5f, false));
            addToBot(new SFXAction("POWER_TIME_WARP", 0.2f));
        }
        Wiz.applyToSelf(new AstronomicalClockPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
        //isInnate = true;
        //uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, ORANGE, YELLOW, mix(GRAY, CERULEAN), darken(SILVER), false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return AstronomicalClock.class.getSimpleName();
    }
}