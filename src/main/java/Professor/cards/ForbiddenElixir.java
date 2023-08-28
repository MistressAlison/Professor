package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.ForbiddenElixirPower;
import Professor.powers.UnstablePower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import Professor.vfx.ColoredSmokeBombEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static Professor.MainModfile.makeID;

public class ForbiddenElixir extends AbstractEasyCard {
    public final static String ID = makeID(ForbiddenElixir.class.getSimpleName());

    public ForbiddenElixir() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new SFXAction("ATTACK_PIERCING_WAIL", 0.2f));
            addToBot(new VFXAction(new ShockWaveEffect(m.hb.cX, m.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC)));
            addToBot(new VFXAction(new ColoredSmokeBombEffect(m.hb.cX, m.hb.cY, Settings.GREEN_TEXT_COLOR)));
        }
        Wiz.applyToEnemy(m, new UnstablePower(m, magicNumber));
        Wiz.applyToEnemy(m, new ForbiddenElixirPower(m, p, 1));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
        //upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.BLACK, Color.FOREST, Color.BLACK, Color.FOREST, false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return "ForbiddenElixir2";
    }
}