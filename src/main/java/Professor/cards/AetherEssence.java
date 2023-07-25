package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.AetherFormPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class AetherEssence extends AbstractEasyCard {
    public final static String ID = makeID(AetherEssence.class.getSimpleName());

    public AetherEssence() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(BaseModCardTags.FORM);
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AetherFormPower(p, magicNumber));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        isEthereal = false;
        uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.PINK, WHITE), mix(CYAN, Color.LIGHT_GRAY), mix(Color.PINK, WHITE), mix(CYAN, Color.LIGHT_GRAY), false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return AetherEssence.class.getSimpleName();
    }
}