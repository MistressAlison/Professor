package Professor.cards;

import Professor.actions.ExtractAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Professor.MainModfile.makeID;

public class OldScroll extends AbstractEasyCard {
    public final static String ID = makeID(OldScroll.class.getSimpleName());

    public OldScroll() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(p, new InflameEffect(p), 0.2F));
        addToBot(new ExtractAction(magicNumber));
        //addToBot(new DiscardAction(p, p, magicNumber, false));
    }

    @Override
    public void upp() {
        //upgradeBlock(3);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.BROWN, WHITE, Color.BROWN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return OldScroll.class.getSimpleName();
    }
}