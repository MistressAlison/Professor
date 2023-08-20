package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class TorchGrass extends AbstractEasyCard {
    public final static String ID = makeID(TorchGrass.class.getSimpleName());

    public TorchGrass() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 2;
        if (CardCrawlGame.playerName.equals("rorDev") && MathUtils.random(4) == 0) {
            this.originalName = this.name = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeTitle();
        }
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new FetchAction(p.discardPile, magicNumber, l -> {
            for (AbstractCard c : l) {
                c.current_x = CardGroup.DISCARD_PILE_X;
                c.current_y = CardGroup.DISCARD_PILE_Y;
            }
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.FOREST, WHITE, Color.FOREST, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return TorchGrass.class.getSimpleName();
    }
}