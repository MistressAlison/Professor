package Professor.cards;

import Professor.vfx.ApplyShaderEffect;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.PhilosophersPower;
import Professor.shaders.SobelShader;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class OldenPhilosophersStone extends AbstractEasyCard {
    public final static String ID = makeID(OldenPhilosophersStone.class.getSimpleName());

    public OldenPhilosophersStone() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(BaseModCardTags.FORM);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("HEART_BEAT", 0.1f));
        //addToBot(new VFXAction(new ApplyShaderEffect(SobelShader.get(), 0.5f)));
        Wiz.applyToSelf(new PhilosophersPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        isEthereal = false;
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, SCARLET, mix(CYAN, Color.LIGHT_GRAY), SCARLET, mix(CYAN, Color.LIGHT_GRAY), false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return OldenPhilosophersStone.class.getSimpleName();
    }
}