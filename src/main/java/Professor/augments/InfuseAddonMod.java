package Professor.augments;

import CardAugments.cardmods.AbstractAugment;
import CardAugments.patches.InterruptUseCardFieldPatches;
import CardAugments.util.PortraitHelper;
import Professor.MainModfile;
import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.DealAOEDamageMod;
import Professor.cardmods.DealDamageMod;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class InfuseAddonMod extends AbstractAugment {
    public static final String ID = MainModfile.makeID(InfuseAddonMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.baseMagicNumber = card.magicNumber = (int) (card.baseDamage * 2/3f);
        InterruptUseCardFieldPatches.InterceptUseField.interceptUse.set(card, true);
        card.isEthereal = false;
        card.exhaust = true;
        card.target = AbstractCard.CardTarget.NONE;
        if (card.type != AbstractCard.CardType.SKILL) {
            card.type = AbstractCard.CardType.SKILL;
            PortraitHelper.setMaskedPortrait(card);
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return isNormalCard(card) && noShenanigans(card) && card.type == AbstractCard.CardType.ATTACK && cardCheck(card, c -> c.baseDamage > 5 && c.baseMagicNumber == -1 && c.baseBlock == -1);
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
        return (card.isInnate ? CARD_TEXT[0] : "") + (ReflectionHacks.getPrivate(card, AbstractCard.class, "isMultiDamage") ? CARD_TEXT[2] : CARD_TEXT[1]);
    }

    @Override
    public void onUpgradeCheck(AbstractCard card) {
        super.onUpgradeCheck(card);
        card.baseMagicNumber = card.magicNumber = (int) (card.baseDamage * 2/3f);
        card.upgradedMagicNumber = card.upgradedDamage;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (ReflectionHacks.getPrivate(card, AbstractCard.class, "isMultiDamage")) {
            addToBot(new InfuseCardsInHandAction(1, new DealAOEDamageMod(card.magicNumber)));
        } else {
            addToBot(new InfuseCardsInHandAction(1, new DealDamageMod(card.magicNumber)));
        }
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new InfuseAddonMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

}
