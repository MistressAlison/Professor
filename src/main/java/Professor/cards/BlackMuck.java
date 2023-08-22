package Professor.cards;

import Professor.actions.SpectrumizeAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.UnstablePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import Professor.vfx.ColoredWaterDropEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class BlackMuck extends AbstractEasyCard {
    public final static String ID = makeID(BlackMuck.class.getSimpleName());

    public BlackMuck() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new SFXAction("POWER_POISON", 0.2f));
            addToBot(new VFXAction(new ColoredWaterDropEffect(m.hb.cX, m.hb.cY, Color.DARK_GRAY.cpy()), 0.2f));
        }
        Wiz.applyToEnemy(m, new UnstablePower(m, magicNumber));
        addToBot(new SpectrumizeAction(secondMagic, !upgraded));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        //upgradeSecondMagic(1);
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.BLACK, WHITE, Color.BLACK, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return BlackMuck.class.getSimpleName();
    }
}