package Professor.cards;

import Professor.actions.SpectrumizeAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import Professor.util.CardArtRoller;
import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AncientBlueWater extends AbstractEasyCard {
    public final static String ID = makeID(AncientBlueWater.class.getSimpleName());

    public AncientBlueWater() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        MultiCardPreview.add(this, new RedNeutralizer(), new BlueNeutralizer(), new YellowNeutralizer(), new GreenNeutralizer());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpectrumizeAction(BaseMod.MAX_HAND_SIZE));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, darken(Color.PURPLE), BLACK, BLUE, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return AncientBlueWater.class.getSimpleName();
    }
}