package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class VirgoRequiemFlower extends AbstractEasyCard {
    public final static String ID = makeID(VirgoRequiemFlower.class.getSimpleName());

    public VirgoRequiemFlower() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FetchAction(p.exhaustPile, c -> !(c instanceof VirgoRequiemFlower), 1, l -> {
            //Maybe do something?
        }));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(GREEN, Color.GRAY), AZURE, mix(GREEN, Color.GRAY), AZURE, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return VirgoRequiemFlower.class.getSimpleName();
    }
}