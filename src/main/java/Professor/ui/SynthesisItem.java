package Professor.ui;

import Professor.actions.PerformSynthesisAction;
import Professor.cards.abstracts.AbstractCreationCard;
import Professor.cards.abstracts.AbstractRecipeCard;
import Professor.patches.ArchetypeHelper;
import Professor.util.ChimeraHelper;
import Professor.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import com.megacrit.cardcrawl.vfx.BobEffect;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.List;

public class SynthesisItem {
    public static final float ORBIT_R = 120f * Settings.scale;
    private final BobEffect bob = new BobEffect(3.0f * Settings.scale, 3.0f);
    public float cX, cY, tX, tY;
    public final ArrayList<SynthesisSlot> slots = new ArrayList<>();
    public AbstractRecipeCard currentRecipe;
    public AbstractCreationCard currentCreation;
    public int r,b,y,g;
    public boolean hovered;
    public float radius = ORBIT_R;
    public float angle;
    public float baseAngularSpeed = 5f;
    public float angularSpeed = baseAngularSpeed;
    public float angularAcceleration = 25f;
    public boolean processing;

    public SynthesisItem(AbstractRecipeCard recipeCard, List<AbstractCard> addedCards) {
        this.cX = this.tX = SynthesisPanel.BASE_X;
        this.cY = this.tY = SynthesisPanel.BASE_Y;
        this.currentRecipe = recipeCard;
        for (AbstractCard c : addedCards) {
            SynthesisSlot s = new SynthesisSlot(this, cX, cY);
            s.addCard(c);
            slots.add(s);
            if (ArchetypeHelper.isFire(c)) {
                r++;
            }
            if (ArchetypeHelper.isIce(c)) {
                b++;
            }
            if (ArchetypeHelper.isBolt(c)) {
                y++;
            }
            if (ArchetypeHelper.isWind(c)) {
                g++;
            }
        }
        this.currentCreation = currentRecipe.getCreation(r, b, y, g);
        this.currentCreation.current_x = cX;
        this.currentCreation.current_y = cY;
        this.currentCreation.drawScale = 0.2f;
        if (Loader.isModLoaded("ChimeraCards")) {
            ChimeraHelper.rollOnSynthesisCard(currentCreation);
        }
    }

    public void update() {
        this.cX = MathHelper.orbLerpSnap(this.cX, this.tX);
        this.cY = MathHelper.orbLerpSnap(this.cY, this.tY);
        bob.update();
        if (currentCreation != null) {
            currentCreation.target_y = cY + bob.y;
            currentCreation.target_x = cX;
            currentCreation.targetAngle = 0;
            currentCreation.update();
            currentCreation.hb.update();
            if (!processing && currentCreation.hb.hovered) {
                currentCreation.targetDrawScale = 0.75f;
                hovered = true;
            } else {
                currentCreation.targetDrawScale = 0.2f;
                hovered = false;
            }
        }
        positionSlots();
        for (SynthesisSlot s : slots) {
            s.update();
        }
    }

    public void render(SpriteBatch sb) {
        for (SynthesisSlot s : slots) {
            s.render(sb);
        }
        if (currentCreation != null) {
            currentCreation.render(sb);
            if (!processing && currentCreation.hb.hovered) {
                TipHelper.renderTipForCard(currentCreation, sb, currentCreation.keywords);
            }
        }
    }


    public void performSynthesis() {
        AbstractDungeon.actionManager.addToTop(new PerformSynthesisAction(this));
    }

    public void updateCreation() {
        currentCreation.updateElementData(new AbstractCreationCard.ElementData(r, b, y, g));
    }

    public void positionSlots() {
        if (processing || !hovered && slots.stream().noneMatch(SynthesisSlot::isHovered)) {
            angle += angularSpeed* Gdx.graphics.getDeltaTime();
            angle %= 360;
        }
        if (processing) {
            angularSpeed += angularAcceleration;
            radius--;
        }
        float da = 360f/slots.size();
        for (SynthesisSlot s : slots) {
            s.setTarget((float) (cX + radius * Math.cos(Math.toRadians(angle))), (float) (cY + radius * Math.sin(Math.toRadians(angle))));
            angle += da;
            angle %= 360;
        }
    }

    public void clear() {
        currentRecipe = null;
        currentCreation = null;
        slots.clear();
        r = b = y = g = 0;
        angularSpeed = baseAngularSpeed;
        radius = ORBIT_R;
        processing = false;
    }
}
