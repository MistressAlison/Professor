package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class HolyArborBranch extends AbstractEasyCard {
    public final static String ID = makeID(HolyArborBranch.class.getSimpleName());

    public HolyArborBranch() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 10;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.SKY, WHITE, Color.SKY, WHITE, false);
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        int index = Wiz.adp().hand.group.indexOf(this);
        if (index != -1) {
            if (index >= 1) {
                Wiz.adp().hand.group.get(index-1).retain = true;
            }
            if (index < Wiz.adp().hand.group.size()-1) {
                Wiz.adp().hand.group.get(index+1).retain = true;
            }
        }
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return HolyArborBranch.class.getSimpleName();
    }
}