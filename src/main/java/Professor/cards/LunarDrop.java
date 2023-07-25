package Professor.cards;

import CardAugments.util.Wiz;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.ArchetypeHelper;
import Professor.util.CardArtRoller;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class LunarDrop extends AbstractEasyCard {
    public final static String ID = makeID(LunarDrop.class.getSimpleName());

    public LunarDrop() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = block = 5;
        baseInfo = info = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block * (countCards()-1)));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }

    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();
        baseInfo = baseBlock * countCards();
        info = block * countCards();
        isInfoModified = baseInfo != info;
    }

    private int countCards() {
        return (int) Wiz.cardsPlayedThisCombat().stream().filter(ArchetypeHelper::isIce).count();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, AZURE, WHITE, AZURE, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return LunarDrop.class.getSimpleName();
    }
}