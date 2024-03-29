package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import Professor.powers.AmalgamPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Amalgam extends AbstractEasyCard {
    public final static String ID = makeID(Amalgam.class.getSimpleName());

    public Amalgam() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
        MultiCardPreview.add(this, new RedNeutralizer(), new BlueNeutralizer(), new YellowNeutralizer(), new GreenNeutralizer());
        //addCustomKeyword(KeywordManager.CATALYST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        Wiz.applyToSelf(new AmalgamPower(p, 1));
        /*if (upgraded) {
            ArrayList<AbstractCard> adjacent = Wiz.getAdjacentCards(this);
            addToBot(new BetterSelectCardsInHandAction(adjacent.size(), SpectrumizeAction.TEXT[0], true, true, adjacent::contains, l -> {
                Collections.reverse(l);
                for (AbstractCard c : l) {
                    addToTop(new SpectrumizeAction(c));
                }
            }));
        } else {
            Wiz.forAdjacentCards(this, c -> addToBot(new SpectrumizeAction(c)));
        }*/
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        //uDesc();
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.SLATE, WHITE, mix(Color.SLATE, AZURE), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Amalgam.class.getSimpleName();
    }

    @Override
    public float itemScale() {
        return 0.85f;
    }
}