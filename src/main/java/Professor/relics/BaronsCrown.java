package Professor.relics;

import Professor.TheProfessor;
import Professor.cardmods.UnlockedMod;
import Professor.patches.ForcedUpgradesPatches;
import Professor.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static Professor.MainModfile.makeID;

public class BaronsCrown extends AbstractEasyRelic {
    public static final String ID = makeID(BaronsCrown.class.getSimpleName());
    private boolean cardsSelected = true;
    private int selection = 1;

    public BaronsCrown() {
        super(ID, RelicTier.RARE, LandingSound.CLINK, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }

    public void onEquip() {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (!CardModifierManager.hasModifier(c, UnlockedMod.ID)) {
                tmp.addToTop(c);
            }
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            ForcedUpgradesPatches.previewMultipleUpgrade = true;
            ForcedUpgradesPatches.upgradeTimes = 1;
            AbstractDungeon.gridSelectScreen.open(tmp, selection, DESCRIPTIONS[1], true, false, false, false);
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == selection) {
            ForcedUpgradesPatches.previewMultipleUpgrade = false;
            ForcedUpgradesPatches.upgradeTimes = 0;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardModifierManager.addModifier(c, new UnlockedMod());
            c.upgrade();
            Wiz.adp().bottledCardUpgradeCheck(c);
            AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH/2f, Settings.HEIGHT/2f));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.cardsSelected = true;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }
}
