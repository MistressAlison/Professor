package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.UnstablePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import Professor.vfx.ColoredSmokeBombEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Professor.MainModfile.makeID;

public class RotwoodMiasma extends AbstractEasyCard {
    public final static String ID = makeID(RotwoodMiasma.class.getSimpleName());

    public RotwoodMiasma() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 2;
        //baseSecondMagic = secondMagic = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> addToBot(new VFXAction(new ColoredSmokeBombEffect(mon.hb.cX, mon.hb.cY, AbstractEasyCard.darken(Color.PURPLE)))));
        Wiz.forAllMonstersLiving(mon -> {
            Wiz.applyToEnemy(mon, new UnstablePower(mon, magicNumber));
            Wiz.applyToEnemy(mon, new WeakPower(mon, magicNumber, false));
        });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        //upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.PURPLE, BLACK, Color.PURPLE, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return RotwoodMiasma.class.getSimpleName();
    }
}