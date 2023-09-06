package Professor.augments;

import CardAugments.cardmods.AbstractAugment;
import Professor.MainModfile;
import Professor.actions.SpectrumizeAction;
import Professor.cards.tokens.BlueNeutralizer;
import Professor.cards.tokens.GreenNeutralizer;
import Professor.cards.tokens.RedNeutralizer;
import Professor.cards.tokens.YellowNeutralizer;
import basemod.abstracts.AbstractCardModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class SpectrumizeMod extends AbstractAugment {
    public static final String ID = MainModfile.makeID(SpectrumizeMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    //private static final List<TooltipInfo> catalystTip = Collections.singletonList(new TooltipInfo(BaseMod.getKeywordTitle(KeywordManager.CATALYST), BaseMod.getKeywordDescription(KeywordManager.CATALYST)));
    //private boolean addTip;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (MultiCardPreview.multiCardPreview.get(card).stream().noneMatch(c -> c instanceof RedNeutralizer)) {
            //addTip = true;
            MultiCardPreview.add(card, new RedNeutralizer(), new BlueNeutralizer(), new YellowNeutralizer(), new GreenNeutralizer());
        }
    }

    /*@Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        List<TooltipInfo> superTips = super.additionalTooltips(card);
        if (addTip) {
            if (superTips == null) {
                return catalystTip;
            }
            superTips.addAll(0, catalystTip);
        }
        return superTips;
    }*/

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2 && card.baseBlock > 0 && card.type == AbstractCard.CardType.SKILL && card.cardsToPreview == null && MultiCardPreview.multiCardPreview.get(card).isEmpty();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToBot(new SpectrumizeAction(1, !card.upgraded));
    }

    @Override
    public String getPrefix() {
        return TEXT[0];
    }

    @Override
    public String getSuffix() {
        return TEXT[1];
    }

    @Override
    public String getAugmentDescription() {
        return TEXT[2];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card.upgraded) {
            return insertAfterText(rawDescription , CARD_TEXT[1]);
        }
        return insertAfterText(rawDescription , CARD_TEXT[0]);
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        card.initializeDescription();
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.COMMON;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SpectrumizeMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
