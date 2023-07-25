package Professor.actions;

import Professor.cardmods.UpgradeFlagMod;
import Professor.patches.ForcedUpgradesPatches;
import Professor.util.ChimeraHelper;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultiUpgradeAction extends AbstractGameAction {
    private final Predicate<AbstractCard> predicate;
    private final ArrayList<AbstractCard> hand;
    private final ArrayList<AbstractCard> tempHand;

    public MultiUpgradeAction(int upgrades) {
        this.amount = upgrades;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.predicate = c -> c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE;
        this.hand = AbstractDungeon.player.hand.group;
        this.tempHand = new ArrayList<>();
        this.tempHand.addAll(this.hand);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.hand.size() != 0 && this.hand.stream().anyMatch(this.predicate)) {
                if (hand.stream().filter(predicate).count() == 1) {
                    performUpgrades(hand.stream().filter(predicate).collect(Collectors.toList()));
                    this.isDone = true;
                } else {
                    this.tempHand.removeIf(this.predicate);
                    if (this.tempHand.size() > 0) {
                        this.hand.removeIf(this.tempHand::contains);
                    }
                    ForcedUpgradesPatches.previewMultipleUpgrade = true;
                    ForcedUpgradesPatches.upgradeTimes = amount;
                    AbstractDungeon.handCardSelectScreen.open(CardCrawlGame.languagePack.getUIString("ArmamentsAction").TEXT[0], 1, false, false, false, true);
                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            ForcedUpgradesPatches.previewMultipleUpgrade = false;
            ForcedUpgradesPatches.upgradeTimes = 0;
            performUpgrades(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            this.hand.addAll(AbstractDungeon.handCardSelectScreen.selectedCards.group);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            if (this.tempHand.size() > 0) {
                this.hand.addAll(this.tempHand);
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            this.isDone = true;
        } else {
            this.tickDuration();
        }
    }

    private void performUpgrades(List<AbstractCard> cards) {
        for (AbstractCard card : cards) {
            if (!(card instanceof SearingBlow) && !CardModifierManager.hasModifier(card, UpgradeFlagMod.ID)) {
                if (!(Loader.isModLoaded("CardAugments") && ChimeraHelper.hasSearing(card))) {
                    CardModifierManager.addModifier(card, new UpgradeFlagMod());
                }
            }
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            card.superFlash();
            card.applyPowers();
            for (int i = 0 ; i < amount ; i++) {
                card.upgrade();
            }
        }
    }
}