package Professor.cutStuff.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.powers.DragonBloodPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class DragonBlood extends AbstractEasyCard {
    public final static String ID = makeID(DragonBlood.class.getSimpleName());

    public DragonBlood() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        //TODO elements not being picked up
        tags.add(CustomTags.PROF_BOLT);
        tags.add(CustomTags.PROF_ICE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new DragonBloodPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //isInnate = true;
        //uDesc();
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(RED,Color.GRAY), SCARLET, mix(RED,Color.GRAY), SCARLET, false);
    }

    @Override
    public String cardArtCopy() {
        return Omega.ID;
    }

    @Override
    public String itemArt() {
        return DragonBlood.class.getSimpleName();
    }
}