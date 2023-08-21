package Professor.cards;

import Professor.actions.SpectrumizeAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import Professor.util.CardArtRoller;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Professor.MainModfile.makeID;

public class AncientSolution extends AbstractEasyCard {
    public final static String ID = makeID(AncientSolution.class.getSimpleName());

    public AncientSolution() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        MultiCardPreview.add(this, new RedNeutralizer(), new BlueNeutralizer(), new YellowNeutralizer(), new GreenNeutralizer());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SpectrumizeAction(magicNumber, l -> {
            for (AbstractCard c : l) {
                if (c.costForTurn == -1) {
                    this.addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
                } else if (c.costForTurn > 0) {// 51
                    this.addToTop(new GainEnergyAction(c.costForTurn));
                }
            }
        }));
    }

    @Override
    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(0);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GREEN, WHITE, Color.GREEN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return AncientSolution.class.getSimpleName();
    }
}