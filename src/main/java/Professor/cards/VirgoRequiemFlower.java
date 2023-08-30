package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashSet;

import static Professor.MainModfile.makeID;

public class VirgoRequiemFlower extends AbstractEasyCard {
    public final static String ID = makeID(VirgoRequiemFlower.class.getSimpleName());

    public VirgoRequiemFlower() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        HashSet<AbstractCard> checked = new HashSet<>();
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (!checked.contains(c) && (c.type == CardType.POWER || c.type == CardType.ATTACK)) {
                g.group.add(c.makeStatEquivalentCopy());
                checked.add(c);
            }
        }
        g.sortAlphabetically(false);
        g.sortByRarity(false);
        addToBot(new FetchAction(g, c -> true, magicNumber, l -> {
            //Maybe do something?
        }));
    }

    @Override
    public void upp() {
        if (timesUpgraded >= 2) {
            upgradeMagicNumber(1);
        } else {
            upgradeBaseCost(0);
        }
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(GREEN, Color.GRAY), AZURE, mix(BRIGHT_GREEN, Color.GRAY), AZURE, false);
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