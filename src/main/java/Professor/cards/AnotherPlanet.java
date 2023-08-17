package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.powers.AnotherPlanetPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AnotherPlanet extends AbstractEasyCard {
    public final static String ID = makeID(AnotherPlanet.class.getSimpleName());

    public AnotherPlanet() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        // TODO not being picked up as an element
        tags.add(CustomTags.PROF_BOLT);
        tags.add(CustomTags.PROF_WIND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AnotherPlanetPower(p, upgraded ? 1 : 0));
    }

    @Override
    public void upp() {
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(AZURE,Color.GRAY), AZURE, mix(AZURE,Color.GRAY), AZURE, false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return AnotherPlanet.class.getSimpleName();
    }
}