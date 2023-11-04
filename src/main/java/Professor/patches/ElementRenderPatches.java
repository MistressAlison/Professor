package Professor.patches;

import Professor.MainModfile;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.TexLoader;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;

public class ElementRenderPatches {
    private static final Color WHITEISH = Color.WHITE.cpy();
    private static final Texture fire = TexLoader.getTexture(MainModfile.makeImagePath("elements/FireSmall.png"));
    private static final Texture ice = TexLoader.getTexture(MainModfile.makeImagePath("elements/IceSmall.png"));
    private static final Texture bolt = TexLoader.getTexture(MainModfile.makeImagePath("elements/BoltSmall.png"));
    private static final Texture wind = TexLoader.getTexture(MainModfile.makeImagePath("elements/WindSmall.png"));
    private static final Texture fireSCV = TexLoader.getTexture(MainModfile.makeImagePath("elements/Fire.png"));
    private static final Texture iceSCV = TexLoader.getTexture(MainModfile.makeImagePath("elements/Ice.png"));
    private static final Texture boltSCV = TexLoader.getTexture(MainModfile.makeImagePath("elements/Bolt.png"));
    private static final Texture windSCV = TexLoader.getTexture(MainModfile.makeImagePath("elements/Wind.png"));
    private static final float spacing = 60f;
    private static final float spacingSCV = 120f;

    @SpirePatch2(clz = CardModifierManager.class, method = "onRender")
    public static class RenderHook {
        @SpirePrefixPatch
        public static void preMods(AbstractCard card, SpriteBatch sb) {
            if (shouldRender(card)) {
                doElementRendering(card, sb);
            }
        }
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onSingleCardViewRender")
    public static class SCVRenderHook {
        @SpirePrefixPatch
        public static void preMods(SingleCardViewPopup screen, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(screen, SingleCardViewPopup.class, "card");
            if (!(card instanceof AbstractEasyCard) && !MainModfile.renderElementsOffCharacter) {
                return;
            }
            doElementRenderingSCV(card, sb);
        }
    }

    public static boolean shouldRender(AbstractCard card) {
        if (!(card instanceof AbstractEasyCard) && !MainModfile.renderElementsOffCharacter) {
            return false;
        }
        return !card.isFlipped && (card.current_y >= -200.0F * Settings.scale && card.current_y <= Settings.HEIGHT + 200.0F * Settings.scale);
    }

    public static void doElementRendering(AbstractCard card, SpriteBatch sb) {
        ArrayList<Texture> elements = new ArrayList<>();
        if (ArchetypeHelper.isFire(card)) {
            //ExtraIcons.icon(fire).render(card);
            elements.add(fire);
        }
        if (ArchetypeHelper.isIce(card)) {
            //ExtraIcons.icon(ice).render(card);
            elements.add(ice);
        }
        if (ArchetypeHelper.isBolt(card)) {
            //ExtraIcons.icon(bolt).render(card);
            elements.add(bolt);
        }
        if (ArchetypeHelper.isWind(card)) {
            //ExtraIcons.icon(wind).render(card);
            elements.add(wind);
        }
        if (!elements.isEmpty()) {
            WHITEISH.a = card.transparency;
            sb.setColor(WHITEISH);
            float dx = -(elements.size()-1) * spacing / 2F;
            float dy = 210f / MainModfile.elementIconSize;
            for (Texture t : elements) {
                sb.draw(t, card.current_x + dx - t.getWidth()/2f, card.current_y + dy - t.getHeight()/2f,
                        t.getWidth()/2f - dx, t.getHeight()/2f - dy, t.getWidth(), t.getHeight(),
                        card.drawScale * Settings.scale * MainModfile.elementIconSize, card.drawScale * Settings.scale * MainModfile.elementIconSize, card.angle,
                        0, 0, t.getWidth(), t.getHeight(), false, false);
                dx += spacing;
            }
            sb.setColor(Color.WHITE);
        }
    }

    public static void doElementRenderingSCV(AbstractCard card, SpriteBatch sb) {
        ArrayList<Texture> elements = new ArrayList<>();
        if (ArchetypeHelper.isFire(card)) {
            //ExtraIcons.icon(fire).render(card);
            elements.add(fireSCV);
        }
        if (ArchetypeHelper.isIce(card)) {
            //ExtraIcons.icon(ice).render(card);
            elements.add(iceSCV);
        }
        if (ArchetypeHelper.isBolt(card)) {
            //ExtraIcons.icon(bolt).render(card);
            elements.add(boltSCV);
        }
        if (ArchetypeHelper.isWind(card)) {
            //ExtraIcons.icon(wind).render(card);
            elements.add(windSCV);
        }
        if (!elements.isEmpty()) {
            sb.setColor(Color.WHITE.cpy());
            float dx = -(elements.size()-1) * spacingSCV / 2F;
            float dy = 420f / MainModfile.elementIconSize;
            for (Texture t : elements) {
                sb.draw(t, Settings.WIDTH/2f + dx - t.getWidth()/2f, Settings.HEIGHT/2f + dy - t.getHeight()/2f,
                        t.getWidth()/2f - dx, t.getHeight()/2f - dy, t.getWidth(), t.getHeight(),
                        Settings.scale * MainModfile.elementIconSize, Settings.scale * MainModfile.elementIconSize, card.angle,
                        0, 0, t.getWidth(), t.getHeight(), false, false);
                dx += spacingSCV;
            }
        }
    }

}
